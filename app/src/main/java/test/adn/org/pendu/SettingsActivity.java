package test.adn.org.pendu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import test.adn.org.pendu.dbhelper.JeuxDbHelper;
import test.adn.org.pendu.params.PenduConsts;

import static test.adn.org.pendu.params.PenduConsts.JEU_JOUER_SON;
import static test.adn.org.pendu.params.PenduConsts.JEU_NIVEAU_DEBUTANT;

public class SettingsActivity extends AppCompatActivity {

    private String niveauJeu = JEU_NIVEAU_DEBUTANT;
    private Boolean sonActive = false;
    private Boolean vibreurActive = false;
    private Boolean guideActive = true;
    private String emailJoueur;
    private String pseudoJoueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences(PenduConsts.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        emailJoueur = sharedPref.getString(getString(R.string.txt_email), "");
        pseudoJoueur = sharedPref.getString(getString(R.string.txt_pseudo), "");
        ((EditText) findViewById(R.id.edit_pseudo)).setText(pseudoJoueur);
        ((EditText) findViewById(R.id.edit_email)).setText(emailJoueur);

//        testDb();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(PenduConsts.JEU_JOUEUR_PSEUDO, pseudoJoueur);
        outState.putString(PenduConsts.JEU_JOUEUR_EMAIL, emailJoueur);
        outState.putBoolean(JEU_JOUER_SON, sonActive);
        outState.putBoolean(PenduConsts.JEU_VIBRER_PHONE, vibreurActive);
        outState.putBoolean(PenduConsts.JEU_AFFICHE_GUIDE, guideActive);
        outState.putString(PenduConsts.JEU_NIVEAU_JEU, niveauJeu);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    public void jouer(View view) {
        Intent intent = new Intent();
        pseudoJoueur = (((EditText) findViewById(R.id.edit_pseudo)).getText()).toString();
        emailJoueur = (((EditText) findViewById(R.id.edit_email)).getText()).toString();
        if (pseudoJoueur.trim().length() == 0) {
            ((EditText) findViewById(R.id.edit_pseudo)).setError("Pseudo obligatoire.");
            return;
        }
        if (emailJoueur.trim().length() == 0) {
            ((EditText) findViewById(R.id.edit_email)).setError("Email obligatoire.");
            return;
        }
        if (pseudoJoueur.trim().length() != 0 && emailJoueur.trim().length() != 0) {
            intent.putExtra(PenduConsts.JEU_JOUEUR_PSEUDO, pseudoJoueur);
            intent.putExtra(PenduConsts.JEU_JOUEUR_EMAIL, emailJoueur);
            intent.putExtra(JEU_JOUER_SON, sonActive);
            intent.putExtra(PenduConsts.JEU_VIBRER_PHONE, vibreurActive);
            intent.putExtra(PenduConsts.JEU_AFFICHE_GUIDE, guideActive);
            intent.putExtra(PenduConsts.JEU_NIVEAU_JEU, niveauJeu);
            intent.setClass(getApplicationContext(), MainActivity.class);

            SharedPreferences sharedPref = getApplicationContext()
                    .getSharedPreferences(PenduConsts.SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.txt_email), emailJoueur);
            editor.putString(getString(R.string.txt_pseudo), pseudoJoueur);
            editor.commit();

            startActivity(intent);
        }
    }

    public void showScores(View view) {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), HistoriqueActivity.class);
//        intent.setClass(getApplicationContext(), MobileActivity.class);
        startActivity(intent);
    }

    public void onSonChecked(View view) {
        sonActive = ((Switch) findViewById(R.id.sw_son)).isChecked();
    }

    public void onVibreurChecked(View view) {
        vibreurActive = ((Switch) findViewById(R.id.sw_son)).isChecked();
    }

    public void onGuideChecked(View view) {
        guideActive = ((Switch) findViewById(R.id.sw_son)).isChecked();
    }

    public void onNiveauButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.rd_debutant:
                niveauJeu = JEU_NIVEAU_DEBUTANT;
                break;
            case R.id.rd_normal:
                niveauJeu = PenduConsts.JEU_NIVEAU_NORMAL;
                break;
            case R.id.rd_expert:
                niveauJeu = PenduConsts.JEU_NIVEAU_EXPERT;
                break;
            default:
                niveauJeu = PenduConsts.JEU_NIVEAU_NORMAL;
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            sonActive = savedInstanceState.getBoolean(JEU_JOUER_SON);
            vibreurActive = savedInstanceState.getBoolean(PenduConsts.JEU_VIBRER_PHONE);
            guideActive = savedInstanceState.getBoolean(PenduConsts.JEU_AFFICHE_GUIDE);
            niveauJeu = savedInstanceState.getString(PenduConsts.JEU_NIVEAU_JEU);
            emailJoueur = savedInstanceState.getString(PenduConsts.JEU_JOUEUR_EMAIL);
            pseudoJoueur = savedInstanceState.getString(PenduConsts.JEU_JOUEUR_PSEUDO);
            restaurerParametres();
        }
    }

    public void restaurerParametres() {
        ((TextView) findViewById(R.id.edit_email)).setText(emailJoueur);
        ((TextView) findViewById(R.id.edit_pseudo)).setText(pseudoJoueur);
        ((Switch) findViewById(R.id.sw_son)).setChecked(sonActive);
        ((Switch) findViewById(R.id.sw_guide)).setChecked(guideActive);
        ((Switch) findViewById(R.id.sw_vibreur)).setChecked(vibreurActive);
        switch (niveauJeu) {
            case PenduConsts.JEU_NIVEAU_EXPERT:
                ((RadioButton) findViewById(R.id.rd_expert)).setChecked(true);
                break;
            case JEU_NIVEAU_DEBUTANT:
                ((RadioButton) findViewById(R.id.rd_debutant)).setChecked(true);
                break;
            case PenduConsts.JEU_NIVEAU_NORMAL:
                ((RadioButton) findViewById(R.id.rd_normal)).setChecked(true);
                break;
            default:
                ((RadioButton) findViewById(R.id.rd_debutant)).setChecked(true);
                break;
        }
    }

    public void testDb() {
        JeuxDbHelper jeuxDbHelper = new JeuxDbHelper(getApplicationContext());
        SQLiteDatabase db = jeuxDbHelper.getWritableDatabase();
        db.execSQL("insert into score(pseudo , email , score)" +
                " VALUES ('adaro2000','adaro2000@gmail.com','8');");
    }
}
