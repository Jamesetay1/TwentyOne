package com.example.james.hellobuttons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    int mCount = 0;
    TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = findViewById(R.id.show_count);
    }

    public void countDown(View view) {
        mCount--;
        if (mShowCount != null)
            mShowCount.setText(Integer.toString(mCount));
    }

    public void countUp(View view) {
        mCount++;
        if (mShowCount != null)
           mShowCount.setText(Integer.toString(mCount));
    }

    public void LogInScreen(View view) {
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
        startActivity(intent);
    }
}
