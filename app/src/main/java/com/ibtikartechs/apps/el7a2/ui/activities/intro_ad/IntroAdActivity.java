package com.ibtikartechs.apps.el7a2.ui.activities.intro_ad;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ibtikartechs.apps.el7a2.MvpApp;
import com.ibtikartechs.apps.el7a2.R;
import com.ibtikartechs.apps.el7a2.data.DataManager;
import com.ibtikartechs.apps.el7a2.ui.activities.base.BaseActivity;
import com.ibtikartechs.apps.el7a2.ui.activities.main.MainActivity;
import com.ibtikartechs.apps.el7a2.ui.activities.main.MainPresenter;
import com.ibtikartechs.apps.el7a2.ui.activities.main_deal_deatails.MainDealDetailsActivity;
import com.ibtikartechs.apps.el7a2.ui_utilities.CustomFontTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroAdActivity extends BaseActivity implements IntroAdMvpView {
    @BindView(R.id.progressBar1)
    ProgressBar countDownProgBar;
    @BindView(R.id.imageView12)
    ImageView imBanner;
    @BindView(R.id.tv_btn_skip_ad)
    CustomFontTextView btnSkipAd;
    @BindView(R.id.progressBar2)
    ProgressBar loadProgBar;
    int total = 0;
    private CountDownTimer cdt;
    private int timePassed;
    Handler handler;
    float density;

    IntroAdPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_ad);
        handler = new Handler(Looper.getMainLooper());
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        changeStatusBarColor();

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        presenter = new IntroAdPresenter(dataManager);
        presenter.onAttach(this);


        btnSkipAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cdt.cancel();
               startActivity(MainActivity.getStartIntent(IntroAdActivity.this));
               finish();
            }
        });

        if (loadProgBar != null) {
            loadProgBar.setIndeterminate(true);
            loadProgBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.white_blue), android.graphics.PorterDuff.Mode.MULTIPLY);
        }

        density = getResources().getDisplayMetrics().density;

        presenter.getBannerAd(density);


/*

        new Thread(new Runnable() {
            public void run() {
                while (total < 100) {
                    total += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(total);

                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


*/






    }


    @Override
    public void startCountDown() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                countDownProgBar.setProgress(0);


                final int oneMin= 5 * 1000; // 1 minute in milli seconds

                // CountDownTimer starts with 1 minutes and every onTick is 1 second
                cdt = new CountDownTimer(oneMin, 30) {

                    public void onTick(final long millisUntilFinished) {

                        total++;
                        // handler.post(new Runnable() {
                        //     @Override
                        //   public void run() {
                        countDownProgBar.setProgress((int)total*100/(oneMin/30));
                        //   }
                        // });


                    }

                    public void onFinish() {
                        // DO something when 1 minute is up
                        total++;
                        // handler.post(new Runnable() {
                        // @Override
                        // public void run() {
                        countDownProgBar.setProgress(100);
                        // }
                        // });
                        startActivity(MainActivity.getStartIntent(IntroAdActivity.this));
                        finish();
                    }
                }.start();

            }
        });



    }

    @Override
    public void setBannerView(final String imgUrl) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (imgUrl.equals("")|| imgUrl == null)
                {}
                else
                    Glide.with(IntroAdActivity.this)
                            .load(imgUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imBanner);
            }
        });

    }

    @Override
    public void hideProgressBar() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (loadProgBar!=null)
                    loadProgBar.setVisibility(View.GONE);
            }
        });
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, IntroAdActivity.class);
        return intent;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
