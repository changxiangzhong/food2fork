package com.xzchang.food2fork.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.xzchang.food2fork.R;

public class RecipieListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_list);
        initFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = RecipieListFragment.newInstance();
        manager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

}
