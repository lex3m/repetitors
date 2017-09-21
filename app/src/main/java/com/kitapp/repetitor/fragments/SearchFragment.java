package com.kitapp.repetitor.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.kitapp.repetitor.App;
import com.kitapp.repetitor.R;
import com.kitapp.repetitor.entities.City;
import com.kitapp.repetitor.entities.PriceRange;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private OnSearchFragmentInteractionListener mListener;
    private ArrayList<City> cities;
    private ArrayList<String> disciplines;
    private ArrayList<PriceRange> priceRanges;
    private Spinner spCity;
    private ArrayAdapter<City> cityArrayAdapter;
    private Spinner spSpecialist;
    private ArrayAdapter<String> disciplineAdapter;
    private Spinner spPrice;
    private ArrayAdapter<PriceRange> priceRangeArrayAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cities = App.getInstance().getApi().getCities();
        disciplines = App.getInstance().getApi().getDisciplines();
        priceRanges = App.getInstance().getApi().getPriceRanges();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_search, container, false);
        spCity = (Spinner) view.findViewById(R.id.spCity);
        cityArrayAdapter = new ArrayAdapter<City>(getContext(), R.layout.spinner_item, cities);
        spCity.setAdapter(cityArrayAdapter);
        spSpecialist = (Spinner) view.findViewById(R.id.spSpecialist);
        disciplineAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, disciplines);
        spSpecialist.setAdapter(disciplineAdapter);
        spPrice = (Spinner) view.findViewById(R.id.spPrice);
        priceRangeArrayAdapter = new ArrayAdapter<PriceRange>(getContext(), R.layout.spinner_item, priceRanges);
        spPrice.setAdapter(priceRangeArrayAdapter);
        Button clearButton = (Button) view.findViewById(R.id.bClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spSpecialist.setSelection(0);
                spPrice.setSelection(0);
                spCity.setSelection(0);
            }
        });
        Button searchButton = (Button) view.findViewById(R.id.bSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchPressed();
            }
        });
        return view;
    }

    public void onSearchPressed() {
        if (mListener != null) {
            mListener.onSearchButtonPressed(spSpecialist.getSelectedItemPosition() != 0 ? (String) spSpecialist.getSelectedItem() : null,
                    (City) spCity.getSelectedItem(), (PriceRange) spPrice.getSelectedItem());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchFragmentInteractionListener) {
            mListener = (OnSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSearchFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSearchFragmentInteractionListener {
        void onSearchButtonPressed(String discipline, City city, PriceRange priceRange);
    }
}
