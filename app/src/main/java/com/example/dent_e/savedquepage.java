package com.example.dent_e;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;


import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import javax.net.ssl.SSLHandshakeException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class savedquepage extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView rv;
    ArrayList<String> dataSource;
    LinearLayoutManager linearLayoutManager;
    savedquepage.MyRvAdapter myRvAdapter;
    ImageButton imgbtn1;
    ImageButton imgbtn;
    ImageButton imgbtn3;
    ImageButton imgbtn4;
    ChatbotApi chatbotApi;
    String sessionId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savedques);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        rv = findViewById(R.id.verticalRv);
        //Setting the que source
        dataSource = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(savedquepage.this, LinearLayoutManager.VERTICAL, false);
        myRvAdapter = new savedquepage.MyRvAdapter(dataSource,this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(myRvAdapter);
        imgbtn = (ImageButton) findViewById(R.id.userbtn);
        imgbtn3 = (ImageButton) findViewById(R.id.catergoriesbtn);
        imgbtn1 = (ImageButton) findViewById(R.id.homebtn1);



        //setup Retrofit view
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80/")  // Replace with your server IP and port
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotApi = retrofit.create(ChatbotApi.class);


        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(savedquepage.this,Chatpage.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(savedquepage.this,profilepg.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(savedquepage.this, categories.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });

        loadmessages(sessionId);
    }
    @Override
    public void onitemclick(int position) {
        /*String question = "What cement do I use for implant crown cementation?";*/
        Intent intent = new Intent(savedquepage.this, Chatpage.class);
        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        intent.putExtra("keyname", dataSource.get(position));
        startActivity(intent);
    }

    class MyRvAdapter extends RecyclerView.Adapter<savedquepage.MyRvAdapter.MyHolder> {
        private  RecyclerViewInterface recyclerViewInterface;

        ArrayList<String> data;


        public MyRvAdapter(ArrayList<String> data, RecyclerViewInterface recyclerViewInterface) {
            this.data = data;
            this.recyclerViewInterface = recyclerViewInterface;
        }

        @NonNull
        @Override
        public savedquepage.MyRvAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(savedquepage.this).inflate(R.layout.faq_item, parent, false);
            return new savedquepage.MyRvAdapter.MyHolder(view, recyclerViewInterface);
        }

        @Override
        public void onBindViewHolder(@NonNull savedquepage.MyRvAdapter.MyHolder holder, int position) {
            holder.savedque.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView savedque;

            public MyHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
                super(itemView);
                savedque = itemView.findViewById(R.id.savedque);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (recyclerViewInterface != null){
                            int pos = getAdapterPosition();
                            if (pos!= RecyclerView.NO_POSITION){
                                recyclerViewInterface.onitemclick(pos);
                            }
                        }
                    }
                });
            }
        }
    }
    void addToChat(String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message.isEmpty()){
                    dataSource.add("there is no saved questions yet");
                }
                else {
                    dataSource.add(message);
                    myRvAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void loadmessages(String message) {
        ChatbotRequest request = new ChatbotRequest(message);
        Call<ChatbotResponse> call = chatbotApi.loadmessages(request);

        call.enqueue(new Callback<ChatbotResponse>() {
            @Override
            public void onResponse(Call<ChatbotResponse> call, Response<ChatbotResponse> response) {
                if (response.isSuccessful()) {
                    ChatbotResponse chatbotResponse = response.body();
                    if (chatbotResponse != null) {
                        String answer = chatbotResponse.getResponse();

                        if (Objects.equals(answer, "No Saved Messages")) {
                            Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_SHORT).show();
                        } else {
                            String[] arrOfStr = answer.split(",");
                            for (String a : arrOfStr)
                                addToChat(a);
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

