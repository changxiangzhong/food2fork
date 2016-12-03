package com.xzchang.food2fork.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.xzchang.food2fork.R;

public class RecipieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_list);
        initFragment();
    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = RecipieListFragment.newInstance();
        manager.beginTransaction().add(R.id.fragment_container, fragment).commit();
    }

}
