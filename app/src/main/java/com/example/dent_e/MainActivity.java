package com.example.dent_e;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    float x1,x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                if (x1>x2){
                    Intent i = new Intent(MainActivity.this, Page2.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }


}