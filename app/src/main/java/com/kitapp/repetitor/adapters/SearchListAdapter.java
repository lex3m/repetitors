package com.kitapp.repetitor.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitapp.repetitor.App;
import com.kitapp.repetitor.R;
import com.kitapp.repetitor.entities.City;
import com.kitapp.repetitor.entities.Repetitor;

import java.util.ArrayList;

/**
 * Created by denis on 9/21/17.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Repetitor> repetitors;
    private SearchListItemClickListener itemClickListener;

    public SearchListAdapter(Context context) {
        this.context = context;
        this.repetitors = new ArrayList<>();
    }

    public void add(ArrayList<Repetitor> list) {
        for (Repetitor r : list) {
            repetitors.add(r);
            this.notifyItemInserted(getItemCount() - 1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.i_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Repetitor r = repetitors.get(position);
        holder.tvName.setText(r.getFio());
        holder.tvSubject.setText(r.getDiscipline());
        City c = App.getInstance().getApi().getCityById(r.getCity_id());
        if (c != null) holder.tvCity.setText(c.getName());
        if (r.getPrice() != -1) {
            String s = String.valueOf(r.getPrice()) + " грн/" + r.getUnits();
            holder.tvPrice.setText(s);
        } else {
            holder.tvPrice.setText("-");
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) itemClickListener.onSearchItemClick(r.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return repetitors.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvSubject;
        TextView tvPrice;
        TextView tvCity;
        CardView card;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvSubject = (TextView) itemView.findViewById(R.id.tvSubject);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvCity = (TextView) itemView.findViewById(R.id.tvCity);
            card = (CardView) itemView.findViewById(R.id.cvCard);
        }
    }

    public interface SearchListItemClickListener {
        void onSearchItemClick(int id);
    }

    public void setItemClickListener(SearchListItemClickListener clickListener) {
        this.itemClickListener = clickListener;
    }
}
