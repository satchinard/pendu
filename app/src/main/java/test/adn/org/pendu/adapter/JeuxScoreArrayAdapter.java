package test.adn.org.pendu.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import test.adn.org.pendu.R;
import test.adn.org.pendu.db.Jeux;
import test.adn.org.pendu.db.JeuxDb;
import test.adn.org.pendu.dbhelper.JeuxDbHelper;

/**
 * Created by cagecfi on 14/07/2017.
 */

public class JeuxScoreArrayAdapter extends ArrayAdapter<Jeux> {

    private final Context context;
    private final ArrayList<Jeux> values;

    public JeuxScoreArrayAdapter(Context context, ArrayList<Jeux> values) {
        super(context, R.layout.activity_historique_item, values);
        this.context = context;
        this.values = values;
    }

    public static Cursor getScoreDatas(Context context, String pseudo) {

        JeuxDbHelper jeuxDbHelper = new JeuxDbHelper(context);
        SQLiteDatabase db = jeuxDbHelper.getReadableDatabase();


        String[] projection = {
                JeuxDb.ScoreJeux._ID,
                JeuxDb.ScoreJeux.COLUMN_NAME_PSEUDO,
                JeuxDb.ScoreJeux.COLUMN_NAME_EMAIL,
                JeuxDb.ScoreJeux.COLUMN_NAME_NIVEAU,
                JeuxDb.ScoreJeux.COLUMN_NAME_SCORE,
                JeuxDb.ScoreJeux.COLUMN_NAME_REUSSITE,
                JeuxDb.ScoreJeux.COLUMN_NAME_DATE
        };

//        // Filter results WHERE "pseudo" = 'pseudo'
        String selection = JeuxDb.ScoreJeux.COLUMN_NAME_PSEUDO + " = ?";
        String[] selectionArgs = {pseudo};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                JeuxDb.ScoreJeux.COLUMN_NAME_REUSSITE + " DESC, " +
                        JeuxDb.ScoreJeux.COLUMN_NAME_NIVEAU + " DESC, " +
                        JeuxDb.ScoreJeux.COLUMN_NAME_DATE + " DESC ";

        Cursor cursor = db.query(
                JeuxDb.ScoreJeux.TABLE_NAME,                // The table to query
                projection,                               // The columns to return
//                selection,                                // The columns for the WHERE clause
//                selectionArgs,                            // The values for the WHERE clause
                null,
                null,
                null,                            // don't group the rows
                null,                             // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return cursor;
    }

    public static ArrayList<Jeux> getScoreDatas(Context context) {

        JeuxDbHelper jeuxDbHelper = new JeuxDbHelper(context);
        SQLiteDatabase db = jeuxDbHelper.getReadableDatabase();

        String[] projection = {
                JeuxDb.ScoreJeux._ID,
                JeuxDb.ScoreJeux.COLUMN_NAME_PSEUDO,
                JeuxDb.ScoreJeux.COLUMN_NAME_EMAIL,
                JeuxDb.ScoreJeux.COLUMN_NAME_NIVEAU,
                JeuxDb.ScoreJeux.COLUMN_NAME_SCORE,
                JeuxDb.ScoreJeux.COLUMN_NAME_REUSSITE,
                JeuxDb.ScoreJeux.COLUMN_NAME_DATE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                JeuxDb.ScoreJeux.COLUMN_NAME_REUSSITE + " DESC, " +
                        JeuxDb.ScoreJeux.COLUMN_NAME_NIVEAU + " DESC, " +
                        JeuxDb.ScoreJeux.COLUMN_NAME_DATE + " DESC ";

        Cursor cursor = db.query(
                JeuxDb.ScoreJeux.TABLE_NAME,                // The table to query
                projection,                               // The columns to return
                null,
                null,
                null,                            // don't group the rows
                null,                             // don't filter by row groups
                sortOrder                                 // The sort order
        );

        ArrayList<Jeux> jeuxList = new ArrayList<>();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Jeux jeux = new Jeux();
                jeux.setPseudo(cursor.getString(1));
                jeux.setEmail(cursor.getString(2));
                jeux.setNiveau(cursor.getString(3));
                jeux.setScore(cursor.getString(4));
                jeux.setReussite(cursor.getString(5));
                jeux.setDate(cursor.getString(6));
                // Adding jeux to list
                jeuxList.add(jeux);
            } while (cursor.moveToNext());
        }

        return jeuxList;
    }

