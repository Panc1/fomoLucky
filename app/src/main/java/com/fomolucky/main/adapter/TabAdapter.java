package com.fomolucky.main.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.fomolucky.main.R;
import com.fomolucky.main.databinding.ItemTabBinding;
import com.fomolucky.main.model.Tab;

import java.util.List;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder> {

    private List<Tab> list;
    private Callback callback;

    public TabAdapter(@NonNull Callback callback) {
        this.callback = callback;
    }

    public void setList(List<Tab> list) {
        if (this.list == null) {
            this.list = list;
            notifyItemRangeInserted(0, list.size());
        } else {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTabBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tab, parent, false);
        return new TabViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
        holder.binding.setPosition(position);
        holder.binding.setTab(list.get(position));
        holder.binding.setCallback(callback);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class TabViewHolder extends RecyclerView.ViewHolder {

        private ItemTabBinding binding;

        TabViewHolder(ItemTabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onClicked(int position);
    }

}
