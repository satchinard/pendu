package test.adn.org.pendu.db;

import android.provider.BaseColumns;

/**
 * Created by cagecfi on 13/07/2017.
 */

public final class JeuxDb {

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ScoreJeux.TABLE_NAME + " ( " +
                    ScoreJeux._ID + " INTEGER AUTO INCREMENT PRIMARY KEY , " +
                    ScoreJeux.COLUMN_NAME_PSEUDO + " TEXT, " +
                    ScoreJeux.COLUMN_NAME_EMAIL + " TEXT, " +
                    ScoreJeux.COLUMN_NAME_NIVEAU + " TEXT, " +
                    ScoreJeux.COLUMN_NAME_SCORE + " INTEGER, " +
                    ScoreJeux.COLUMN_NAME_DATE + " DATE, " +
                    ScoreJeux.COLUMN_NAME_REUSSITE + " INTEGER) ";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ScoreJeux.TABLE_NAME;

    private JeuxDb() {
    }

    public static class ScoreJeux implements BaseColumns {
        public static final String TABLE_NAME = "score";
        public static final String COLUMN_NAME_PSEUDO = "pseudo";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_NIVEAU = "niveau";
        public static final String COLUMN_NAME_SCORE = "score";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_REUSSITE = "reussite";
    }
}
