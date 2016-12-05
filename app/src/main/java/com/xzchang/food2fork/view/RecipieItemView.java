package com.xzchang.food2fork.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xzchang.food2fork.R;
import com.xzchang.food2fork.model.Recipie;

import com.xzchang.food2fork.util.CircleTransformation;
import com.xzchang.food2fork.util.heteroadapter.BindableView;
import com.xzchang.food2fork.util.heteroadapter.ViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiangzhc on 05/12/2016.
 */

public class RecipieItemView extends LinearLayout implements BindableView<RecipieItemView.RecipieViewModel> {
    @BindView(R.id.recipie_icon)
    ImageView recipieIcon;

    @BindView(R.id.recipie_title)
    TextView recipieTitle;

    public RecipieItemView(Context context) {
        super(context);
        initView(context, null, -1);
    }

    public RecipieItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, -1);
    }

    public RecipieItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    public void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        try {
            LayoutInflater.from(context).inflate(R.layout.item_view_recipie0, this, true);
            ButterKnife.bind(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void bind(RecipieViewModel viewModel) {
        Recipie r = viewModel.getWrapped();
        recipieTitle.setText(r.getTitle());
        Picasso.with(getContext()).load(r.getImageUrl().toString()).placeholder(R.drawable.recipie_image_placeholder).transform(new CircleTransformation()).into(recipieIcon);
    }

    public static class RecipieViewModel extends ViewModel<Recipie> {

        public RecipieViewModel(Recipie model) {
            super(model);
        }

        @Override
        public int getLayout() {
            return R.layout.item_view_recipie;
        }
    }

}
