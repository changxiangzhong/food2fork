package com.xzchang.food2fork.view;

import android.content.Context;
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

import com.xzchang.food2fork.R;
import com.xzchang.food2fork.app.AppComponent;
import com.xzchang.food2fork.model.Recipie;
import com.xzchang.food2fork.rpc.GetSearchEvent;
import com.xzchang.food2fork.rpc.RecipieService;
import com.xzchang.food2fork.util.heteroadapter.BindableViewHolder;
import com.xzchang.food2fork.util.heteroadapter.HeterogenousAdapter;
import com.xzchang.food2fork.util.EndlessRecyclerOnScrollListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import retrofit2.Call;


public class RecipieListFragment extends BaseFragment {
    @Inject
    RecipieService recipieService;

    @BindView(R.id.recipie_list)
    RecyclerView recipieList;

    private String keyWord;
    private Call ongoing;

    private EndlessRecyclerOnScrollListener scrollListener;

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
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recipieList.setLayoutManager(llm);
        recipieList.addItemDecoration(new DividerItemDecoration(inflater.getContext(), DividerItemDecoration.VERTICAL));
        scrollListener = new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int currentPage) {
                recipieService.searchRecipie(keyWord, currentPage);
            }
        };
        recipieList.addOnScrollListener(scrollListener);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipieList.setAdapter(new RecipieListAdapter());
        searchRecipie(null);
    }

    private void searchRecipie(String keyWord) {
        this.keyWord = keyWord;
        getAdapter().clearRecipies();
        if (ongoing != null) {
            ongoing.cancel();
        }
        scrollListener.reset();
        recipieService.searchRecipie(keyWord, 1);
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
        ongoing = null;
        getAdapter().appendRecipies(event.recipies);
        onEndLoading();
    }

    private static class RecipieListAdapter extends HeterogenousAdapter<BindableViewHolder> {

        private void appendRecipies(Recipie[] newRecipies) {
            // Remove the loading indicator for previous page.
            if (viewModels.size() > 0 && viewModels.get(viewModels.size() - 1) instanceof ProgressFooterView.ProgressFooterViewModel) {
                viewModels.remove(viewModels.size() - 1);
            }

            for (Recipie r: newRecipies) {
                viewModels.add(new RecipieItemView.RecipieViewModel(r));
            }

            if (newRecipies.length > 0) {
                viewModels.add(new ProgressFooterView.ProgressFooterViewModel(null));
                notifyDataSetChanged();
            }
        }

        @Override
        public BindableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);

            // This is ugly.
            if (viewType == R.layout.footer_view_loading) {
                return new ProgressFooterViewHolder(v);
            } else if (viewType == R.layout.item_view_recipie){
                return new RecipieListItemViewHolder(v);
            }

            return null;
        }

        private void clearRecipies() {
            int size = viewModels.size();
            viewModels.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    public static class ProgressFooterViewHolder extends BindableViewHolder<ProgressFooterView.ProgressFooterViewModel> {

        public ProgressFooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class RecipieListItemViewHolder extends BindableViewHolder<RecipieItemView.RecipieViewModel> {
        public RecipieListItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void bind(final RecipieItemView.RecipieViewModel viewModel) {
            super.bind(viewModel);

            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipieDetailActivity.class);
                    intent.putExtra(RecipieDetailFragment.PARAM_RECIPIE_STUB, viewModel.getWrapped());
                    context.startActivity(intent);
                }
            });

        }
    }
}