    public static void deleteAll(Context context) {
        JeuxDbHelper jeuxDbHelper = new JeuxDbHelper(context);
        SQLiteDatabase db = jeuxDbHelper.getReadableDatabase();
        db.delete(JeuxDb.ScoreJeux.TABLE_NAME, null, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_historique_item,
                parent, false);

        TextView pseudoTextView = (TextView) rowView.findViewById(R.id.txt_pseudo);
        TextView emailTextView = (TextView) rowView.findViewById(R.id.txt_email);
        TextView niveauTextView = (TextView) rowView.findViewById(R.id.txt_niveau);
        TextView scoreTextView = (TextView) rowView.findViewById(R.id.txt_coups);
        TextView dateTextView = (TextView) rowView.findViewById(R.id.txt_date);
        TextView resultatTextView = (TextView) rowView.findViewById(R.id.txt_resultat);

//        ImageView imageView = (ImageView) rowView.findViewById(R.id.pendu_img);

//        CardView cardView = (CardView) rowView.findViewById(R.id.cardview);

        Jeux j = values.get(position);

//        Typeface pseudoTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/JosefinSans-Bold.ttf");
//        Typeface emailTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/JosefinSans-SemiBoldItalic.ttf");
//        Typeface resultatTypeface = Typeface.createFromAsset(context.getAssets(),"fonts/Quicksand-Bold.otf");

        pseudoTextView.setText(j.getPseudo());
//        pseudoTextView.setTypeface(pseudoTypeface);
        emailTextView.setText(j.getEmail());
//        emailTextView.setTypeface(emailTypeface);
        niveauTextView.setText(j.getNiveau());
        scoreTextView.setText(j.getScore());
        dateTextView.setText(j.getDate());
        resultatTextView.setText(j.getReussite());
//        resultatTextView.setTypeface(resultatTypeface);
//        imageView.setImageResource(R.drawable.pendu_03);

        int p_left = ((position % 2) == 0) ? 4 : 8;
        int p_right = ((position % 2) == 0) ? 8 : 4;
        int i_color = ((position % 2) == 0) ? R.color.colorHist : R.color.colorWhite;

        rowView.setPadding(p_left, 8, p_right, 2);
        rowView.setBackgroundColor(context.getResources().getColor(i_color));

        return rowView;
    }

    private void deleteDatas(String pseudo) {
        JeuxDbHelper jeuxDbHelper = new JeuxDbHelper(context);
        SQLiteDatabase db = jeuxDbHelper.getReadableDatabase();

        // Define 'where' part of query.
        String selection = JeuxDb.ScoreJeux.COLUMN_NAME_PSEUDO + " LIKE ?";

        // Specify arguments in placeholder order.
        String[] selectionArgs = {pseudo};
        // Issue SQL statement.
        db.delete(JeuxDb.ScoreJeux.TABLE_NAME, selection, selectionArgs);
    }

    private void updateDatas(String pseudo) {
        JeuxDbHelper jeuxDbHelper = new JeuxDbHelper(context);
        SQLiteDatabase db = jeuxDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(JeuxDb.ScoreJeux.COLUMN_NAME_PSEUDO, pseudo);

        // Which row to update, based on the title
        String selection = JeuxDb.ScoreJeux.COLUMN_NAME_PSEUDO + " LIKE ?";
        String[] selectionArgs = {pseudo};

        int count = db.update(
                JeuxDb.ScoreJeux.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
}
