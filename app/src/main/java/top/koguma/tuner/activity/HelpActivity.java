package top.koguma.tuner.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import com.laputapp.ui.BaseActivity;
import top.koguma.tuner.R;

public class HelpActivity extends BaseActivity {

    TextView txtCommon;
    TextView txtOther;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        txtCommon = (TextView) findViewById(R.id.txt_common);
        txtOther = (TextView) findViewById(R.id.txt_other);
        txtCommon.setText(Html.fromHtml(getString(R.string.help_common_problems_content)));
        txtOther.setText(Html.fromHtml(getString(R.string.help_other_problems_content)));

    }
}
