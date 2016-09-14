package vladimiroff.csu.cookiemonster;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainActivity extends AppCompatActivity {
    final int MONSTER_UPDATE = 1;
    final int TIME_UPDATE = 2;
    final int COOKIE_BAKE_UPDATE = 3;

    ProgressBar timeProgressBar;
    ProgressBar progressBar1;
    ProgressBar progressBar2;

    TextView clockText;
    TextView cookiesEaten1;
    TextView cookiesEaten2;

    TextView cookiesBaked;

    SimulationClock clock;
    GrandmasOven grandma;
    CookieMonster monster1;
    CookieMonster bigTasty;

    int totalCookiesBaked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeProgressBar = (ProgressBar) findViewById(R.id.timeProgressBar);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        clockText = (TextView) findViewById(R.id.clockText);
        cookiesEaten1 = (TextView) findViewById(R.id.cookiesEaten1);
        cookiesEaten2 = (TextView) findViewById(R.id.cookiesEaten2);

        cookiesBaked = (TextView) findViewById(R.id.cookiesBaked);

        startThreads();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MONSTER_UPDATE) {
                int cookiesEaten = msg.arg1;
                int monsterID = msg.arg2;

                if(monsterID == 1) {
                    progressBar1.incrementProgressBy(cookiesEaten);
                    cookiesEaten1.setText(progressBar1.getProgress() + " cookies eaten so far.");
                } else if(monsterID == 2) {
                    progressBar2.incrementProgressBy(cookiesEaten);
                    cookiesEaten2.setText(progressBar2.getProgress() + " cookies eaten so far.");
                }
            } else if(msg.what == TIME_UPDATE) {
                int percentage = msg.arg1;
                int timeElapsed = msg.arg2;
                timeProgressBar.setProgress(percentage);
                clockText.setText(timeElapsed/1000 + " / 120 sec.");
            } else if(msg.what == COOKIE_BAKE_UPDATE) {
                int baked = (int) msg.obj;
                totalCookiesBaked += baked;
                cookiesBaked.setText(totalCookiesBaked + " total cookies baked so far.");
            }
        }
    };

    public void startThreads() {
        clock = new SimulationClock(120*1000);
        clock.start();

        CookieJar cookieJar = new CookieJar();
        grandma = new GrandmasOven(cookieJar, clock);
        monster1 = new CookieMonster(cookieJar, clock, 1);
        bigTasty = new CookieMonster(cookieJar, clock, 2);

        grandma.start();
        monster1.start();
        bigTasty.start();
    }

    public void onStartOver(View view) {
        onCancel(view);

        startThreads();
    }

    public void onCancel(View view) {
        clock.interrupt();
        grandma.interrupt();
        monster1.interrupt();
        bigTasty.interrupt();

        progressBar1.setProgress(0);
        progressBar2.setProgress(0);
        timeProgressBar.setProgress(0);
        cookiesEaten1.setText("0 cookies eaten so far.");
        cookiesEaten2.setText("0 cookies eaten so far.");
        clockText.setText("0 / 120 sec.");
        cookiesBaked.setText("0 total cookies baked so far.");
        totalCookiesBaked = 0;

    }

    class CookieMonster extends Thread {
        private CookieJar cookiejar;
        private SimulationClock clock;
        private int monsterID;
        private Random rand = new Random();

        public CookieMonster(CookieJar cookiejar, SimulationClock clock, int monsterID) {
            this.cookiejar = cookiejar;
            this.clock = clock;
            this.monsterID = monsterID;
        }

        @Override
        public void run() {
            for (int i = 0; clock.currentClock < clock.totalSimulationTime; i++) {
                int value = cookiejar.takeCookies();
                Message androMsg = handler.obtainMessage(MONSTER_UPDATE, value, monsterID);
                handler.sendMessage(androMsg);
                try {
                    Thread.sleep(rand.nextInt(6) * 1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    class SimulationClock extends Thread {
        Integer totalSimulationTime;
        Integer currentClock = 0;

        public SimulationClock(Integer totalSimulationTime) {
            this.totalSimulationTime = totalSimulationTime;
        }

        @Override
        public void run() {
            int sleepTime = 1000;
            while (currentClock < totalSimulationTime) {
                try {
                    Thread.sleep(sleepTime);
                    currentClock += sleepTime;
                    Message androMsg = handler.obtainMessage(TIME_UPDATE, currentClock*100 / totalSimulationTime, currentClock);
                    handler.sendMessage(androMsg);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }

    class CookieJar {
        private int cookie = 0;
        Random rand = new Random();

        ReadWriteLock rwLock = new ReentrantReadWriteLock();

        public int takeCookies() {
            rwLock.writeLock().lock();
            if (cookie != 0) {
                int cookiesEaten = rand.nextInt(cookie);

                cookie -= cookiesEaten;

                rwLock.writeLock().unlock();

                return cookiesEaten;
            } else {
                rwLock.writeLock().unlock();

                return 0;
            }
        }

        public int bakeCookies() {
            rwLock.writeLock().lock();

            int cookiesBaked = rand.nextInt(11);
            cookie += cookiesBaked;

            rwLock.writeLock().unlock();

            return cookiesBaked;
        }
    }

    class GrandmasOven extends Thread {
        private CookieJar cookieJar;
        private SimulationClock clock;

        public GrandmasOven(CookieJar cookieJar, SimulationClock clock) {
            this.cookieJar = cookieJar;
            this.clock = clock;
        }

        @Override
        public void run() {
            for(int i=0; clock.currentClock < clock.totalSimulationTime; i++) {
                int cookiesBaked = cookieJar.bakeCookies();
                Message androMsg = handler.obtainMessage(COOKIE_BAKE_UPDATE, cookiesBaked);
                handler.sendMessage(androMsg);

                try {
                    sleep(5000);
                } catch(InterruptedException e) {
                    break;
                }
            }
        }
    }
}

