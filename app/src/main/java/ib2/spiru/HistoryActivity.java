package ib2.spiru;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;

import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    TextView textData;
    List<DataEntry> seriesData;
    int id = 1;
    AnyChartView anyChartView;
    Cartesian cartesian;
    private static final String FILE_NAME = "data.txt";
    Button download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        textData = (TextView) findViewById(R.id.history);
        AppCompatButton backButton = findViewById(R.id.history_left);
        AppCompatButton goButton = findViewById(R.id.history_right);
        AppCompatButton download = findViewById(R.id.download);
        anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progressBar));
        AppCompatButton backHistoryButton =
                (AppCompatButton) findViewById(R.id.history_back);
        goButton.setEnabled(false);
//        goButton
        cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 20d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                // TODO ystroke
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.yAxis(0).title("Taxa respiratória");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        cartesian.legend().enabled(false);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        anyChartView.setChart(cartesian);
        chooseEntry(id);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        backHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HistoryActivity.this, MonitorActivity.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id++;
                chooseEntry(id);
                if (id > 1) {
                    goButton.setEnabled(true);
                }
            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id--;
                chooseEntry(id);
                if (id == 1) {
                    goButton.setEnabled(false);
                }
            }
        });
    }

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
        }
    }

    private void chooseEntry(int id) {
        seriesData = new ArrayList<>();
        if (id == 1) {
            seriesData.add(new CustomDataEntry("13:53", 52, 2.3, 2.8));
            seriesData.add(new CustomDataEntry("13:54", 53, 4.0, 4.1));
            seriesData.add(new CustomDataEntry("13:55", 52, 6.2, 5.1));
            seriesData.add(new CustomDataEntry("13:56", 52, 11.8, 6.5));
            seriesData.add(new CustomDataEntry("13:57", 49, 13.0, 12.5));
            seriesData.add(new CustomDataEntry("13:58", 48, 13.9, 18.0));
            seriesData.add(new CustomDataEntry("13:59", 51, 18.0, 21.0));
            seriesData.add(new CustomDataEntry("14:00", 53, 23.3, 20.3));
            seriesData.add(new CustomDataEntry("14:01", 49, 24.7, 19.2));
            seriesData.add(new CustomDataEntry("14:02", 50, 18.0, 14.4));
            seriesData.add(new CustomDataEntry("14:03", 51, 15.1, 9.2));
            seriesData.add(new CustomDataEntry("14:04", 52, 11.3, 5.9));
            seriesData.add(new CustomDataEntry("14:05", 51, 14.2, 5.2));
            seriesData.add(new CustomDataEntry("14:06", 51, 13.7, 4.7));
            seriesData.add(new CustomDataEntry("14:07", 51, 9.9, 4.2));
            seriesData.add(new CustomDataEntry("14:08", 49, 12.1, 1.2));
            seriesData.add(new CustomDataEntry("14:09", 49, 13.5, 5.4));
            seriesData.add(new CustomDataEntry("14:10", 50, 15.1, 6.3));
            seriesData.add(new CustomDataEntry("14:11", 51, 17.9, 8.9));
            seriesData.add(new CustomDataEntry("14:12", 50, 18.9, 10.1));
            textData.setText("15/08/2022");

        }
        if (id == 2) {
//            anyChartView.clear();
//            seriesData.clear();
            seriesData.add(new CustomDataEntry("21:53", 52, 2.3, 2.8));
            seriesData.add(new CustomDataEntry("21:54", 63, 4.0, 4.1));
            seriesData.add(new CustomDataEntry("21:55", 62, 6.2, 5.1));
            seriesData.add(new CustomDataEntry("21:56", 72, 11.8, 6.5));
            seriesData.add(new CustomDataEntry("21:57", 79, 13.0, 12.5));
            seriesData.add(new CustomDataEntry("21:58", 78, 13.9, 18.0));
            seriesData.add(new CustomDataEntry("21:59", 71, 18.0, 21.0));
            seriesData.add(new CustomDataEntry("22:00", 53, 23.3, 20.3));
            seriesData.add(new CustomDataEntry("22:01", 49, 24.7, 19.2));
            seriesData.add(new CustomDataEntry("22:02", 65, 18.0, 14.4));
            seriesData.add(new CustomDataEntry("22:03", 70, 15.1, 9.2));
            seriesData.add(new CustomDataEntry("22:04", 71, 11.3, 5.9));
            seriesData.add(new CustomDataEntry("22:05", 67, 14.2, 5.2));
            seriesData.add(new CustomDataEntry("22:06", 65, 13.7, 4.7));
            seriesData.add(new CustomDataEntry("22:07", 62, 9.9, 4.2));
            seriesData.add(new CustomDataEntry("22:08", 49, 12.1, 1.2));
            seriesData.add(new CustomDataEntry("22:09", 49, 13.5, 5.4));
            seriesData.add(new CustomDataEntry("22:10", 50, 15.1, 6.3));
            seriesData.add(new CustomDataEntry("22:11", 51, 17.9, 8.9));
            seriesData.add(new CustomDataEntry("22:12", 50, 18.9, 10.1));
            textData.setText("14/08/2022");
        }
        cartesian.data(seriesData);
    }
    public void save(){
        FileWriter writer = null;
        try {
            writer = new FileWriter("output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(DataEntry str: seriesData) {
            try {
                writer.write(str + System.lineSeparator());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}