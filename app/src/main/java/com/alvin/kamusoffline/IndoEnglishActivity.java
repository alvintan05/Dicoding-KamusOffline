package com.alvin.kamusoffline;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alvin.kamusoffline.Adapter.IndoEnglishAdapter;
import com.alvin.kamusoffline.Database.KamusHelper;
import com.alvin.kamusoffline.Model.DataModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndoEnglishActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    IndoEnglishAdapter adapter;
    KamusHelper kamusHelper;

    private ArrayList<DataModel> searchResults;
    private ActionBarDrawerToggle toggle;

    @BindView(R.id.help_text)
    TextView helpText;

    @BindView(R.id.result_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.indo_eng_toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indo_english);
        ButterKnife.bind(this);

        kamusHelper = new KamusHelper(this);
        adapter = new IndoEnglishAdapter(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getResources().getString(R.string.indonesia_english));

        navigationView.setNavigationItemSelectedListener(this);

        if (searchResults != null) adapter.addItem(searchResults);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedWord(searchResults.get(position));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getResources().getString(R.string.search_hint_indo));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                kamusHelper.open();
                searchResults = kamusHelper.getDataByNameIndo(query);
                kamusHelper.close();
                helpText.setVisibility(View.GONE);
                adapter.addItem(searchResults);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.eng_indo) {
            Intent indoIntent = new Intent(IndoEnglishActivity.this, HomeActivity.class);
            startActivity(indoIntent);
            finish();
        } else if (id == R.id.indo_eng) {

        } else if (id == R.id.all_english_words) {
            Intent allEngIntent = new Intent(IndoEnglishActivity.this, AllEnglishWordActivity.class);
            startActivity(allEngIntent);
        } else if (id == R.id.all_indonesia_words) {
            Intent allIndoIntent = new Intent(IndoEnglishActivity.this, AllIndonesiaActivity.class);
            startActivity(allIndoIntent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSelectedWord(DataModel dataModel) {
        DataModel kataPilihan = new DataModel();
        kataPilihan.setKata(dataModel.getKata());
        kataPilihan.setArti(dataModel.getArti());
        Intent moveWithObjectIntent = new Intent(IndoEnglishActivity.this, DetailActivity.class );
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_DETAIL, kataPilihan);
        startActivity(moveWithObjectIntent);
    }

}
