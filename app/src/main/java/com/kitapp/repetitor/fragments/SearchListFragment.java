package com.kitapp.repetitor.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kitapp.repetitor.Api;
import com.kitapp.repetitor.App;
import com.kitapp.repetitor.R;
import com.kitapp.repetitor.activities.SpecialistActivity;
import com.kitapp.repetitor.adapters.SearchListAdapter;
import com.kitapp.repetitor.entities.Repetitor;

import java.util.ArrayList;

public class SearchListFragment extends Fragment implements Api.SearchResultListener, SearchListAdapter.SearchListItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String paramDiscipline;
    private int paramCityId;
    private int paramStartPrice;
    private int paramEndPrice;

    private SearchListAdapter searchListAdapter;
    private RecyclerView rvSearchList;
    private TextView tvNotFound;

    public SearchListFragment() {
        // Required empty public constructor
    }

    public static SearchListFragment newInstance(String discipline, int city_id, int startPrice, int endPrice) {
        SearchListFragment fragment = new SearchListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, discipline);
        args.putInt(ARG_PARAM2, city_id);
        args.putInt(ARG_PARAM3, startPrice);
        args.putInt(ARG_PARAM4, endPrice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paramDiscipline = getArguments().getString(ARG_PARAM1);
            paramCityId = getArguments().getInt(ARG_PARAM2);
            paramStartPrice = getArguments().getInt(ARG_PARAM3);
            paramEndPrice = getArguments().getInt(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_search_list, container, false);
        rvSearchList = (RecyclerView) v.findViewById(R.id.rvSearchList);
        searchListAdapter = new SearchListAdapter(getContext());
        rvSearchList.setAdapter(searchListAdapter);
        rvSearchList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSearchList.setItemAnimator(new DefaultItemAnimator());
        searchListAdapter.setItemClickListener(this);
        tvNotFound = (TextView) v.findViewById(R.id.tvNotFound);
        App.getInstance().getApi().setSearchResultListener(this);
        App.getInstance().getApi().getFilteredRepetitors(paramDiscipline, paramCityId, paramStartPrice, paramEndPrice);
        return v;
    }

    @Override
    public void onSearchResult(ArrayList<Repetitor> result) {
        if (!result.isEmpty()) tvNotFound.setVisibility(View.INVISIBLE);
        searchListAdapter.add(result);
    }

    @Override
    public void onSearchError() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        App.getInstance().getApi().setSearchResultListener(null);
    }

    @Override
    public void onSearchItemClick(int id) {
        Intent intent = new Intent(SearchListFragment.this.getActivity(), SpecialistActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
