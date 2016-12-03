package com.xzchang.food2fork.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xzchang.food2fork.R;
import com.xzchang.food2fork.app.AppComponent;
import com.xzchang.food2fork.model.Recipie;
import com.xzchang.food2fork.rpc.GetSearchEvent;
import com.xzchang.food2fork.rpc.RecipieService;
import com.xzchang.food2fork.util.CircleTransformation;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;


public class RecipieListFragment extends BaseFragment {
    @Inject
    RecipieService recipieService;

    @BindView(R.id.recipie_list)
    RecyclerView recipieList;

    public static RecipieListFragment newInstance() {
        return new RecipieListFragment();
    }

    @Override
    protected boolean hasContent() {
        return recipieList.getAdapter().getItemCount() > 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private RecipieListAdapter getAdapter() {
        return (RecipieListAdapter) recipieList.getAdapter();
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        appComponent.plus(new RecipieListComponent.RecipieListModule(this)).inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recipie_list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        recipieList.setLayoutManager(new LinearLayoutManager(getContext()));
        recipieList.addItemDecoration(new DividerItemDecoration(inflater.getContext(), DividerItemDecoration.VERTICAL));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipieList.setAdapter(new RecipieListAdapter());
        searchRecipie(null);
    }

    private void searchRecipie(String keyWord) {
        getAdapter().clearRecipies();
        recipieService.searchRecipie(keyWord);
        onStartLoading();
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
                searchRecipie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                searchRecipie(null);
                return true;
            }
        });
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onSearchResult(GetSearchEvent event) {
        getAdapter().appendRecipies(event.recipies);
        onEndLoading();
    }

    private class RecipieListAdapter extends RecyclerView.Adapter<RecipieListItemViewHolder> {
        private ArrayList<Recipie> recipies;

        private RecipieListAdapter(ArrayList<Recipie> recipies) {
            this.recipies = recipies;
        }

        private RecipieListAdapter() {
            this.recipies = new ArrayList<>();
        }

        private void appendRecipies(Recipie[] newRecipies) {
            recipies.addAll(Arrays.asList(newRecipies));
            notifyDataSetChanged();
        }

        private void appendRecipies(ArrayList<Recipie> newRecipies) {
            recipies.addAll(newRecipies);
        }

        @Override
        public RecipieListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recipie_list, parent, false);
            return new RecipieListItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecipieListItemViewHolder holder, int position) {
            final Recipie r = recipies.get(position);
            holder.title.setText(r.getTitle());
            holder.itemRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), RecipieDetailActivity.class);
                    startActivity(intent);
                }
            });
            Picasso.with(getActivity()).load(r.getImageUrl().toString()).placeholder(R.drawable.recipie_image_placeholder).transform(new CircleTransformation()).into(holder.icon);
        }

        @Override
        public int getItemCount() {
            return recipies.size();
        }

        private void clearRecipies() {
            int size = recipies.size();
            recipies.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    private static class RecipieListItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView title;
        private final View itemRoot;
        private RecipieListItemViewHolder(View itemView) {
            super(itemView);
            itemRoot = itemView;
            icon = (ImageView) itemView.findViewById(R.id.recipie_icon);
            title = (TextView) itemView.findViewById(R.id.recipie_title);
        }
    }
}
