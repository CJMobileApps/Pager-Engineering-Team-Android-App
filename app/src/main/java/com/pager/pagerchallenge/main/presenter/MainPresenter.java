package com.pager.pagerchallenge.main.presenter;

import android.util.Log;

import com.pager.pagerchallenge.main.presenter.result.MainResult;
import com.pager.pagerchallenge.main.presenter.result.NewStatusOrNewUserResult;
import com.pager.pagerchallenge.main.presenter.result.RolesResult;
import com.pager.pagerchallenge.main.presenter.result.TeamMembersResult;
import com.pager.pagerchallenge.main.presenter.result.UserResult;
import com.pager.pagerchallenge.network.model.User;
import com.pager.pagerchallenge.main.navigator.MainPresenterToViewNavigator;
import com.pager.pagerchallenge.network.NetworkService;
import com.pager.pagerchallenge.network.model.TeamMember;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;


public class MainPresenter {
    private NetworkService mNetworkService;
    private MainPresenterToViewNavigator mMainPresenterToViewNavigator;
    private List<User> mUserList = new ArrayList<>();
    private CompositeDisposable mCompositeDisposable;
    private Map<Integer, String> mRolesMap;
    private MainUiModel initialUiModelState = MainUiModel.idle();
    private Observable<MainResult> mResults;
    private Observable<MainUiModel> mMainUiModels;

    private ObservableTransformer<GetUsersAction, TeamMembersResult> getTeamMembers = new ObservableTransformer<GetUsersAction, TeamMembersResult>() {
        @Override
        public ObservableSource<TeamMembersResult> apply(Observable<GetUsersAction> actions) {
            return actions.flatMap(getUsersAction -> mNetworkService.getTeamSingle()
                    .toObservable()
                    .map(teamMembers -> TeamMembersResult.success(teamMembers))
                    .onErrorReturn(t -> TeamMembersResult.failure(t.getMessage()))
                    .subscribeOn(Schedulers.io())
            );
        }
    };

    private ObservableTransformer<GetUsersAction, RolesResult> getRoles = new ObservableTransformer<GetUsersAction, RolesResult>() {
        @Override
        public ObservableSource<RolesResult> apply(Observable<GetUsersAction> actions) {
            return actions.flatMap(getUsersAction -> mNetworkService.getRolesSingle()
                    .toObservable()
                    .map(roles -> RolesResult.success(roles))
                    .onErrorReturn(t -> RolesResult.failure(t.getMessage()))
                    .subscribeOn(Schedulers.io())
            );
        }
    };

