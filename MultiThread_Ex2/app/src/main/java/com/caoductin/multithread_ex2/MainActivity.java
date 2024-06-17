package com.caoductin.multithread_ex2;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.caoductin.multithread_ex2.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int percent;
    int randNumb;

    Random random = new Random();

    ActivityMainBinding binding;
    Handler handler = new Handler();
    int count = 0;
    int buttonsPerRow = 2; // Number of buttons to display in each row
    Runnable foregroundThread = new Runnable() {
        @Override
        public void run() {
            count++ ;
            //update UI
            binding.txtPercent.setText(percent + "%");
            binding.pbPercent.setProgress(percent);
           // ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200,ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500,LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(15,15,15,15);

            Button button = new Button(MainActivity.this);
            button.setLayoutParams(params);
            button.setText(String.valueOf(randNumb));
            button.setTextSize(22);
//
//            int gravity = count % 2 == 0 ? Gravity.RIGHT : Gravity.LEFT;
//            params.gravity = gravity;
            if (randNumb % 2 == 0){
                button.setBackgroundColor(Color.rgb(0,150,136));
            }else{
                button.setBackgroundColor(Color.rgb(224,67,54));
            }


            binding.containerLayout.addView(button);

            if(percent == 100){

                binding.txtPercent.setText("done!");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding = ActivityMainBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
       addEvent();
    }
    public void addEvent(){
        binding.btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.containerLayout.removeAllViews();
                execBackgoundThread();
            }
        });
    }
    private void execBackgoundThread(){
        int numbOfViews = Integer.parseInt(binding.edtNumofView.getText().toString());
        Thread backgroundThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i<= numbOfViews;i++){
                    percent = i* 100/ numbOfViews;
                    randNumb = random.nextInt(100);
                    handler.post(foregroundThread);
                    SystemClock.sleep(100);
                }

            }
        });
        backgroundThread.start();
    }

}