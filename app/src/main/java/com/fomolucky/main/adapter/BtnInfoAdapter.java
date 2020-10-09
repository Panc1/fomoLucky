package com.fomolucky.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fomolucky.main.R;
import com.fomolucky.main.databinding.ItemBtnInfoBinding;
import com.fomolucky.main.model.HomeInfoModel;

import java.util.List;

public class BtnInfoAdapter extends RecyclerView.Adapter<BtnInfoAdapter.BtnInfoItemViewHolder> {

    private List<? extends HomeInfoModel.BtnInfoItem> list;

    public void setList(List<? extends HomeInfoModel.BtnInfoItem> list) {
        if (this.list == null) {
            this.list = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return BtnInfoAdapter.this.list.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return BtnInfoAdapter.this.list.get(oldItemPosition).getName().compareTo(list.get(newItemPosition).getName()) == 0;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    HomeInfoModel.BtnInfoItem oldItem = list.get(oldItemPosition);
                    HomeInfoModel.BtnInfoItem newItem = list.get(newItemPosition);
                    return oldItem.getName().compareTo(newItem.getName()) == 0;
                }
            });
            this.list = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public BtnInfoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBtnInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_btn_info, parent, false);
        return new BtnInfoItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BtnInfoItemViewHolder holder, int position) {
        holder.binding.setBtnInfo(list.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class BtnInfoItemViewHolder extends RecyclerView.ViewHolder {

        final ItemBtnInfoBinding binding;

        BtnInfoItemViewHolder(@NonNull ItemBtnInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
