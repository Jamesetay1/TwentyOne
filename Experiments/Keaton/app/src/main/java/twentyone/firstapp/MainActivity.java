package twentyone.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.PushMe);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openPush();
            }
        });
    }


    public void openPush(){
        Intent in = new Intent(this, OpenPush.class);
        startActivity(in);
    }


}
