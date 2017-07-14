package test.adn.org.pendu;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import test.adn.org.pendu.adapter.MobileArrayAdapter;

public class MobileActivity extends ListActivity {

//    static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
//            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
//            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };

    static final String[] MOBILE_OS =
            new String[]{"Android", "iOS", "WindowsMobile", "Blackberry"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setListAdapter(new ArrayAdapter<String>(this,
//                R.layout.activity_historique,FRUITS));
//
//        ListView listView = getListView();
//        listView.setTextFilterEnabled(true);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // When clicked, show a toast with the TextView text
//                Toast.makeText(getApplicationContext(),
//                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });


//        String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME,
//                ContactsContract.CommonDataKinds.Phone.NUMBER};
//        int[] toViews = {R.id.display_name, R.id.phone_number};
//
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
//                R.layout.person_name_and_number, cursor, fromColumns, toViews, 0);

//        ListView listView = getListView();
//        listView.setAdapter(adapter);

        setListAdapter(new MobileArrayAdapter(this, MOBILE_OS));
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        //get selected items
        String selectedValue = (String) getListAdapter().getItem(position);
        Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

    }
}
