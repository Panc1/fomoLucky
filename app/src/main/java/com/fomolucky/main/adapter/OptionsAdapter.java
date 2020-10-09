package com.fomolucky.main.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fomolucky.base.utils.Utils;
import com.fomolucky.main.R;
import com.fomolucky.main.databinding.ItemOptionsBinding;
import com.fomolucky.main.databinding.ItemRoundInfoBinding;
import com.fomolucky.main.model.HomeInfoModel;

import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.OptionsItemViewHolder> {
    private CallBack callBack;

    public OptionsAdapter(CallBack callBack) {
        this.callBack = callBack;
    }

    private List<? extends HomeInfoModel.OptionsItem> list;

    public void setList(List<? extends HomeInfoModel.OptionsItem> list) {
        if (this.list == null) {
            this.list = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return OptionsAdapter.this.list.size();
                }

                @Override
                public int getNewListSize() {
                    return list.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return OptionsAdapter.this.list.get(oldItemPosition).getNum().compareTo(list.get(newItemPosition).getNum()) == 0;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    HomeInfoModel.OptionsItem oldItem = list.get(oldItemPosition);
                    HomeInfoModel.OptionsItem newItem = list.get(newItemPosition);
                    return oldItem.getNum().compareTo(newItem.getNum()) == 0;
                }
            });
            this.list = list;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public OptionsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOptionsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_options, parent, false);
        int item_width;
        if (viewType == 2)
            item_width = parent.getWidth() * 30 / 100;
        else
            item_width = parent.getWidth() * 40 / 300;
        ViewGroup.LayoutParams lp = binding.itemMain.getLayoutParams();
        lp.width = item_width;
        binding.itemMain.setLayoutParams(lp);
        binding.setCallback(callBack);
        return new OptionsItemViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        String symbol = list.get(position).getSymbol();
        if (symbol != null && !TextUtils.isEmpty(symbol))
            return 2;
        else return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsItemViewHolder holder, int position) {
        if (position == 0) holder.binding.viewBorder.setVisibility(View.GONE);
        holder.binding.setOptions(list.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class OptionsItemViewHolder extends RecyclerView.ViewHolder {

        final ItemOptionsBinding binding;

        OptionsItemViewHolder(@NonNull ItemOptionsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface CallBack {
        void onClicked(String number);
    }


}
