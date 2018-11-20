package com.example.admin_linux.csdevproject.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin_linux.csdevproject.R;
import com.example.admin_linux.csdevproject.data.models.favorites.tabs.FavoritePossibleItem;

import java.util.ArrayList;
import java.util.List;

public class FavoritesTabPossibleItemAdapter extends RecyclerView.Adapter<FavoritesTabPossibleItemAdapter.ViewHolderPossibleItem> {

    private List<FavoritePossibleItem> mList;
    private Context mContext;

    public FavoritesTabPossibleItemAdapter(List<FavoritePossibleItem> mList, Context mContext) {
        this.mList = new ArrayList<>();
        this.mList = mList;

        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderPossibleItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_fragment_favorites_page_possible_item, viewGroup, false);

        return new ViewHolderPossibleItem(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPossibleItem holder, int position) {
        if (mList != null) {
            FavoritePossibleItem item = mList.get(position);
            holder.tvName.setText(item.getFavoriteFormTemplateName());
            holder.tvManufacturer.setText(item.getFavoriteFormTemplateManufacturer());
        } else {
            throw new IllegalArgumentException("Some error with binding data for FavoritesTabPossibleItemAdapter recycler view");
        }
    }

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolderPossibleItem extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvManufacturer;

        ViewHolderPossibleItem(View itemView){
            super(itemView);

            tvName = itemView.findViewById(R.id.list_item_possible_tv_name);
            tvManufacturer = itemView.findViewById(R.id.list_item_possible_tv_manufacturer);

        }

        void showFavoritePossibleItemDetails(FavoritePossibleItem possibleItem){

        }


    }
}
