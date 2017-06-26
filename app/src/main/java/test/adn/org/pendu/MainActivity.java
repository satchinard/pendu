package test.adn.org.pendu;

import android.media.Image;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static final String JEU_NOMBRE_CACHE = "jeu_nombre_cache";
    static final String JEU_NOMBRE_ESSAIS = "jeu_nombre_essais";
    static final String JEU_DERNIERE_SAISIE = "jeu_derniere_saisie";

    static final String JEU_TEMPS_ECOULE = "jeu_temps_ecoule";

    static final String JEU_JOUEUR_PSEUDO = "jeu_joueur_pseudo";
    static final String JEU_JOUEUR_EMAIL = "jeu_joueur_email";

    static final String JEU_NIVEAU_INTERVAL = "jeu_niveau_interval";
    static final String JEU_NIVEAU_TEMPS = "jeu_niveau_temps";

    private int nombreCache;
    private int nombreEssais;
    private int nombreSaisi;
    private int niveauPendaison;
    private boolean bonneSaisie = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        choixNombre();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void initialiserJeu(View view) {
        nombreEssais = 0;
        nombreSaisi = 0;
        nombreCache = 0;
        niveauPendaison = 0;
        ((LinearLayout) findViewById(R.id.resultat)).setVisibility(LinearLayout.INVISIBLE);
        ((Button) findViewById(R.id.btn_valider)).setEnabled(true);
        ((TextView) findViewById(R.id.txt_guide)).setText("");
        choixNombre();
        affichePendu(view);
    }

    public void choixNombre() {
        Random random = new Random();
        nombreCache = random.nextInt(100);
    }

    public void verifieNombre(View view) {
        nombreSaisi = (int) Integer.valueOf(((EditText) findViewById(R.id.txt_nombre_saisi)).getText().toString());
        if (nombreSaisi != nombreCache) {
            niveauPendaison++;
            nombreEssais++;
        }
        afficheGuide(view);
        affichePendu(view);
        if (niveauPendaison >= 8 || bonneSaisie) {
            afficheResultat(view);
            ((Button) findViewById(R.id.btn_valider)).setEnabled(false);
        }
    }

    public void affichePendu(View view) {
        ImageView imageView = (ImageView) findViewById(R.id.img_pendu);
        switch (niveauPendaison) {
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
        }
    }

    public void afficheGuide(View view) {
        TextView textView = (TextView) findViewById(R.id.txt_guide);
        if (nombreSaisi < nombreCache) {
            textView.setText("Votre nombre est inférieur.");
        }
        if (nombreSaisi > nombreCache) {
            textView.setText("Votre nombre est supérieur.");
        }
        if (nombreSaisi == nombreCache) {
            textView.setText("Trouvé !");
            Toast.makeText(getApplicationContext(), "Bravo! Vous avez trouvé.", Toast.LENGTH_LONG);
            Button button = ((Button) findViewById(R.id.btn_valider));
            button.setEnabled(false);
            bonneSaisie = true;
        }

    }

    public void afficheResultat(View view) {
        LinearLayout linearLayout = ((LinearLayout) findViewById(R.id.resultat));
        linearLayout.setVisibility(LinearLayout.VISIBLE);
        TextView textEssais = ((TextView) findViewById(R.id.txt_res_essais));
        textEssais.setText(String.valueOf(nombreEssais));
        TextView textCache = ((TextView) findViewById(R.id.txt_res_cache));
        textCache.setText(String.valueOf(nombreCache));
    }
}