    private ObservableTransformer<GetUsersAction, MainResult> getNewStatusOrNewUser = new ObservableTransformer<GetUsersAction, MainResult>() {
        @Override
        public ObservableSource<MainResult> apply(Observable<GetUsersAction> actions) {
            return actions.flatMap(getUsersAction -> mNetworkService.getNewStatusOrNewUser()
                    .toObservable()
                    .map(user -> getNewStatusOrNewUserResult(user, mUserList, mRolesMap))
                    .onErrorReturn(t -> NewStatusOrNewUserResult.failure(t.getMessage()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .startWith(NewStatusOrNewUserResult.inProgress())
            );
        }
    };

    public MainPresenter(MainPresenterToViewNavigator mainPresenterToViewNavigator, NetworkService networkService) {
        this.mNetworkService = networkService;
        this.mMainPresenterToViewNavigator = mainPresenterToViewNavigator;
    }

    public void onCreate() {
        mCompositeDisposable = new CompositeDisposable();
        loadUsers();
    }

    public void onDestroy() {
        mCompositeDisposable.clear();
    }

    private void loadUsers() {
        Observable<GetUsersEvent> getUsersEvent = getUsersEvent();
        mCompositeDisposable.add(getTeamMembers(getUsersEvent));
    }

    private NewStatusOrNewUserResult getNewStatusOrNewUserResult(User currentUser, List<User> userList, Map<Integer, String> rolesMap) {
        //If user result already finished
        if (rolesMap != null) {
            boolean userFound = false;
            for (User user : userList) {
                if (user.getName().equals(currentUser.getName())) {
                    userFound = true;
                    user.setStatus(currentUser.getStatus());
                    break;
                }
            }

            if (!userFound) {
                userList.add(currentUser);
            }
            //No roles to use
        } else {
            currentUser.setStatus("Not available");
            userList.add(currentUser);
        }

        return NewStatusOrNewUserResult.success(userList);
    }

    private Observable<MainResult> getUsersResult(Observable<TeamMembersResult> teamMembersResultObservable,
                                                  Observable<RolesResult> rolesResultObservable,
                                                  List<User> originalUserList) {
        return Observable.zip(
                teamMembersResultObservable
                        .onErrorReturn(t -> TeamMembersResult.failure(t.getMessage())),
                rolesResultObservable
                        .onErrorReturn(t -> RolesResult.failure(t.getMessage())),
                new BiFunction<TeamMembersResult, RolesResult, MainResult>() {

                    @Override
                    public MainResult apply(TeamMembersResult teamMembersResult, RolesResult rolesResult) {
                        Set<User> userSet = new HashSet<>();

                        //if original user list not empty
                        if (originalUserList != null) {
                            for (User user : originalUserList) {
                                String role = mRolesMap.get(Integer.valueOf(user.role()));
                                user.setRole(role);
                                userSet.add(user);
                            }
                        }

                        List<TeamMember> teamMemberList = teamMembersResult.teamMembers;
                        List<User> userList = new ArrayList<>();
                        Map<Integer, String> rolesMap = rolesResult.rolesMap;

                        for (TeamMember teamMember : teamMemberList) {
                            String role = rolesMap.get(teamMember.role());
                            User user = new User(teamMember.name(),
                                    teamMember.languages(),
                                    teamMember.skills(),
                                    teamMember.location(),
                                    "Not available",
                                    role);

                            if (!userSet.contains(user)) {
                                userList.add(user);
                            }
                        }

                        if (originalUserList != null) {
                            originalUserList.addAll(userList);
                        }

                        return UserResult.success(originalUserList, rolesMap);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(UserResult.inProgress());
    }

    private Observable<MainUiModel> composeResultsToMainUiModel(Observable<MainResult> results) {
        this.mResults = results;

        if (mMainUiModels == null) {
            mMainUiModels = getMainUiModels();
        }

        return mMainUiModels;
    }

    private Observable<MainUiModel> getMainUiModels() {
        return mMainUiModels = mResults
                .scan(initialUiModelState, (state, mainResult) -> {

                    if (mainResult instanceof UserResult) {

                        if(mainResult.equals(UserResult.inProgress())){
                            return MainUiModel.inProgress();
                        }

                        if (mainResult.equals(UserResult.success(null, null))) {
                            UserResult userResult = (UserResult) mainResult;
                            return MainUiModel.success(userResult.userList, false, userResult.rolesMap);
                        }
                    }

                    if (mainResult instanceof NewStatusOrNewUserResult) {

                        if(mainResult.equals(NewStatusOrNewUserResult.inProgress())){
                            return MainUiModel.inProgress();
                        }

                        if (mainResult.equals(NewStatusOrNewUserResult.success(null))) {
                            return MainUiModel.success(((NewStatusOrNewUserResult) mainResult).userList, true, null);
                        }
                    }

                    throw new IllegalArgumentException("Unknown mainResult: error" + mainResult);
                });
    }

    private Observable<GetUsersEvent> getUsersEvent() {
        return Observable.just(new GetUsersEvent());
    }

    private ObservableTransformer<GetUsersEvent, MainUiModel> mainUiModel = new ObservableTransformer<GetUsersEvent, MainUiModel>() {
        @Override
        public ObservableSource<MainUiModel> apply(Observable<GetUsersEvent> events) {
            return events.map(event -> new GetUsersAction())
                    .publish(sharedAction1 -> {
                        return Observable.merge(sharedAction1.ofType(GetUsersAction.class)
                                .publish(sharedAction2 -> {
                                    Observable<TeamMembersResult> teamMembersResultObservable = sharedAction2.ofType(GetUsersAction.class).compose(getTeamMembers);
                                    Observable<RolesResult> rolesResultObservable = sharedAction2.ofType(GetUsersAction.class).compose(getRoles);
                                    return getUsersResult(teamMembersResultObservable, rolesResultObservable, mUserList);
                                }), sharedAction1.ofType(GetUsersAction.class).compose(getNewStatusOrNewUser));

                    })
                    .compose(results -> composeResultsToMainUiModel(results));
        }
    };

    private Disposable getTeamMembers(Observable<GetUsersEvent> event) {
        return event.compose(mainUiModel).subscribe(mainUiModel -> {

            if (mainUiModel.inProgress) {
                mMainPresenterToViewNavigator.showProgressDialog();
            } else {
                mMainPresenterToViewNavigator.dismissProgressDialog();
            }

            if(!mainUiModel.inProgress) {
                if (mainUiModel.success) {
                    mUserList = mainUiModel.userList;

                    if (!mainUiModel.newStatusOrNewUser) {
                        mRolesMap = mainUiModel.rolesMap;
                    }

                    if (mRolesMap != null) {
                        mMainPresenterToViewNavigator.setUsersToAdapter(mainUiModel.userList);

                    }
                    Log.d("HERE_", "getTeamMembers:success: mainUiModelSize " + mainUiModel.userList.size());
                } else {
                    Log.e("HERE_", "getTeamMembers: failed " +  mainUiModel.errorMessage);
                }
            }
        }, t -> {
            throw new OnErrorNotImplementedException(t);
        });
    }
}