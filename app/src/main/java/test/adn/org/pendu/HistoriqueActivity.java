package test.adn.org.pendu;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import test.adn.org.pendu.adapter.JeuxScoreArrayAdapter;

public class HistoriqueActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        listView.setAdapter(new JeuxScoreArrayAdapter(this, JeuxScoreArrayAdapter
                .getScoreDatas(this)));
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    }
}
