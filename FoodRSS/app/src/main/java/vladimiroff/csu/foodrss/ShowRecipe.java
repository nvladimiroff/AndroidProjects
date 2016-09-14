package vladimiroff.csu.foodrss;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowRecipe extends AppCompatActivity {
    ArrayList<SingleItem> recipeList = new ArrayList<>();
    ListView listView;
    String url = "";
    String title = "";
    SingleItem selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent callingIntent = getIntent();

        Bundle myBundle = callingIntent.getExtras();
        url = myBundle.getString("urlAddress");
        title = myBundle.getString("urlTitle");

        listView = (ListView) findViewById(R.id.myListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int index, long id)
            {
                selectedItem = recipeList.get(index);
                showNiceDialogBox(selectedItem, getApplicationContext());
            }
        });

        DownloadRssFeed downloader = new DownloadRssFeed(ShowRecipe.this);
        downloader.execute(url, title);
    }

    public void showNiceDialogBox(SingleItem selectedItem, Context context) {
        String title = selectedItem.getTitle();
        String description = selectedItem.getDescription();
        if (title.toLowerCase().equals(description.toLowerCase())) {
            description = "";
        }
        try {
            final Uri storyLink = Uri.parse(selectedItem.getLink());
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
            myBuilder
                    .setTitle((Html.fromHtml(this.title)))
                    .setMessage(title + "\n\n" + Html.fromHtml(description + "\n"))
                    .setPositiveButton("Close", null)
                    .setNegativeButton("More", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browser = new Intent(Intent.ACTION_VIEW, storyLink);
                            startActivity(browser);
                        }
                    }).show();
        } catch (Exception e) {
            Log.e("Error DialogBox", e.getMessage());
        }
    }

}
