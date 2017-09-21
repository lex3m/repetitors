package com.kitapp.repetitor.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.kitapp.repetitor.entities.City;
import com.kitapp.repetitor.entities.PriceRange;
import com.kitapp.repetitor.fragments.AboutFragment;
import com.kitapp.repetitor.fragments.FavoritesFragment;
import com.kitapp.repetitor.fragments.SearchFragment;
import com.kitapp.repetitor.R;
import com.kitapp.repetitor.fragments.SearchListFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements SearchFragment.OnSearchFragmentInteractionListener {

    FragmentManager fragmentManager;
    BottomNavigationView navigation;
    int currentItem = R.id.navigation_specialists;
    int previousItem = R.id.navigation_specialists;
    private SearchFragment searchFragment;
    private FavoritesFragment favoritesFragment;
    private AboutFragment aboutFragment;
    private SearchListFragment searchListFragment;
    private boolean listShown = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (currentItem != item.getItemId()) {
                previousItem = currentItem;
                FragmentTransaction ts = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_specialists:
                        if (!listShown) {
                            setWindowTitle(R.string.search);
                            ts.show(searchFragment);
                            if (currentItem == R.id.navigation_favorites)
                                ts.remove(favoritesFragment);
                            if (currentItem == R.id.navigation_about) ts.remove(aboutFragment);
                            ts.commit();
                            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        } else {
                            setWindowTitle(R.string.search_list);
                            ts.show(searchListFragment);
                            if (currentItem == R.id.navigation_favorites)
                                ts.remove(favoritesFragment);
                            if (currentItem == R.id.navigation_about) ts.remove(aboutFragment);
                            ts.commit();
                            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        }
                        break;
                    case R.id.navigation_favorites:
                        setWindowTitle(R.string.favorites);
                        if (favoritesFragment == null) favoritesFragment = FavoritesFragment.newInstance();
                        ts.add(R.id.content, favoritesFragment);
                        ts.hide(searchFragment);
                        if (currentItem == R.id.navigation_about) ts.remove(aboutFragment);
                        ts.commit();
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        break;
                    case R.id.navigation_about:
                        setWindowTitle(R.string.about);
                        if (aboutFragment == null) aboutFragment = AboutFragment.newInstance();
                        ts.add(R.id.content, aboutFragment);
                        ts.hide(searchFragment);
                        if (currentItem == R.id.navigation_favorites) ts.remove(favoritesFragment);
                        ts.commit();
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        break;
                }
                currentItem = item.getItemId();
                return true;
                //updateNavigationBarState(item.getItemId());
            }
            return false;
        }

    };

    private void updateNavigationBarState(int actionId){
        Menu menu = navigation.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        setWindowTitle(R.string.search);
        fragmentManager = getSupportFragmentManager();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        searchFragment = SearchFragment.newInstance();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, SearchFragment.newInstance());
        transaction.commit();
    }

    private void setWindowTitle(int titleResId) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(titleResId);
        }
    }

    @Override
    public void onSearchButtonPressed(String discipline, City city, PriceRange priceRange) {
        FragmentTransaction ts = fragmentManager.beginTransaction();
        searchListFragment = SearchListFragment.newInstance(discipline, city.getId(), priceRange.getStartPrice(), priceRange.getEndPrice());
        ts.add(R.id.content, searchListFragment);
        ts.hide(searchFragment);
        ts.commit();
        setWindowTitle(R.string.search_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listShown = true;
    }

    @Override
    public void onBackPressed() {
        if (currentItem == R.id.navigation_specialists) {
            if (listShown) {
                listShown = false;
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                FragmentTransaction ts = fragmentManager.beginTransaction();
                ts.show(searchFragment);
                ts.remove(searchListFragment);
                ts.commit();
                searchListFragment = null;
            } else {
                finish();
            }
        } else {
            navigation.findViewById(R.id.navigation_specialists).performClick();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
