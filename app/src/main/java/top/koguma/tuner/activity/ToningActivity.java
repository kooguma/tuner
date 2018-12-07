package top.koguma.tuner.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import top.koguma.tuner.Navigator;
import top.koguma.tuner.R;
import top.koguma.tuner.view.TitleSwitchTab;
import top.koguma.tuner.view.ToningIndicator;

public class ToningActivity extends TunerBaseActivity implements View.OnClickListener {

    TextView btnHelp;

    TextView[] btnStrings = new TextView[4];

    TitleSwitchTab tabSwitch;
    ToningIndicator indicator;

    int[] titles = new int[] { R.string.toning_violin, R.string.toning_viola,
        R.string.toning_cello };

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toning);
        initViews();
    }

    void initViews() {
        btnHelp = (TextView) findViewById(R.id.btn_help);
        btnHelp.setOnClickListener(this);
        tabSwitch = (TitleSwitchTab) findViewById(R.id.tab_switch);
        tabSwitch.setTabs(titles);
        tabSwitch.setOnTabChangeListener(new TitleSwitchTab.OnTabChangeListener() {
            @Override public void onTabChange(int curPos) {

            }
        });

        btnStrings[0] = (TextView) findViewById(R.id.btn_string_g);
        btnStrings[1] = (TextView) findViewById(R.id.btn_string_d);
        btnStrings[2] = (TextView) findViewById(R.id.btn_string_a);
        btnStrings[3] = (TextView) findViewById(R.id.btn_string_e);

        for (TextView t : btnStrings) {
            t.setOnClickListener(this);
        }
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_help:
                Navigator.startHelpActivity(this);
                break;
            case R.id.btn_string_g:
                btnStrings[0].setActivated(true);
                btnStrings[1].setActivated(false);
                btnStrings[2].setActivated(false);
                btnStrings[3].setActivated(false);
                break;
            case R.id.btn_string_d:
                btnStrings[0].setActivated(false);
                btnStrings[1].setActivated(true);
                btnStrings[2].setActivated(false);
                btnStrings[3].setActivated(false);
                break;
            case R.id.btn_string_a:
                btnStrings[0].setActivated(false);
                btnStrings[1].setActivated(false);
                btnStrings[2].setActivated(true);
                btnStrings[3].setActivated(false);
                break;
            case R.id.btn_string_e:
                btnStrings[0].setActivated(false);
                btnStrings[1].setActivated(false);
                btnStrings[2].setActivated(false);
                btnStrings[3].setActivated(true);
                break;
            default:
        }
    }

    // public void updateBtnStrings(View v) {
    //     for (TextView btn : btnStrings) {
    //         if (btn == v) {
    //             v.setActivated(true);
    //         } else {
    //             v.setActivated(false);
    //         }
    //     }
    // }
}
