package ib2.spiru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        androidx.appcompat.widget.AppCompatButton historyBackButton =  findViewById(R.id.history_back);
        historyBackButton.setOnClickListener((View.OnClickListener) this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(HistoryActivity.this, MonitorActivity.class);
        startActivity(intent);
    }
}