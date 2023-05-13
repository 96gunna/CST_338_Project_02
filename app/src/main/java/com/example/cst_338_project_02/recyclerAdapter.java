package com.example.cst_338_project_02;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.cardViewHolder> {
    private List<RegisteredUser> currentUsers;
    public static class cardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;
        ImageView imageViewDelete;

        public cardViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewDelete = (ImageView) itemView.findViewById(R.id.imageDelete);
        }
    }

    public recyclerAdapter(List<RegisteredUser> currentUsers) {
        this.currentUsers = currentUsers;
    }

    @NonNull
    @Override
    public recyclerAdapter.cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.admin_view_user_card, parent, false);
        view.setOnClickListener(RemoveUsersActivity.customClick);
        recyclerAdapter.cardViewHolder newCard = new recyclerAdapter.cardViewHolder(view);
        return newCard;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.cardViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        TextView adminStatus = holder.textViewVersion;
        textViewName.setText(currentUsers.get(position).toString());
        adminStatus.setText("Admin Status: " + currentUsers.get(position).isAdmin());
    }

    @Override
    public int getItemCount() {
        return currentUsers.size();
    }
}
