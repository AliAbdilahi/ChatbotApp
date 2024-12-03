package com.example.dent_e;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class FAQ extends AppCompatActivity {
    ImageButton que1;
    ImageButton que2;
    ImageButton que4;
    String sessionId;
    ImageButton bot;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        que1 = findViewById(R.id.q101);
        que2 = findViewById(R.id.q102);
        que4 = findViewById(R.id.q104);
        bot = findViewById(R.id.botbtn);
        que1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = "What is STI?";
                Intent intent = new Intent(FAQ.this, Chatpage.class);
                intent.putExtra("keyname", question);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        que2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = "What kind of tools should I obtain for STI treatment?";
                Intent intent = new Intent(FAQ.this, Chatpage.class);
                intent.putExtra("keyname", question);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        que4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = "What cement do I use for implant crown cementation?";
                Intent intent = new Intent(FAQ.this, Chatpage.class);
                intent.putExtra("keyname", question);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FAQ.this, Chatpage.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });

    }


}
