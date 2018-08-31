package com.alvin.kamusoffline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alvin.kamusoffline.Adapter.AllEnglishAdapter;
import com.alvin.kamusoffline.Database.KamusHelper;
import com.alvin.kamusoffline.Model.DataModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEnglishWordActivity extends AppCompatActivity {

    AllEnglishAdapter adapter;
    KamusHelper kamusHelper;

    private ArrayList<DataModel> dataModels;

    @BindView(R.id.all_english_recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_english_word);
        ButterKnife.bind(this);

        adapter = new AllEnglishAdapter(this);
        kamusHelper = new KamusHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.all_english_words));

        kamusHelper.open();
        // Ambil semua data
        dataModels = kamusHelper.getAllDataEng();

        kamusHelper.close();

        adapter.addItem(dataModels);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedWord(dataModels.get(position));
            }
        });
    }

    private void showSelectedWord(DataModel dataModel) {
        DataModel kataPilihan = new DataModel();
        kataPilihan.setKata(dataModel.getKata());
        kataPilihan.setArti(dataModel.getArti());
        Intent moveWithObjectIntent = new Intent(AllEnglishWordActivity.this, DetailActivity.class );
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_DETAIL, kataPilihan);
        startActivity(moveWithObjectIntent);
    }

}
