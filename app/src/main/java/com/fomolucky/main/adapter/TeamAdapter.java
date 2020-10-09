package com.fomolucky.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fomolucky.base.utils.GlideLoadUtils;
import com.fomolucky.main.R;
import com.fomolucky.main.databinding.ItemTeamBinding;
import com.fomolucky.main.model.HomeInfoModel;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    List<? extends HomeInfoModel.TeamItem> list;

    public void setList(List<? extends HomeInfoModel.TeamItem> list) {
        if (this.list == null) {
            this.list = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return TeamAdapter.this.list.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return TeamAdapter.this.list.get(oldItemPosition).getName().compareTo(list.get(newItemPosition).getName()) == 0;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    HomeInfoModel.TeamItem oldItem = list.get(oldItemPosition);
                    HomeInfoModel.TeamItem newItem = list.get(newItemPosition);
                    return oldItem.getName().compareTo(newItem.getName()) == 0;
                }
            });
            this.list = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTeamBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_team, parent, false);
        return new TeamViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        holder.binding.setTeams(list.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class TeamViewHolder extends RecyclerView.ViewHolder {

        final ItemTeamBinding binding;

        public TeamViewHolder(@NonNull ItemTeamBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
