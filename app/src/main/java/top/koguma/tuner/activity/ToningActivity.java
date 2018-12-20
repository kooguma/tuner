package top.koguma.tuner.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import top.koguma.tuner.Navigator;
import top.koguma.tuner.R;
import top.koguma.tuner.view.TitleSwitchTab;
import top.koguma.tuner.view.ToningIndicator;

public class ToningActivity extends TunerBaseActivity implements View.OnClickListener {

    TextView btnHelp;

    TextView[] btnStrings = new TextView[4];
    ImageView imageView;

    TitleSwitchTab tabSwitch;
    ToningIndicator indicator;

    int[] titles = new int[] { R.string.toning_violin, R.string.toning_viola,
        R.string.toning_cello };

    int currentTitle = titles[0];

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toning);
        initViews();
        registerSubscription(
            Flowable
                .interval(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override public void accept(Long aLong) throws Exception {
                        //随机正负号
                        double sign = Math.random() > 0.5 ? 1d : -1d;
                        //随机频率 -150 到 150
                        double frequency = sign * Math.random() * 150;
                        indicator.setDeltaFrequency((float) frequency);
                    }
                })
                .subscribe()
        );

    }

    void initViews() {
        indicator = (ToningIndicator) findViewById(R.id.indicator);
        btnHelp = (TextView) findViewById(R.id.btn_help);
        btnHelp.setOnClickListener(this);
        tabSwitch = (TitleSwitchTab) findViewById(R.id.tab_switch);
        tabSwitch.setTabs(titles);
        tabSwitch.setOnTabChangeListener(new TitleSwitchTab.OnTabChangeListener() {
            @Override public void onTabChange(int curPos) {
                updateBtnStringsName(curPos);
            }
        });

        btnStrings[0] = (TextView) findViewById(R.id.btn_string_1);
        btnStrings[1] = (TextView) findViewById(R.id.btn_string_2);
        btnStrings[2] = (TextView) findViewById(R.id.btn_string_3);
        btnStrings[3] = (TextView) findViewById(R.id.btn_string_4);

        for (TextView t : btnStrings) {
            t.setOnClickListener(this);
        }

        imageView = (ImageView) findViewById(R.id.img_instrument);
    }

    @Override public void onClick(View v) {
        updateBtnStringsStatus(v);
        switch (v.getId()) {
            case R.id.btn_help:
                Navigator.startHelpActivity(this);
                break;
            case R.id.btn_string_1:
                imageView.setImageResource(R.drawable.rough_1);
                break;
            case R.id.btn_string_2:
                imageView.setImageResource(R.drawable.rough_2);
                break;
            case R.id.btn_string_3:
                imageView.setImageResource(R.drawable.rough_3);
                break;
            case R.id.btn_string_4:
                imageView.setImageResource(R.drawable.rough_4);
                break;
            default:
        }
    }

    public void updateBtnStringsStatus(View v) {
        if (v instanceof TextView) {
            v.setActivated(true);
            for (TextView btn : btnStrings) {
                if (v != btn) btn.setActivated(false);
            }
        }
    }

    public void updateBtnStringsName(int curPos) {
        switch (curPos) {
            case 0:
                btnStrings[0].setText(R.string.toning_string_d);
                btnStrings[1].setText(R.string.toning_string_g);
                btnStrings[2].setText(R.string.toning_string_a);
                btnStrings[3].setText(R.string.toning_string_e);
                break;
            case 1:
                btnStrings[0].setText(R.string.toning_string_c);
                btnStrings[1].setText(R.string.toning_string_g);
                btnStrings[2].setText(R.string.toning_string_d);
                btnStrings[3].setText(R.string.toning_string_a);
                break;
            case 2:
                btnStrings[0].setText(R.string.toning_string_c);
                btnStrings[1].setText(R.string.toning_string_g);
                btnStrings[2].setText(R.string.toning_string_d);
                btnStrings[3].setText(R.string.toning_string_a);
                break;
            default:
                break;
        }
    }

    public void updateInstrumentPicture(int curPos) {

    }
}
