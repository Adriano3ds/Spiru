package ib2.spiru;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        timerText = findViewById(R.id.data);
        Button pauseButton = findViewById(R.id.pause_button);
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
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MonitorActivity.this, BluetoothActivity.class));
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ringtone(false);
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
        if (seconds == 10){
            ringtone(true);
        }

        return formatTime(seconds);
    }

    private String formatTime(int seconds)
    {
        return String.format("%02d",seconds);
    }

    public void ringtone(boolean bool){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            if (bool){
                notification();
                r.play();
            }
            else {
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