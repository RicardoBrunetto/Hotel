package curso.android.actionbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Camargo on 17/09/2017.
 */

public class OtherPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.other_main);
        TextView txt = (TextView)findViewById(R.id.txtOtherMain);

        Intent it = getIntent();

        txt.setText(it.getStringExtra("name"));
    }
}
