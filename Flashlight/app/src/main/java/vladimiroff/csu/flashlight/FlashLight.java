package vladimiroff.csu.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class FlashLight extends Activity {

    RelativeLayout layout;
    Thread rainbowWorker;
    SharedPreferences sharedPref;
    String currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        setContentView(R.layout.activity_flash_light);
        layout = (RelativeLayout) findViewById(R.id.MyLayout);

        restoreState(sharedPref.getString("color", "White"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("color", currentColor);
        editor.commit();
    }

    private void restoreState(String s) {
        if(s.equals("rainbow")) {
            onClickRainbow(null);
        } else {
            layout.setBackgroundColor(Color.parseColor(s));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flash_light, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        // Kill the background thread if it's running.
        if(rainbowWorker != null) {
            rainbowWorker.interrupt();
            rainbowWorker = null;
        }

        Button b = (Button) view;
        currentColor = b.getText().toString();
        layout.setBackgroundColor(Color.parseColor(currentColor));
    }

    public void onClickRainbow(View view) {
        currentColor = "rainbow";

        // Make sure we don't start the thread if it's already running.
        if(rainbowWorker == null) {
            // Lambdas would be really useful here.
            rainbowWorker = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Run a for loop forever and send a request to the UI thread to change
                        // the background color every 15 milliseconds.
                        for (int x = 0; ; x++) {
                            final float[] hsv = {x % 360, 1.0f, 1.0f};
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    layout.setBackgroundColor(Color.HSVToColor(hsv));
                                }
                            });
                            Thread.sleep(15);
                        }
                    } catch (InterruptedException e) {
                        // Just end the thread.
                    }
                }
            });
            rainbowWorker.start();
        }
    }
}
