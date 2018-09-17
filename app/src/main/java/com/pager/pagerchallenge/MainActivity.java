package com.pager.pagerchallenge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.moshi.Moshi.Builder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pager-team.herokuapp.com/")
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(MoshiConverterFactory.create()).build();
    CompositeDisposable disposable = new CompositeDisposable();
    RealGetUsersUseCase useCase =
      new RealGetUsersUseCase(new HttpRolesRepository(retrofit), new RealTeamRepository(retrofit),
        new SocketRepository("http://ios-hiring-backend.dokku.canillitapp.com", new OkHttpClient(),
          new Builder().build()));
    disposable.add(
      useCase.exec()
              .subscribeOn(Schedulers.computation())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(user -> Log.v("HERE_", user.toString()),
                  throwable -> Log.v("Error", throwable.toString())));
  }
}
