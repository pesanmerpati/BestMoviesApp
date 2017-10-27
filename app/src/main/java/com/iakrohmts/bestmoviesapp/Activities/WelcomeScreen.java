package com.iakrohmts.bestmoviesapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import com.iakrohmts.bestmoviesapp.R;

public class WelcomeScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_screen);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(new Intent(WelcomeScreen.this, MainActivity.class));
        finish();
      }
    },0);
  }
}
