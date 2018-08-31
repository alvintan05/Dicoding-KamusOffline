package com.alvin.kamusoffline;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.alvin.kamusoffline.Database.KamusHelper;
import com.alvin.kamusoffline.Model.DataModel;
import com.alvin.kamusoffline.Preference.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {

        final String TAG = LoadData.class.getSimpleName();
        AppPreference appPreference;
        KamusHelper kamusHelper;
        double progress, progressMaxInsert, progressDiff;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            kamusHelper = new KamusHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {

            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {

                ArrayList<DataModel> engIndoModelArrayList = preLoadRawEngIndo();
                ArrayList<DataModel> indoEngArrayList = preLoadRawIndoEng();

                kamusHelper.open();

                progress = 10;
                publishProgress((int) progress);
                progressMaxInsert = 50.0;
                progressDiff = (progressMaxInsert - progress) / engIndoModelArrayList.size();

                kamusHelper.beginTransaction();

                try {
                    for (DataModel engIndoModel : engIndoModelArrayList) {
                        kamusHelper.insertEng(engIndoModel);
                            progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    // Jika semua proses telah di set success maka akan commit ke database
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                progressMaxInsert = 90.0;
                progressDiff = (progressMaxInsert - progress) / indoEngArrayList.size();

                try {
                    for (DataModel dataModel : indoEngArrayList) {
                        kamusHelper.insertIndo(dataModel);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    // Jika semua proses telah di set success maka akan commit ke database
                    kamusHelper.setTransactionSuccess();
                } catch (Exception e) {
                    // Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                kamusHelper.endTransaction();

                kamusHelper.close();

                appPreference.setFirstRun(false);

                publishProgress((int) maxprogress);

            }  else {
                try {
                    synchronized (this) {
                        this.wait(1000);
                        publishProgress(50);
                        this.wait(1000);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {

                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }

        public ArrayList<DataModel> preLoadRawEngIndo() {
            ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
            String line = null;
            BufferedReader reader;
            try {
                Resources res = getResources();
                InputStream raw_dict_eng_indo = res.openRawResource(R.raw.english_indonesia);

                reader = new BufferedReader(new InputStreamReader(raw_dict_eng_indo));
                int count = 0;
                do {
                    line = reader.readLine();
                    String [] splitstr = line.split("\t");

                    DataModel dataModel;

                    dataModel = new DataModel(splitstr [0], splitstr [1]);
                    dataModelArrayList.add(dataModel);
                    count++;
                } while (line != null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dataModelArrayList;
        }

        public ArrayList<DataModel> preLoadRawIndoEng() {
            ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
            String line = null;
            BufferedReader reader;
            try {
                Resources res = getResources();
                InputStream raw_dict_indo_eng = res.openRawResource(R.raw.indonesia_english);

                reader = new BufferedReader(new InputStreamReader(raw_dict_indo_eng));
                int count = 0;
                do {
                    line = reader.readLine();
                    String [] splitstr = line.split("\t");

                    DataModel dataModel;

                    dataModel = new DataModel(splitstr [0], splitstr [1]);
                    dataModelArrayList.add(dataModel);
                    count++;
                } while (line != null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dataModelArrayList;
        }
    }
}
