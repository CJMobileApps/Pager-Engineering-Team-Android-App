package com.pager.pagerchallenge.main.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pager.pagerchallenge.R;
import com.pager.pagerchallenge.network.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterHolder> {
    private List<User> mUserList;
    private Picasso mPicasso;

    public MainAdapter(Picasso picasso) {
        this.mPicasso = picasso;
    }

    @Override
    public MainAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,
                parent, false);

        return new MainAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MainAdapterHolder holder, int position) {
        User user = mUserList.get(position);
        holder.userName.setText(user.getName());
        holder.userStatus.setText(user.getStatus());
        holder.userRole.setText(user.getRole());
        holder.userGithub.setText(user.getGithub());
        holder.userLanguages.setText(user.getLanguages());
        holder.userSkills.setText(user.getSkills());
        holder.userLocation.setText(user.getLocation());
        mPicasso.load(user.getAvatar()).into(holder.userProfileImage);
    }

    @Override
    public int getItemCount() {
        return mUserList == null ? 0 : mUserList.size();
    }

    public void setUsersList(List<User> userList) {
        this.mUserList = userList;
        notifyDataSetChanged();
    }

    public class MainAdapterHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView userStatus;
        TextView userRole;
        TextView userGithub;
        TextView userLanguages;
        TextView userSkills;
        TextView userLocation;
        ImageView userProfileImage;


        public MainAdapterHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userStatus = itemView.findViewById(R.id.user_fromuser_status);
            userRole = itemView.findViewById(R.id.user_role);
            userGithub = itemView.findViewById(R.id.user_github);
            userProfileImage = itemView.findViewById(R.id.user_profile_image);
            userLanguages = itemView.findViewById(R.id.user_fromuser_languages);
            userSkills = itemView.findViewById(R.id.user_fromuser_skills);
            userLocation = itemView.findViewById(R.id.user_fromuser_location);
        }
    }
}