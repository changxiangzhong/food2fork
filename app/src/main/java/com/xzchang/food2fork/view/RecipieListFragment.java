package com.xzchang.food2fork.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xzchang.food2fork.R;
import com.xzchang.food2fork.app.AppComponent;
import com.xzchang.food2fork.rpc.GetSearchEvent;
import com.xzchang.food2fork.rpc.RecipieService;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;


public class RecipieListFragment extends BaseFragment {
    @Inject
    RecipieService recipieService;

    public RecipieListFragment() {}

    public static RecipieListFragment newInstance() {
        return new RecipieListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        appComponent.plus(new RecipieListComponent.RecipieListModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipie_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipieService.searchRecipie(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_recipie_list, menu);

        final MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getActivity(), "Search", Toast.LENGTH_LONG).show();
                MenuItemCompat.collapseActionView(searchItem);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Subscribe
    public void onSearchResult(GetSearchEvent event) {
        Toast.makeText(getActivity(), "Recipie count = " + event.recipies.length, Toast.LENGTH_SHORT).show();
    }
}
