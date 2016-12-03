package com.xzchang.food2fork.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.xzchang.food2fork.R;

import java.io.Serializable;

public class RecipieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_detail);
        initFragment();
    }

    protected void initFragment() {
        Serializable serializable = getIntent().getSerializableExtra(RecipieDetailFragment.PARAM_RECIPIE_STUB);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = RecipieDetailFragment.newInstance(serializable);
        manager.beginTransaction().add(R.id.fragment_container, fragment).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
