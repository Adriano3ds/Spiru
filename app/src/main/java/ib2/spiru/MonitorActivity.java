package ib2.spiru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        Button goToHistoryButton =  findViewById(R.id.go_to_history_button);
        goToHistoryButton.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(MonitorActivity.this, HistoryActivity.class);
        startActivity(intent);
    }
}