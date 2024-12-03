package com.example.dent_e;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class categories extends AppCompatActivity {
    ImageButton mbtn1;
    String sessionId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        mbtn1 = (ImageButton) findViewById(R.id.imagebtn1);

        mbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(categories.this,Faqs2.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
    }
}
