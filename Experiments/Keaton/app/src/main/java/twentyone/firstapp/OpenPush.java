package twentyone.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OpenPush extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        button = findViewById(R.id.Home);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openHome();
            }
        });
    }


    public void openHome(){
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }
}
