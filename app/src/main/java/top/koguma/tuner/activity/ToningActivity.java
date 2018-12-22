package top.koguma.tuner.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import top.koguma.tuner.Navigator;
import top.koguma.tuner.R;
import top.koguma.tuner.audio.ASoundThread;
import top.koguma.tuner.model.Sound;
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

    /**
     * 声音请求权限信息
     */
    private static final int PERMISSION_AUDIORECORD = 2;

    /**
     * 采集声音开关
     */
    private TextView btnStart;

    /**
     * 声音信息
     */
    public static int SOUND_MESSAGE = 1;
    private top.koguma.tuner.audio.ASoundThread ASoundThread;
    /**
     * 当前的频率
     */
    private int currentFrequency;
    /**
     * 线程之间通讯
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Sound sound = (Sound) msg.obj;
                    //update ui
                    indicator.setDeltaFrequency(296f, (float) sound.mFrequency);
                    break;
            }
        }
    };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toning);
        initViews();

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
        btnStart = (TextView) findViewById(R.id.txt_start);
        btnStart.setOnClickListener(this);
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
            case R.id.txt_start:
                startToning();
                break;
            default:
        }
    }

    public void updateBtnStringsStatus(View v) {
        if (v instanceof TextView && v.getId() != R.id.txt_start) {
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

    public void startToning() {
        if (btnStart.isSelected()) {
            btnStart.setText(R.string.start);
            btnStart.setSelected(false);
            stopAnalysis();
        } else {
            btnStart.setText(R.string.stop);
            btnStart.setSelected(true);
            //判断是否有权限
            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {
                //如果应用之前请求过此权限但用户拒绝的请求 ,此方法返回true
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                    //这里可以写个对话框之类的项向用户解释为什么要申请权限，
                    // 并在对话框的确认键后续再次申请权限
                } else {
                    ActivityCompat.requestPermissions(this,
                        new String[] { Manifest.permission.RECORD_AUDIO },
                        PERMISSION_AUDIORECORD);
                }

            } else {
                startAnalysis();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_AUDIORECORD) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    startAnalysis();
                }

            }
        }
    }

    @Override protected void onStop() {
        super.onStop();
        stopAnalysis();
    }

    /**
     * 开始采集
     */
    private void startAnalysis() {
        ASoundThread = new ASoundThread(handler);
        ASoundThread.start();
    }

    /**
     * 停止采集音频
     */
    private void stopAnalysis() {
        if (ASoundThread != null) {
            ASoundThread.close();
        }

    }

    public void updateInstrumentPicture(int curPos) {

    }
}
