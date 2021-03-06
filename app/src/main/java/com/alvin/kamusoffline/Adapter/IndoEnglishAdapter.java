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
 * Created by Alvin Tandiardi on 30/08/2018.
 */

public class IndoEnglishAdapter extends RecyclerView.Adapter<IndoEnglishAdapter.IndoEnglishHolder> {

    private ArrayList<DataModel> dataModels = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public IndoEnglishAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public IndoEnglishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent, false);
        return new IndoEnglishHolder(view);
    }

    public void addItem(ArrayList<DataModel> mData) {
        this.dataModels= mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(IndoEnglishHolder holder, int position) {
        holder.titleResult.setText(dataModels.get(position).getKata());
        holder.descResult.setText(dataModels.get(position).getArti());
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
        return dataModels.size();
    }


    public static class IndoEnglishHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_result)
        TextView titleResult;

        @BindView(R.id.desc_result)
        TextView descResult;

        public IndoEnglishHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
