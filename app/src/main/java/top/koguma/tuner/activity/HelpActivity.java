package top.koguma.tuner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.laputapp.ui.BaseActivity;
import top.koguma.tuner.BuildConfig;
import top.koguma.tuner.R;

public class HelpActivity extends BaseActivity {

    TextView txtCommon;
    TextView txtOther;
    TextView txtSend;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        txtCommon = (TextView) findViewById(R.id.txt_common);
        txtOther = (TextView) findViewById(R.id.txt_other);
        txtSend = (TextView) findViewById(R.id.txt_send);
        txtCommon.setText(Html.fromHtml(getString(R.string.help_common_problems_content)));
        txtOther.setText(Html.fromHtml(getString(R.string.help_other_problems_content)));
        txtSend.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("message/rfc822");
                email.putExtra(Intent.EXTRA_EMAIL,new String[]{BuildConfig.EMAIL_ADDRESS});
                email.putExtra(Intent.EXTRA_SUBJECT, "Help");
                email.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(email, ""));
            }
        });
    }
}
