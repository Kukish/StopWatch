package org.kukish.android.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class StopWatchActivity extends Activity {
    //number of tenth of second on the stopwatch
    private int ticks = 0;
    //indicates stopwatch running or not
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            ticks = savedInstanceState.getInt("ticks");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        ticks = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = ticks / 36000;
                int minutes = ((ticks / 10) % 3600) / 60;
                int secs = (ticks / 10) % 60;
                int tenthOfSec = ticks % 10;
                String time = String.format("%d:%02d:%02d.%d", hours, minutes, secs, tenthOfSec);
                timeView.setText(time);
                if (running) {
                    ticks++;
                }
                handler.postDelayed(this, 100);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("ticks", ticks);
        savedInstanceState.putBoolean("running", running);
    }
}
