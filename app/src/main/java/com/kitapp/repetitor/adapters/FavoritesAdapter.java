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
import com.kitapp.repetitor.entities.Repetitor;

import java.util.ArrayList;

/**
 * Created by denis on 9/21/17.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> implements ItemTouchHelperAdapter {

    private Context context;
    private ArrayList<Repetitor> repetitors;
    private FavoritesItemClickListener itemClickListener;

    public FavoritesAdapter(Context context) {
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
    public void onItemMove(int fromPosition, int toPosition) {
        //
    }

    public void onItemDismiss(int position) {
        App.getInstance().getApi().setFavorite(repetitors.get(position).getId(), false);
        repetitors.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.i_favorite, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Repetitor r = repetitors.get(position);
        holder.tvName.setText(r.getFio());
        holder.tvSubject.setText(r.getDiscipline());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onFavoriteItemClick(r.getId());
                }
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
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvFavName);
            tvSubject = (TextView) itemView.findViewById(R.id.tvFavSubject);
            card = (CardView) itemView.findViewById(R.id.cvFavCard);
        }
    }

    public interface FavoritesItemClickListener {
        void onFavoriteItemClick(int id);
    }

    public void setItemClickListener(FavoritesItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
