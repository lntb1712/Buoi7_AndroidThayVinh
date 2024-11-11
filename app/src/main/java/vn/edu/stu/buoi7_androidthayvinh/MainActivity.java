package vn.edu.stu.buoi7_androidthayvinh;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnButton,btnRunnableTask,btnThreadTask,btnStopThreadTask,btnThreadHandlerTask,btnStopThreadHandlerTask,btnPauseThreadHandlerTask;
    ProgressBar progressBar;

    MyThread myThread;


    Handler handler;
    Thread thread;
    MyThreadHandler myThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuly();
            }
        });

        btnRunnableTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyRunnableTask();
            }
        });
        
        btnThreadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyThreadTask();
            }
        });

        btnStopThreadTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyStopThreadTask();
            }
        });

        btnThreadHandlerTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyThreadHandler();
            }
        });

        btnPauseThreadHandlerTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyPauseThreadHandler();
            }
        });

        btnStopThreadHandlerTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulyStopThreadHandler();
            }
        });
    }

    private void xulyStopThreadHandler() {
        if(thread!=null)
        {
            thread.interrupt();
        }
    }

    private void xulyPauseThreadHandler() {
        if(myThreadHandler!=null)
            if(myThreadHandler.isRunning()){
                myThreadHandler.setRunning(false);
                btnPauseThreadHandlerTask.setText("ResumeThreadHandlerTask");
            }else{
                myThreadHandler.setRunning(true);
            }

    }

    private void xulyThreadHandler() {
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                int data = (int) msg.obj;
                progressBar.setProgress(data);
            }
        };
        myThreadHandler= new MyThreadHandler(handler);
        thread=new Thread(myThreadHandler);
        thread.start();
    }

    private void xulyStopThreadTask() {
        if (myThread!=null){
            myThread.interrupt();
        }
    }

    private void xulyThreadTask() {
        myThread=new MyThread();
        myThread.start();
    }

    private void xulyRunnableTask() {
        new Thread(new MyRunnable()).start();
    }

    private void xuly() {
        try {
            int data=0;
            while(data<100){
                Thread.sleep(100);
                data++;

                progressBar.setProgress(data);
            }
        }catch (Exception e){
            Log.e("Loi",e.toString());
        }
    }

    private void addControls() {
        progressBar=findViewById(R.id.ProgressBar);
        btnButton=findViewById(R.id.btnButton);
        btnRunnableTask=findViewById(R.id.btnRunnableTask);
        btnThreadTask=findViewById(R.id.btnThreadTask);
        btnStopThreadTask=findViewById(R.id.btnStopThreadTask);
        btnThreadHandlerTask=findViewById(R.id.btnThreadHandlerTask);
        btnStopThreadHandlerTask=findViewById(R.id.btnStopThreadHandlerTask);
        btnPauseThreadHandlerTask=findViewById(R.id.btnPauseThreadHandlerTask);
    }

    class MyRunnable implements Runnable
    {
        @Override
        public void run() {
            try {
                int data=0;
                while(data<100){
                    Thread.sleep(100);
                    data++;

                    progressBar.setProgress(data);
                }
            }catch (Exception e){
                Log.e("Loi",e.toString());
            }
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                int data=0;
                while(data<100){
                    Thread.sleep(100);
                    data++;

                    progressBar.setProgress(data);
                }
            }catch (Exception e){
                Log.e("Loi",e.toString());
            }
        }
    }
    class MyThreadHandler implements Runnable{
        Handler handler;
        volatile boolean running =true;

        public MyThreadHandler(Handler handler) {
            this.handler = handler;
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            try {
                int data=0;
                while(data<100){
                    if (running) {
                        Thread.sleep(100);
                        data++;
                        Message msg =new Message();
                        msg.obj=data;
                        handler.sendMessage(msg);
                    }
                }
            }catch (Exception e){
                Log.e("Loi",e.toString());
            }
        }
    }
}