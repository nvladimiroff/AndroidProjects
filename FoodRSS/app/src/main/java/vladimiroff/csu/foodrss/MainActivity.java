package vladimiroff.csu.foodrss;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<String> adapterMainSubjects;
    ListView mainListView;
    Context context;
    //SingleItem selectedItem;

    String[][] urls = {
            {"http://www.food.com/rssapi.do?page_type=26&slug=appetizers", "Appetizers"},
            {"http://www.food.com/rssapi.do?page_type=26&slug=asian", "Asian"},
            {"http://www.food.com/rssapi.do?page_type=26&slug=beef", "Beef"},
            {"http://www.food.com/rssapi.do?page_type=26&slug=breakfast", "Breakfast"},
            {"http://www.food.com/rssapi.do?page_type=26&slug=chicken", "Chicken"},
            {"http://www.food.com/rssapi.do?page_type=28&slug=desserts", "Desserts"},
            {"http://www.food.com/rssapi.do?page_type=26&slug=italian", "Italian"},
            {"http://www.food.com/rssapi.do?page_type=28&slug=mexican", "Mexican"},
            {"http://www.food.com/rssapi.do?page_type=26&slug=pasta", "Pasta"},
            {"http://www.food.com/rssapi.do?page_type=26&slug=stews", "Stew"}
    };

    String[] captions = new String[urls.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        mainListView = (ListView) findViewById(R.id.myListView);

        for(int i=0; i< urls.length; i++) {
            captions[i] = urls[i][1];
        }

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
                String urlAddress = urls[_index][0];
                String urlCaption = urls[_index][1];

                Intent callShowHeadlines = new Intent(MainActivity.this, ShowRecipe.class);

                Bundle myData = new Bundle();
                myData.putString("urlAddress", urlAddress);
                myData.putString("urlTitle", urlCaption);
                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);
            }
        });

        adapterMainSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, captions);
        mainListView.setAdapter(adapterMainSubjects);
    }
}