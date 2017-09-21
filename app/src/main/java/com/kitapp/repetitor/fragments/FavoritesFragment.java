package com.kitapp.repetitor.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kitapp.repetitor.Api;
import com.kitapp.repetitor.App;
import com.kitapp.repetitor.R;
import com.kitapp.repetitor.activities.SpecialistActivity;
import com.kitapp.repetitor.adapters.FavoriteItemTouchCallback;
import com.kitapp.repetitor.adapters.FavoritesAdapter;
import com.kitapp.repetitor.entities.Repetitor;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment implements Api.FavoritesResultListener, FavoritesAdapter.FavoritesItemClickListener {

    RecyclerView rvFavList;
    FavoritesAdapter favoritesAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_favorites, container, false);
        rvFavList = (RecyclerView) v.findViewById(R.id.rvFavList);
        favoritesAdapter = new FavoritesAdapter(getContext());
        rvFavList.setAdapter(favoritesAdapter);
        rvFavList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavList.setItemAnimator(new DefaultItemAnimator());
        ItemTouchHelper touchHelper = new ItemTouchHelper(new FavoriteItemTouchCallback(favoritesAdapter));
        touchHelper.attachToRecyclerView(rvFavList);
        favoritesAdapter.setItemClickListener(this);
        App.getInstance().getApi().setFavoritesResultListener(this);
        App.getInstance().getApi().getFavoriteRepetitors();
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getInstance().getApi().setFavoritesResultListener(null);
    }

    @Override
    public void onFavoritesResult(ArrayList<Repetitor> result) {
        favoritesAdapter.add(result);
    }

    @Override
    public void onFavoritesError() {

    }

    @Override
    public void onFavoriteItemClick(int id) {
        Intent intent = new Intent(FavoritesFragment.this.getActivity(), SpecialistActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("fav", true);
        startActivity(intent);
    }
}
