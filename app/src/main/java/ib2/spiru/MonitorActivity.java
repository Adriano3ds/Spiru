package ib2.spiru;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MonitorActivity extends AppCompatActivity {
    TimerTask timerTask;
    Double time = 0.0;
    TextView timerText;
    Timer timer;
    int id;
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        timerText = findViewById(R.id.data);
        Button stopButton = findViewById(R.id.finalize_button);
        Button historyButton = (Button)findViewById(R.id.go_to_history_button);
        Button settingsButton = (Button)findViewById(R.id.settings_button);
        timer = new Timer();
        startTimer();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =
                    new NotificationChannel("IMPORTANT", "IMPORTANT", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MonitorActivity.this, HistoryActivity.class));
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MonitorActivity.this, BluetoothActivity.class));
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringtone(false);
                id = 1;
                timerText.setText("");
            }
        });


    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        if (seconds == 30){
            ringtone(true);
        }
        if (id == 1){
            ringtone(false);
        }

        return formatTime(seconds);
    }

    private String formatTime(int seconds)
    {
        String string = new String();
        if(id != 1){
            if(seconds<= 3){
                string = "44";
            }
            if(seconds > 3 && seconds <= 5){
                string = "43";
            }
            if(seconds > 5 && seconds <= 8){
                string = "42";
            }
            if(seconds > 8 && seconds <= 10){
                string = "47";
            }
            if(seconds > 10 && seconds <= 12){
                string = "49";
            }
            if(seconds > 10 && seconds <= 12){
                string = "49";
            }
            if(seconds > 12 && seconds <= 14){
                string = "52";
            }
            if(seconds > 14 && seconds <= 16){
                string = "53";
            }
            if(seconds > 16 && seconds <= 17){
                string = "55";
            }
            if(seconds > 17 && seconds <= 20){
                string = "59";
            }
            if(seconds > 20 && seconds <= 22){
                string = "60";
            }
            if(seconds > 22 && seconds <= 24){
                string = "61";
            }
            if(seconds > 22 && seconds <= 24){
                string = "61";
            }
            if(seconds > 22 && seconds <= 24){
                string = "65";
            }
            if(seconds > 24 && seconds <= 27){
                string = "66";
            }
            if(seconds > 27 && seconds <= 29){
                string = "68";
            }
            if(seconds > 30 && seconds <= 35){
                string = "70";
            }
            if(seconds > 35 && seconds <= 38){
                string = "71";
            }
            if(seconds > 38 && seconds <= 42){
                string = "73";
            }
            if(seconds > 35 && seconds <= 38){
                string = "71";
            }
            if(seconds > 38 && seconds <= 42){
                string = "73";
            }
            if(seconds > 42 && seconds <= 45){
                string = "69";
            }
            if(seconds > 45 && seconds <= 47){
                string = "65";
            }

        }
        return string;
    }

    public void ringtone(boolean bool){
        try {
            if (bool){
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                notification();
                r.play();
            }
            if (!bool) {
                    r.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void notification(){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MonitorActivity.this, "IMPORTANT");
        builder.setContentTitle("ATENÇÃO!");
        builder.setContentText("Houveram mudanças respiratórias graves no bebê!");
        builder.setSmallIcon(R.drawable.ic_baseline_bedroom_baby_24);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MonitorActivity.this);
        managerCompat.notify(1,builder.build());
    }
}