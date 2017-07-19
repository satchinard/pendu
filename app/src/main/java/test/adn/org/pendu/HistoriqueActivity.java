package test.adn.org.pendu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import test.adn.org.pendu.adapter.JeuxScoreArrayAdapter;

public class HistoriqueActivity extends AppCompatActivity {

    private ListView listView;
    private JeuxScoreArrayAdapter jsaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        listView = (ListView) findViewById(R.id.lv_scores);
        listView.setAdapter(new JeuxScoreArrayAdapter(this, JeuxScoreArrayAdapter
                .getScoreDatas(this)));
        TextView textView = new TextView(getApplicationContext());
        textView.setText(R.string.txt_meilleurs_scores);
        textView.setTextColor(getResources().getColor(R.color.colorNiveau));
        listView.addHeaderView(textView);
        registerForContextMenu(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_historique, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_tout_supprimer:
                JeuxScoreArrayAdapter.deleteAll(getApplicationContext());
                Toast.makeText(this, R.string.tst_historique_supprime, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
