package com.kitapp.repetitor;

import com.kitapp.repetitor.entities.City;
import com.kitapp.repetitor.entities.PriceRange;
import com.kitapp.repetitor.entities.Repetitor;

import java.util.ArrayList;

/**
 * Created by denis on 9/20/17.
 */

public class Api {

    private RepetitiorDB db;
    private ArrayList<City> cities = null;
    private ArrayList<String> disciplines = null;
    private ArrayList<PriceRange> priceRanges = null;

    private SearchResultListener searchResultListener;
    private FavoritesResultListener favoritesResultListener;

    Api(RepetitiorDB db) {
        this.db = db;
    }

    public void init() {
        cities = db.getCitiesList();
        disciplines = db.getDisciplinesList();
        priceRanges = initPriceRanges();
    }

    public ArrayList<City> getCities() {
        if (cities == null) cities = db.getCitiesList();
        return cities;
    }

    public ArrayList<String> getDisciplines() {
        if (disciplines == null) disciplines = db.getDisciplinesList();
        return disciplines;
    }

    public ArrayList<PriceRange> getPriceRanges() {
        if (priceRanges == null) priceRanges = initPriceRanges();
        return priceRanges;
    }

    public void getFilteredRepetitors(String discipline, int city_id, int startPrice, int endPrice) {
        if (searchResultListener != null) {
            searchResultListener.onSearchResult(db.getFilteredRepetitorsList(discipline, city_id, startPrice, endPrice));
        }
    }

    public Repetitor getRepetotor(int id) {
        return db.getRepetitorById(id);
    }

    public void getFavoriteRepetitors() {
        if (favoritesResultListener != null) {
            favoritesResultListener.onFavoritesResult(db.getFavoritesList());
        }
    }

    public void setFavorite(int repetitor_id, boolean favorite) {
        db.setFavoriteById(repetitor_id, favorite);
    }

    private ArrayList<PriceRange> initPriceRanges() {
        ArrayList<PriceRange> l = new ArrayList<>();
        l.add(new PriceRange(-1, -1));
        l.add(new PriceRange(0, 70));
        for (int i = 70; i < 200; i += 10) {
            l.add(new PriceRange(i, i + 10));
        }
        l.add(new PriceRange(200, -1));
        return l;
    }

    public City getCityById(int id) {
        ArrayList<City> cities = getCities();
        for (City c : cities) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public void setSearchResultListener(SearchResultListener searchResultListener) {
        this.searchResultListener = searchResultListener;
    }

    public void setFavoritesResultListener(FavoritesResultListener favoritesResultListener) {
        this.favoritesResultListener = favoritesResultListener;
    }

    public interface SearchResultListener {
        void onSearchResult(ArrayList<Repetitor> result);
        void onSearchError();
    }

    public interface FavoritesResultListener {
        void onFavoritesResult(ArrayList<Repetitor> result);
        void onFavoritesError();
    }

}
