package top.koguma.tuner.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.fastui.uipattern.IRecycler;
import com.laputapp.http.BaseResponse;
import com.laputapp.ui.adapter.RxRecyclerAdapter;
import io.reactivex.Flowable;
import java.util.List;
import top.koguma.tuner.R;
import top.koguma.tuner.model.Product;

public class MainActivity extends AppCompatActivity implements IRecycler<Product> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override public RxRecyclerAdapter<Product> createRecyclerViewAdapter() {
        return null;
    }

    @Override
    public Flowable<? extends BaseResponse<List<Product>>> requestData(String offset, String page, String pageSize) {
        return null;
    }
}
