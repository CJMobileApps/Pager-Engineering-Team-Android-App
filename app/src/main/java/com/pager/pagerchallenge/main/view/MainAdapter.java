package com.pager.pagerchallenge.main.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pager.pagerchallenge.R;
import com.pager.pagerchallenge.network.model.User;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainAdapterHolder> {
    private List<User> mUserList;

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

        public MainAdapterHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userStatus = itemView.findViewById(R.id.user_status);
        }
    }
}