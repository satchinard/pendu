package test.adn.org.pendu;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import test.adn.org.pendu.adapter.JeuxScoreArrayAdapter;

public class HistoriqueActivity extends AppCompatActivity {

    private ListView listView;
    private JeuxScoreArrayAdapter jsaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);
        populateListView();
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
                JeuxScoreArrayAdapter.deleteAll(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder
//                        .setTitle(R.string.app_name)
                        .setMessage(R.string.tst_historique_supprime)
                        .setPositiveButton(R.string.txt_ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(R.drawable.ic_info_user)
                ;
                builder.create().show();
                updateListView();
//                Toast.makeText(this, R.string.tst_historique_supprime, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void populateListView() {
        listView = (ListView) findViewById(R.id.lv_scores);
        listView.setAdapter(new JeuxScoreArrayAdapter(this, JeuxScoreArrayAdapter
                .getScoreDatas(this)));
        TextView textView = new TextView(getApplicationContext());
        textView.setText(R.string.txt_meilleurs_scores);
        textView.setTextColor(getResources().getColor(R.color.colorNiveau));
        listView.addHeaderView(textView);
        registerForContextMenu(listView);
    }

    private void updateListView() {
        listView = (ListView) findViewById(R.id.lv_scores);
        listView.setAdapter(new JeuxScoreArrayAdapter(this, JeuxScoreArrayAdapter
                .getScoreDatas(this)));
    }

}
