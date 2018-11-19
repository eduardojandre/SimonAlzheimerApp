package uff.simonalzheimer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, Main2Activity.class));
        //TODO: Connect to ContextNet to get enabled routines and possible routines.
        finish();
    }

}
