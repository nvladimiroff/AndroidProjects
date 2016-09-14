package vladimiroff.csu.cookingebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        int image = 0;

        Bundle extra = getIntent().getExtras();

        if(extra != null) {
            image = extra.getInt("image");
        }

        ImageView fullView = (ImageView) findViewById(R.id.fullView);
        fullView.setImageResource(image);
    }
}
