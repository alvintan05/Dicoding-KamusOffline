package com.alvin.kamusoffline;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.alvin.kamusoffline.Model.DataModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_DETAIL = "extra_detail";
    private String kata = "";

    @BindView(R.id.detail_title)
    TextView detailTitle;

    @BindView(R.id.detail_desc)
    TextView detailDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        DataModel detailObject = getIntent().getParcelableExtra(EXTRA_DETAIL);
        kata = detailObject.getKata();
        detailTitle.setText(kata);
        detailDesc.setText(detailObject.getArti());

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(kata);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
