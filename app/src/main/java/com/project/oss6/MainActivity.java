package com.project.oss6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText _getAge;
    private Button btn_MoveHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       _getAge = findViewById(R.id._getAge);

        btn_MoveHome = findViewById(R.id.btn_MoveHome);
        btn_MoveHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                _getAge.getText();

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

System.out.println("df");