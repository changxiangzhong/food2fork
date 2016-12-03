package com.xzchang.food2fork.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xzchang.food2fork.R;
import com.xzchang.food2fork.app.AppComponent;
import com.xzchang.food2fork.model.Recipie;
import com.xzchang.food2fork.rpc.GetIngredient;
import com.xzchang.food2fork.rpc.RecipieService;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by xiangzhc on 03/12/2016.
 */

public class RecipieDetailFragment extends BaseFragment {
    public static final String PARAM_RECIPIE_STUB = "PARAM_RECIPIE_STUB";
    private Recipie stub;
    private boolean hasContent;

    @Inject
    RecipieService recipieService;

    @BindView(R.id.recipie_photo)
    ImageView recipiePhoto;

    @BindView(R.id.ingredient_list)
    TextView ingredientList;

    @BindView(R.id.button_instruction)
    TextView instructionButton;

    @BindView(R.id.button_original)
    TextView viewOriginalButton;

    @BindView(R.id.publisher_name)
    TextView publisher;

    @BindView(R.id.popularity)
    TextView popularityText;

    public static RecipieDetailFragment newInstance(Serializable serializable) {
        Bundle args = new Bundle();
        args.putSerializable(PARAM_RECIPIE_STUB, serializable);
        RecipieDetailFragment f = new RecipieDetailFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    protected boolean hasContent() {
        return hasContent;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        appComponent.plus(new RecipieDetailComponent.RecipieDetailModule(this)).inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recipie_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stub = (Recipie) getArguments().getSerializable(PARAM_RECIPIE_STUB);

        hasContent = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recipieService.fetchRecipieDetail(stub.getRecipieId());
        onStartLoading();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        NumberFormat formatter = new DecimalFormat("#0.00");
        Picasso.with(inflater.getContext())
                .load(stub.getImageUrl().toString())
                .into(recipiePhoto);

        publisher.setText(stub.getPublisher());
        popularityText.setText(getString(R.string.popularity_string, formatter.format(stub.getSocialRank())));
        instructionButton.setPaintFlags(instructionButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        instructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(stub.getF2fUrl().toString());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });
        viewOriginalButton.setPaintFlags(viewOriginalButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        viewOriginalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(stub.getSourceUrl().toString());
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
            }
        });

        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetRecipieDetail(GetIngredient event) {
        String[] ingredients = event.detail.getIngredients();

        String composedText = "&#8226;\t";

        for(int i = 0; i < ingredients.length - 1; i++) {
            composedText += ingredients[i] + "<br>&#8226;\t";
        }

        composedText += ingredients[ingredients.length - 1];

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ingredientList.setText(Html.fromHtml(composedText, Html.FROM_HTML_MODE_LEGACY));
        } else {
            ingredientList.setText(Html.fromHtml(composedText));
        }
    }
}
