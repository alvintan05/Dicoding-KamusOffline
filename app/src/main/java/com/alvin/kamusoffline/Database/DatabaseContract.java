package com.alvin.kamusoffline.Database;

import android.provider.BaseColumns;

/**
 * Created by Alvin Tandiardi on 26/08/2018.
 */

public class DatabaseContract {

    static String TABLE_NAME1 = "table_english_indonesia";
    static String TABLE_NAME2 = "table_indonesia_english";

    static final class EngIndoColumns implements BaseColumns {

        //Kata yang dicari
        static String KATA_ENG = "kata_eng";
        //Jenis kata
        //Arti kata
        static String ARTI_ENG = "arti_eng";

    }

    static final class IndoEngColumns implements BaseColumns {

        //Kata yang dicari
        static String KATA_INDO = "kata_indo";
        //Arti kata
        static String ARTI_INDO = "arti_indo";

    }

}
