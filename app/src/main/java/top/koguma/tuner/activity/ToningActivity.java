package top.koguma.tuner.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import top.koguma.tuner.Navigator;
import top.koguma.tuner.R;

public class ToningActivity extends TunerBaseActivity implements View.OnClickListener {

    TextView btnHelp;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toning);
        initViews();
    }

    void initViews(){
        btnHelp = (TextView) findViewById(R.id.btn_help);
        btnHelp.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        Navigator.startHelpActivity(this);
    }
}
