package com.kitapp.repetitor;

import android.util.Log;

import com.kitapp.repetitor.entities.City;
import com.kitapp.repetitor.entities.PriceRange;
import com.kitapp.repetitor.entities.Repetitor;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by denis on 9/20/17.
 */

public class Api {

    private RepetitiorDB db;
    private ArrayList<City> cities = null;
    private ArrayList<String> disciplines = null;
    private ArrayList<PriceRange> priceRanges = null;

    private final static String api_url = "http://dev-topsu.ru/mail/index.php";
    private final OkHttpClient client;

    Api(RepetitiorDB db) {
        this.db = db;
        client = new OkHttpClient();
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

    public void sendConnectFormData(int id, String name, String phone, String place, String comment) {
        FormBody.Builder body = new FormBody.Builder();
        body.add("id", String.valueOf(id));
        body.add("name", name);
        body.add("phone", phone);
        body.add("place", place);
        body.add("comment", comment);
        Request request = new Request.Builder()
                .url(api_url)
                .post(body.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (connectFormResultListener != null) {
                    connectFormResultListener.onError();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.d("response", response.body().string());
                    if (connectFormResultListener != null) {
                        connectFormResultListener.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendAboutFormData(String name, String phone, String comment) {
        FormBody.Builder body = new FormBody.Builder();
        body.add("name", name);
        body.add("phone", phone);
        body.add("comment", comment);
        Request request = new Request.Builder()
                .url("http://dev-topsu.ru/mail2/index.php")
                .post(body.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                if (aboutFormResultListener != null) {
                    aboutFormResultListener.onError();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.d("response", response.body().string());
                    if (aboutFormResultListener != null) {
                        aboutFormResultListener.onSuccess();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private SearchResultListener searchResultListener;
    private FavoritesResultListener favoritesResultListener;
    private ConnectFormResultListener connectFormResultListener;
    private AboutFormResultListener aboutFormResultListener;

    public void setSearchResultListener(SearchResultListener searchResultListener) {
        this.searchResultListener = searchResultListener;
    }

    public void setFavoritesResultListener(FavoritesResultListener favoritesResultListener) {
        this.favoritesResultListener = favoritesResultListener;
    }

    public void setConnectFormResultListener(ConnectFormResultListener connectFormResultListener) {
        this.connectFormResultListener = connectFormResultListener;
    }

    public void setAboutFormResultListener(AboutFormResultListener aboutFormResultListener) {
        this.aboutFormResultListener = aboutFormResultListener;
    }

    public interface SearchResultListener {
        void onSearchResult(ArrayList<Repetitor> result);
        void onSearchError();
    }

    public interface FavoritesResultListener {
        void onFavoritesResult(ArrayList<Repetitor> result);
        void onFavoritesError();
    }

    public interface ConnectFormResultListener {
        void onSuccess();
        void onError();
    }

    public interface AboutFormResultListener {
        void onSuccess();
        void onError();
    }

}
