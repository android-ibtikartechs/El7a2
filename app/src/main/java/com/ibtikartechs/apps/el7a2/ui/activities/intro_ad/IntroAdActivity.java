package com.ibtikartechs.apps.el7a2.ui.activities.intro_ad;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ibtikartechs.apps.el7a2.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroAdActivity extends AppCompatActivity {
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;
    int total;
    private CountDownTimer cdt;
    private int timePassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_ad);
        ButterKnife.bind(this);
        progressBar.setProgress(total);

        int oneMin= 5 * 1000; // 1 minute in milli seconds

        /** CountDownTimer starts with 1 minutes and every onTick is 1 second */
        cdt = new CountDownTimer(oneMin, 1000) {

            public void onTick(long millisUntilFinished) {

                total = (int) ((timePassed/ 60) * 100);
                progressBar.setProgress(total);
            }

            public void onFinish() {
                // DO something when 1 minute is up
                Toast.makeText(IntroAdActivity.this, "done", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}
