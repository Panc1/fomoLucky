package com.fomolucky.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fomolucky.main.R;
import com.fomolucky.main.databinding.ItemRoundInfoBinding;
import com.fomolucky.main.model.HomeInfoModel;

import java.util.List;

public class RoundInfoAdapter extends RecyclerView.Adapter<RoundInfoAdapter.RoundItemViewHolder> {

    List<? extends HomeInfoModel.RoundInfoItem> list;

    public void setList(List<? extends HomeInfoModel.RoundInfoItem> list) {
        if (this.list == null) {
            this.list = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return RoundInfoAdapter.this.list.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return RoundInfoAdapter.this.list.get(oldItemPosition).getName().compareTo(list.get(newItemPosition).getName()) == 0;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    HomeInfoModel.RoundInfoItem oldItem = list.get(oldItemPosition);
                    HomeInfoModel.RoundInfoItem newItem = list.get(newItemPosition);
                    return oldItem.getName().compareTo(newItem.getName()) == 0;
                }
            });
            this.list = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public RoundItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRoundInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_round_info, parent, false);
        return new RoundItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RoundItemViewHolder holder, int position) {
        holder.binding.setRoundInfoItem(list.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class RoundItemViewHolder extends RecyclerView.ViewHolder {

        final ItemRoundInfoBinding binding;

        public RoundItemViewHolder(@NonNull ItemRoundInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
