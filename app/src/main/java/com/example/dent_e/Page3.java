package com.example.dent_e;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Page3 extends AppCompatActivity {
    Button btn;
    float x1,x2, y1, y2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_2);
        btn = (Button) findViewById(R.id.btn2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Page3.this, Login.class);
                startActivity(intent);
            }
        });
    }
    public boolean onTouchEvent(MotionEvent touchevent){
        switch(touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1= touchevent.getX();
                y1= touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2= touchevent.getX();
                y2= touchevent.getY();
                if (x1<x2){
                    Intent i = new Intent(Page3.this, Page2.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
