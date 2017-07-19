package test.adn.org.pendu;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

import test.adn.org.pendu.db.JeuxDb;
import test.adn.org.pendu.dbhelper.JeuxDbHelper;
import test.adn.org.pendu.params.PenduConsts;
import test.adn.org.pendu.utils.DateUtils;
import test.adn.org.pendu.utils.UIHelper;

import static test.adn.org.pendu.params.PenduConsts.JEU_JOUEUR_EMAIL;

public class MainActivity extends AppCompatActivity {

    private int nombreCache;
    private int nombreEssais;
    private int nombreSaisi;
    private String pseudoJoueur;
    private String emailJoueur;
    private String niveauJeu;
    private int niveauPendaison;
    private boolean bonneSaisie = false;
    private boolean sonActif = false;
    private boolean vibreurActif = false;
    private boolean guideActif = false;
    private boolean finJeu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            emailJoueur = savedInstanceState.getString(JEU_JOUEUR_EMAIL);
            pseudoJoueur = savedInstanceState.getString(PenduConsts.JEU_JOUEUR_PSEUDO);
            niveauJeu = savedInstanceState.getString(PenduConsts.JEU_NIVEAU_JEU);
            sonActif = savedInstanceState.getBoolean(PenduConsts.JEU_JOUER_SON);
            guideActif = savedInstanceState.getBoolean(PenduConsts.JEU_AFFICHE_GUIDE);
            vibreurActif = savedInstanceState.getBoolean(PenduConsts.JEU_VIBRER_PHONE);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            emailJoueur = bundle.getString(JEU_JOUEUR_EMAIL);
            pseudoJoueur = bundle.getString(PenduConsts.JEU_JOUEUR_PSEUDO);
            niveauJeu = bundle.getString(PenduConsts.JEU_NIVEAU_JEU);
            sonActif = bundle.getBoolean(PenduConsts.JEU_JOUER_SON);
            guideActif = bundle.getBoolean(PenduConsts.JEU_AFFICHE_GUIDE);
            vibreurActif = bundle.getBoolean(PenduConsts.JEU_VIBRER_PHONE);
        }
        choixNombreCache();
        setContentView(R.layout.activity_main);
        if (!guideActif)
            cacheGuide();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt(PenduConsts.JEU_NOMBRE_CACHE, nombreCache);
        outState.putInt(PenduConsts.JEU_DERNIERE_SAISIE, nombreSaisi);
        outState.putInt(PenduConsts.JEU_NOMBRE_ESSAIS, nombreEssais);
        outState.putString(PenduConsts.JEU_JOUEUR_PSEUDO, pseudoJoueur);
        outState.putString(JEU_JOUEUR_EMAIL, emailJoueur);
        outState.putString(PenduConsts.JEU_NIVEAU_JEU, niveauJeu);
        outState.putBoolean(PenduConsts.JEU_JOUER_SON, sonActif);
        outState.putBoolean(PenduConsts.JEU_VIBRER_PHONE, vibreurActif);
        outState.putBoolean(PenduConsts.JEU_AFFICHE_GUIDE, guideActif);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        nombreEssais = savedInstanceState.getInt(PenduConsts.JEU_NOMBRE_ESSAIS);
        nombreCache = savedInstanceState.getInt(PenduConsts.JEU_NOMBRE_CACHE);
        nombreSaisi = savedInstanceState.getInt(PenduConsts.JEU_DERNIERE_SAISIE);
        emailJoueur = savedInstanceState.getString(JEU_JOUEUR_EMAIL);
        pseudoJoueur = savedInstanceState.getString(PenduConsts.JEU_JOUEUR_PSEUDO);
        niveauJeu = savedInstanceState.getString(PenduConsts.JEU_NIVEAU_JEU);
        sonActif = savedInstanceState.getBoolean(PenduConsts.JEU_JOUER_SON);
        guideActif = savedInstanceState.getBoolean(PenduConsts.JEU_AFFICHE_GUIDE);
        vibreurActif = savedInstanceState.getBoolean(PenduConsts.JEU_VIBRER_PHONE);
    }

    public void initialiserJeu(View view) {
        nombreEssais = 0;
        nombreSaisi = 0;
        nombreCache = 0;
        niveauPendaison = 0;
        finJeu = false;
        bonneSaisie = false;
        findViewById(R.id.resultat).setVisibility(LinearLayout.INVISIBLE);
        findViewById(R.id.btn_valider).setEnabled(true);
        ((TextView) findViewById(R.id.txt_guide)).setText("");
        ((EditText) findViewById(R.id.txt_nombre_saisi)).setText("");
        choixNombreCache();
        affichePendu(view);
    }

    public void choixNombreCache() {
        Random random = new Random();
        switch (niveauJeu) {
            case PenduConsts.JEU_NIVEAU_DEBUTANT:
                nombreCache = random.nextInt(50);
                break;
            case PenduConsts.JEU_NIVEAU_NORMAL:
                nombreCache = random.nextInt(100);
                break;
            case PenduConsts.JEU_NIVEAU_EXPERT:
                nombreCache = random.nextInt(1000);
                break;
            default:
                nombreCache = random.nextInt(100);
                break;
        }
    }

    public void verifieNombre(View view) {

        if (((EditText) findViewById(R.id.txt_nombre_saisi)).getText().toString().length() == 0) {
            ((EditText) findViewById(R.id.txt_nombre_saisi)).setError(getResources().getString(R.string.txt_obligatoire));
            return;
        }
        nombreSaisi = Integer.valueOf(((EditText) findViewById(R.id.txt_nombre_saisi))
                .getText().toString());
        nombreEssais++;

        if (nombreSaisi != nombreCache) {
            niveauPendaison++;
        }

        afficheGuide(view);

        affichePendu(view);

        effetsSurPhone();

        if (bonneSaisie) {
            afficheResultat(view);
            findViewById(R.id.btn_valider).setEnabled(false);
        } else {
            if (niveauPendaison >= 8 && (niveauJeu.equals(PenduConsts.JEU_NIVEAU_DEBUTANT)
                    || niveauJeu.equals(PenduConsts.JEU_NIVEAU_NORMAL))) {
                afficheResultat(view);
                findViewById(R.id.btn_valider).setEnabled(false);
            }
            if (niveauPendaison >= 11 && niveauJeu.equals(PenduConsts.JEU_NIVEAU_EXPERT)) {
                afficheResultat(view);
                findViewById(R.id.btn_valider).setEnabled(false);
            }
        }
        ((EditText) findViewById(R.id.txt_nombre_saisi)).setText("");
    }

    public void affichePendu(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.img_pendu);
        if (niveauJeu.equals(PenduConsts.JEU_NIVEAU_DEBUTANT)
                || niveauJeu.equals(PenduConsts.JEU_NIVEAU_NORMAL)) {
            switch (niveauPendaison) {
                case 0:
                    imageView.setImageResource(R.drawable.pendu_0);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.pendu_1);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.pendu_2);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.pendu_3);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.pendu_4);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.pendu_5);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.pendu_6);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.pendu_7);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.pendu_8);
                    break;
                default:
                    imageView.setImageResource(R.drawable.pendu_0);
                    break;
            }
        } else {
            switch (niveauPendaison) {
                case 0:
                    imageView.setImageResource(R.drawable.pendu_00);
                    break;
                case 1:
                    imageView.setImageResource(R.drawable.pendu_01);
                    break;
                case 2:
                    imageView.setImageResource(R.drawable.pendu_02);
                    break;
                case 3:
                    imageView.setImageResource(R.drawable.pendu_03);
                    break;
                case 4:
                    imageView.setImageResource(R.drawable.pendu_04);
                    break;
                case 5:
                    imageView.setImageResource(R.drawable.pendu_05);
                    break;
                case 6:
                    imageView.setImageResource(R.drawable.pendu_06);
                    break;
                case 7:
                    imageView.setImageResource(R.drawable.pendu_07);
                    break;
                case 8:
                    imageView.setImageResource(R.drawable.pendu_08);
                    break;
                case 9:
                    imageView.setImageResource(R.drawable.pendu_09);
                    break;
                case 10:
                    imageView.setImageResource(R.drawable.pendu_10);
                    break;
                case 11:
                    imageView.setImageResource(R.drawable.pendu_11);
                    break;
                default:
                    imageView.setImageResource(R.drawable.pendu_00);
                    break;
            }
        }

    }

    public void afficheGuide(View view) {
        String message = "";
        TextView textView = (TextView) findViewById(R.id.txt_guide);
        if (nombreSaisi < nombreCache) {
            message = String.valueOf(nombreSaisi) + " " + getResources().getString(R.string.txt_est_inferieur);
            textView.setText(message);
            bonneSaisie = false;
        }
        if (nombreSaisi > nombreCache) {
            message = String.valueOf(nombreSaisi) + " " + getResources().getString(R.string.txt_est_superieur);
            textView.setText(message);
            bonneSaisie = false;
        }
        if (nombreSaisi == nombreCache) {
            textView.setText(R.string.txt_trouve);
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.txt_bravo_vous_avez_trouve), Toast.LENGTH_LONG).show();
            Button button = ((Button) findViewById(R.id.btn_valider));
            button.setEnabled(false);
            bonneSaisie = true;
        }
    }

    public void effetsSurPhone() {
        long vibrationDuration;
        Uri ringUri;
        if (bonneSaisie) {
            vibrationDuration = 500;
            ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        } else {
            vibrationDuration = 250;
            ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }
        if (vibreurActif) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(vibrationDuration);
        }
        if (sonActif) {
            try {
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ringUri);
                r.play();
                r.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cacheGuide() {
        TextView textView = (TextView) findViewById(R.id.txt_guide);
        textView.setVisibility(TextView.INVISIBLE);
        TextView textViewLbl = (TextView) findViewById(R.id.txt_guide_titre);
        textViewLbl.setVisibility(TextView.INVISIBLE);
    }

    public void afficheResultat(View view) {
        findViewById(R.id.resultat).setVisibility(LinearLayout.VISIBLE);
        ((TextView) findViewById(R.id.txt_res_essais)).setText(String.valueOf(nombreEssais));
        ((TextView) findViewById(R.id.txt_res_cache)).setText(String.valueOf(nombreCache));
        saveGameDatas();
        finJeu = true;
    }

    public void saveGameDatas() {
        JeuxDbHelper jeuxDbHelper = new JeuxDbHelper(getApplicationContext());
        SQLiteDatabase db = jeuxDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JeuxDb.ScoreJeux.COLUMN_NAME_EMAIL, emailJoueur);
        values.put(JeuxDb.ScoreJeux.COLUMN_NAME_PSEUDO, pseudoJoueur);
        values.put(JeuxDb.ScoreJeux.COLUMN_NAME_NIVEAU, niveauJeu);
        values.put(JeuxDb.ScoreJeux.COLUMN_NAME_SCORE, nombreEssais);
        values.put(JeuxDb.ScoreJeux.COLUMN_NAME_REUSSITE, bonneSaisie);
        values.put(JeuxDb.ScoreJeux.COLUMN_NAME_DATE, DateUtils.dateHistorique(new Date(), null));

        long newRowId = db.insert(JeuxDb.ScoreJeux.TABLE_NAME, null, values);

        if (db.isOpen())
            db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_jeu_encours, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mn_recommencer:
                recommencerJeu();
                return true;
            case R.id.mn_nouveau:
                initialiserJeu(null);
                return true;
            case R.id.mn_aide:
                showHelp();
                return true;
            case R.id.mn_quitter:
                UIHelper.killApp(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void recommencerJeu() {
        if (finJeu) {
            initialiserJeu(null);
        } else {
            nombreEssais = 0;
            bonneSaisie = false;
            niveauPendaison = 0;
            affichePendu(null);
        }
    }

    public void showHelp() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), HelpActivity.class);
        startActivity(intent);
    }
}
