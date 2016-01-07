package org.kukish.android.stopwatch;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopWatchActivity extends Activity {
    //number of tenth of second on the stopwatch
    private int ticks = 0;
    //indicates stopwatch running or not
    private boolean running;

    private Button startPauseButton;
    private Drawable startIcon;
    private Drawable pauseIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        startPauseButton = (Button) findViewById(R.id.start_pause_button);
        startIcon = getResources().getDrawable(R.drawable.start);
        pauseIcon = getResources().getDrawable(R.drawable.pause);

        if (savedInstanceState != null) {
            ticks = savedInstanceState.getInt("ticks");
            running = savedInstanceState.getBoolean("running");
            if (running) {
                startPauseButton.setText(R.string.pasue);
                startPauseButton.setCompoundDrawablesWithIntrinsicBounds(null , null, pauseIcon, null);
            } else if (ticks > 0) {
                startPauseButton.setText(R.string.resume);
            }
        }
        runTimer();
    }

    public void onClickStartPause(View view) {
        if (running) {
            running = false;
            startPauseButton.setText(ticks > 0 ? R.string.resume : R.string.pasue);
            startPauseButton.setCompoundDrawablesWithIntrinsicBounds(null, null, startIcon, null);
        } else {
            running = true;
            startPauseButton.setText(R.string.pasue);
            startPauseButton.setCompoundDrawablesWithIntrinsicBounds(null, null, pauseIcon, null);
        }
    }

    public void onClickReset(View view) {
        running = false;
        ticks = 0;
        startPauseButton.setText(R.string.start);
        startPauseButton.setCompoundDrawablesWithIntrinsicBounds(null, null, startIcon, null);
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
