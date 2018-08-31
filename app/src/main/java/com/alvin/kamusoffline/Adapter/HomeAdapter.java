package com.alvin.kamusoffline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alvin.kamusoffline.Model.DataModel;
import com.alvin.kamusoffline.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alvin Tandiardi on 29/08/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private ArrayList<DataModel> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public HomeAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new HomeHolder(view);
    }

    public void addItem(ArrayList<DataModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(HomeHolder holder, int position) {
        holder.titleResult.setText(mData.get(position).getKata());
        holder.descResult.setText(mData.get(position).getArti());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class HomeHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_result)
        TextView titleResult;

        @BindView(R.id.desc_result)
        TextView descResult;

        public HomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
