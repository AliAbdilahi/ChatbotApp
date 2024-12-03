package com.example.dent_e;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Locale;
import java.util.Objects;
import javax.net.ssl.SSLHandshakeException;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    ImageButton btn;
    ImageButton btn2;

    EditText emailtxt;
    EditText passwordtxt;

    ChatbotApi chatbotApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btn = (ImageButton) findViewById(R.id.contbtn);
        btn2 = (ImageButton) findViewById(R.id.signup);

        passwordtxt = findViewById(R.id.password);
        emailtxt = findViewById(R.id.email);

        //setup Retrofit view
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80/")  // Replace with your server IP and port
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotApi = retrofit.create(ChatbotApi.class);

        btn.setOnClickListener((v)->{
            String email = emailtxt.getText().toString();
            String password = passwordtxt.getText().toString();
            String userInformation = (email + " " + password);

            // Now send the user input to the chatbot server

            logintoApp(userInformation,email);
        });



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, signup2.class);
                startActivity(intent);
            }
        });
    }


    private void logintoApp(String message,String email) {
        ChatbotRequest request = new ChatbotRequest(message);
        Call<ChatbotResponse> call = chatbotApi.login(request);

        call.enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful()) {
                    ChatbotResponse chatbotResponse = response.body();
                    if (chatbotResponse != null) {
                        String answer = chatbotResponse.getResponse();

                        if (Objects.equals(answer, "User Authenticated")) {
                            Intent intent= new Intent(Login.this, Homepage.class);
                            intent.putExtra("EXTRA_SESSION_ID", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();
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
