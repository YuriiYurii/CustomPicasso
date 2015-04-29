package yuriitsap.example.com.custompicasso;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import yuriitsap.example.com.custompicasso.utils.FixedExecutor;
import yuriitsap.example.com.custompicasso.utils.PicassoUtil;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicassoUtil.with(MainActivity.this)
                        .load("http://i65.photobucket.com/albums/h235/Ignwar/Album%20Clouds/StormWatch.jpg")
                        .placeholder(R.drawable.placeholder)
                        .into(
                                (android.widget.ImageView) findViewById(R.id.image_view));

            }
        });
    }
}