package com.example.dent_e;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Faqs2 extends AppCompatActivity implements RecyclerViewInterface{
    RecyclerView rv;
    ArrayList<String> dataSource;
    LinearLayoutManager linearLayoutManager;
    Faqs2.MyRvAdapter myRvAdapter;
    ImageButton imgbtn1;
    ImageButton imgbtn;
    ImageButton imgbtn3;

    String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");
        setContentView(R.layout.faqs2);
        rv = findViewById(R.id.verticalRv);
        //Setting the que source
        dataSource = new ArrayList<>();
        dataSource.add("What does STI stand for?");
        dataSource.add("What instruments should I get for STI treatment?");
        dataSource.add("What are the STI procedures codes?");
        dataSource.add("What cement should I use for implant crown cementation?");
        dataSource.add("What is the procedure for the implant?");
        linearLayoutManager = new LinearLayoutManager(Faqs2.this, LinearLayoutManager.VERTICAL, false);
        myRvAdapter = new Faqs2.MyRvAdapter(dataSource,this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(myRvAdapter);
        imgbtn = (ImageButton) findViewById(R.id.userbtn);
        imgbtn3 = (ImageButton) findViewById(R.id.catergoriesbtn);
        imgbtn1 = (ImageButton) findViewById(R.id.homebtn1);
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Faqs2.this,Chatpage.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Faqs2.this,profilepg.class);
                startActivity(intent);
            }
        });
        imgbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Faqs2.this, categories.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onitemclick(int position) {
        /*String question = "What cement do I use for implant crown cementation?";*/
        Intent intent = new Intent(Faqs2.this, Chatpage.class);
        intent.putExtra("keyname", dataSource.get(position));
        intent.putExtra("EXTRA_SESSION_ID", sessionId);
        startActivity(intent);
    }

    class MyRvAdapter extends RecyclerView.Adapter<Faqs2.MyRvAdapter.MyHolder> {
        private  RecyclerViewInterface recyclerViewInterface;

        ArrayList<String> data;


        public MyRvAdapter(ArrayList<String> data, RecyclerViewInterface recyclerViewInterface) {
            this.data = data;
            this.recyclerViewInterface = recyclerViewInterface;
        }

        @NonNull
        @Override
        public Faqs2.MyRvAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(Faqs2.this).inflate(R.layout.faq_item, parent, false);
            return new Faqs2.MyRvAdapter.MyHolder(view, recyclerViewInterface);
        }

        @Override
        public void onBindViewHolder(@NonNull Faqs2.MyRvAdapter.MyHolder holder, int position) {
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
}

