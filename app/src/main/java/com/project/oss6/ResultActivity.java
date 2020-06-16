package com.project.oss6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ResultActivity extends AppCompatActivity {

    private TextView tv_result;//닉네임 TEXT
    private ImageView iv_profile; // 이미지 프로필
    ImageButton btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName"); // MainActivity로 부터 닉네임 받음
        String photoUrl = intent.getStringExtra("photoUrl"); // MainActivity로 부터 프로필사진 url 받음

        tv_result = findViewById(R.id.tv_result);
        tv_result.setText(nickName); // 닉네임 text를 텍스트뷰에 세팅

        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile); // 프로필 url을 이미지 뷰에 세팅

        btn_next = (ImageButton)findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}