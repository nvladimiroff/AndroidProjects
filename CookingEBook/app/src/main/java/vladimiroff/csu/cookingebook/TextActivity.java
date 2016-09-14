package vladimiroff.csu.cookingebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        String text = "";

        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            text = extra.getString("recipe");
        }

        TextView fullText = (TextView) findViewById(R.id.fullText);
        fullText.setText(text);
    }
}
