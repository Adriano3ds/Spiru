package ib2.spiru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        androidx.appcompat.widget.AppCompatButton backHistoryButton =
                (androidx.appcompat.widget.AppCompatButton) findViewById(R.id.history_back);

        backHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, MonitorActivity.class));
            }
        });
    }
}