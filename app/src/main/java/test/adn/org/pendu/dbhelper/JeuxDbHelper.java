package test.adn.org.pendu.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import test.adn.org.pendu.db.JeuxDb;
import test.adn.org.pendu.params.PenduConsts;

/**
 * Created by cagecfi on 14/07/2017.
 */

public class JeuxDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public JeuxDbHelper(Context context) {
        super(context, PenduConsts.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JeuxDb.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
//                db.execSQL("ALTER TABLE " + JeuxDb.ScoreJeux.TABLE_NAME + " ADD COLUMN INVENTORY TEXT");
                break;
            default:
                db.execSQL(JeuxDb.SQL_DELETE_ENTRIES);
        }
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
