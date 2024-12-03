package com.example.dent_e;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Homepage extends AppCompatActivity implements RecyclerViewInterface, RecyclerViewInterface2{
    RecyclerView rv;
    RecyclerView rv2;
    ArrayList<String> dataSource;
    ArrayList<String> saveSource;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter myRvAdapter;
    MyRvAdapter2 myRvAdapter2;
    ImageButton imgbtn1;
    ImageButton imgbtn2;
    ImageButton imgbtn3;
    ImageButton imgbtn5;
    ImageButton imgbtn6;
    ImageButton imgbtn7;
    Button imgbtn4;
    ChatbotApi chatbotApi;
    String sessionId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        setContentView(R.layout.homepage);
        rv = findViewById(R.id.horizontalRv);
        rv2 = findViewById(R.id.horizontalRv2);
        //Setting the data source
        dataSource = new ArrayList<>();
        dataSource.add("Which instruments are necessary for treating STI?");
        dataSource.add("Who is responsible for approving STI codes?");
        dataSource.add("I am going to deliver an implant crown, what should I do first?");
        dataSource.add("Could you outline the procedure for delivering an implant crown?");
        dataSource.add("Which instruments are necessary for treating STI?");
        linearLayoutManager = new LinearLayoutManager(Homepage.this, LinearLayoutManager.HORIZONTAL, false);
        myRvAdapter = new MyRvAdapter( dataSource, this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(myRvAdapter);
        saveSource = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(Homepage.this, LinearLayoutManager.HORIZONTAL, false);
        myRvAdapter2 = new MyRvAdapter2(saveSource, this);
        rv2.setLayoutManager(linearLayoutManager);
        rv2.setAdapter(myRvAdapter2);
        imgbtn1 = (ImageButton) findViewById(R.id.homebtn1);
        imgbtn2 = (ImageButton) findViewById(R.id.userbtn);
        imgbtn3 = (ImageButton) findViewById(R.id.catergoriesbtn);
        imgbtn4 = (Button) findViewById(R.id.chatbtn);
        imgbtn5 = (ImageButton) findViewById(R.id.faq);
        imgbtn6 = (ImageButton) findViewById(R.id.saved);

        Toast.makeText(getApplicationContext(), "Hello "+sessionId, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80/")  // Replace with your server IP and port
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        chatbotApi = retrofit.create(ChatbotApi.class);

        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Homepage.this,Chatpage.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Homepage.this,profilepg.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Homepage.this, categories.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Homepage.this,Chatpage.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Homepage.this, Faqs.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Homepage.this, savedquepage.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        loadmessages(sessionId);



    }

    @Override
    public void onitemclick(int position) {
        /*String question = "What cement do I use for implant crown cementation?";*/
        Intent intent = new Intent(Homepage.this, Chatpage.class);
        intent.putExtra("keyname", dataSource.get(position));
        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        startActivity(intent);
    }
    @Override
    public void onitemclick2(int position) {
        /*String question = "What cement do I use for implant crown cementation?";*/
        Intent intent = new Intent(Homepage.this, Chatpage.class);
        intent.putExtra("keyname", saveSource.get(position));
        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        startActivity(intent);
    }


    class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyHolder> {
        private  RecyclerViewInterface recyclerViewInterface;

        ArrayList<String> data;


        public MyRvAdapter(ArrayList<String> data, RecyclerViewInterface recyclerViewInterface) {
            this.data = data;
            this.recyclerViewInterface = recyclerViewInterface;
        }

        @NonNull
        @Override
        public MyRvAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Homepage.this).inflate(R.layout.homepage_item, parent, false);
            return new MyRvAdapter.MyHolder(view, recyclerViewInterface);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tvTitle.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

         class MyHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;

            public MyHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
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
    class MyRvAdapter2 extends RecyclerView.Adapter<MyRvAdapter2.MyHolder2> {
        private  RecyclerViewInterface2 recyclerViewInterface2;
        ArrayList<String> saved;
        public MyRvAdapter2 (ArrayList<String> saved, RecyclerViewInterface2 recyclerViewInterface2) {

            this.saved = saved;
            this.recyclerViewInterface2 = recyclerViewInterface2;
        }

        @NonNull
        @Override
        public MyRvAdapter2.MyHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Homepage.this).inflate(R.layout.save_item, parent, false);
            return new MyRvAdapter2.MyHolder2(view, (RecyclerViewInterface2) recyclerViewInterface2);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder2 holder, int position) {
            holder.savedque.setText(saved.get(position));
        }

        @Override
        public int getItemCount() {
            return saved.size();
        }

        class MyHolder2 extends RecyclerView.ViewHolder {
            TextView savedque;

            public MyHolder2(@NonNull View itemView, RecyclerViewInterface2 recyclerViewInterface2) {
                super(itemView);
                savedque = itemView.findViewById(R.id.savedque);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (recyclerViewInterface2 != null){
                            int pos = getAdapterPosition();
                            if (pos!= RecyclerView.NO_POSITION){
                                recyclerViewInterface2.onitemclick2(pos);
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
                    saveSource.add("there is no saved questions yet");
                }
                else {
                    saveSource.add(message);
                    myRvAdapter2.notifyDataSetChanged();
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
