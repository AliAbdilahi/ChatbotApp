package com.example.dent_e;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class profilepg extends AppCompatActivity {
    Button btn;
    EditText Username;
    EditText email;
    String sessionId;
    ChatbotApi chatbotApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        btn = (Button) findViewById(R.id.logout);
        Username = (EditText) findViewById(R.id.editText);
        email = (EditText) findViewById(R.id.editText2);
        email.setText(sessionId);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80/")  // Replace with your server IP and port
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotApi = retrofit.create(ChatbotApi.class);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(profilepg.this, Login.class);
                startActivity(intent);
            }
        });
        loadprofile(sessionId);
    }

    private void loadprofile(String message) {
        ChatbotRequest request = new ChatbotRequest(message);
        Call<ChatbotResponse> call = chatbotApi.loadprofile(request);

        call.enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful()) {
                    ChatbotResponse chatbotResponse = response.body();
                    if (chatbotResponse != null) {
                        String answer = chatbotResponse.getResponse();

                        if (answer!= null) {
                            Username.setText(answer);
                        } else {
                            String[] arrOfStr = answer.split(",");
                            for (String a : arrOfStr)
                                Toast.makeText(getApplicationContext(), "Unable to get ur profile", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Server not Responding, Try again latter", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatbotResponse> call, Throwable t) {
                if (t instanceof SSLHandshakeException) {
                    Log.e("TAG", "SSL Handshake Error", t);
                } else {
                    Log.e("TAG", "Failed to connect to Chatbot server", t);
                }
                //outputTextView.setText("\nFailed to connect to Chatbot server");
            }
        });
    }
}
