package com.example.android.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /*
    Number of points
    User gets one point for each correct answer
     */
    int points = 0;

    //List of entries for creating Pie chart
    List<PieEntry> pieEntries = new ArrayList<>();

    //Defines Pie chart
    PieChart answersPie;

    //Adds one point for every correct answer
    public void checkAnswers(View view) {
        for (int i=1; i < 6; i++ ) {
            int id = getResources().getIdentifier("answer_"+i, "id", getPackageName());
            RadioButton button = (RadioButton) findViewById(id);
            if (button.isChecked()) {
                points += 1;
            }
        }
        setupPieChart();
        switchButtons();
    }

    private void setupPieChart() {
        //Creates list of entries
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(points, getString(R.string.correct_answers)));
        pieEntries.add(new PieEntry((5 - points), getString(R.string.wrong_answers)));

        //Creates DataSet from the list of entries
        PieDataSet answersDataSet = new PieDataSet(pieEntries, getString(R.string.correct_answers));
        PieData answers = new PieData(answersDataSet);
        answersDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //Settings of the chart
        answersPie = (PieChart) findViewById(R.id.chart);
        PieChart answersPie = (PieChart) findViewById(R.id.chart);
        answersPie.setData(answers);
        answersPie.setVisibility(answersPie.VISIBLE);
        answersPie.setUsePercentValues(true);
        answersPie.getDescription().setEnabled(false);  //Disables description of the chart

        //Disables legend of the Pie chart
        Legend legend = answersPie.getLegend();
        legend.setEnabled(false);
    }

    private void switchButtons() {
        Button checkAnswersButton = (Button) findViewById(R.id.check_answers);
        Button resetButton = (Button) findViewById(R.id.reset);
        if (checkAnswersButton.getVisibility()==checkAnswersButton.VISIBLE) {
            checkAnswersButton.setVisibility(checkAnswersButton.GONE);
            resetButton.setVisibility(resetButton.VISIBLE);
        } else {
            checkAnswersButton.setVisibility(checkAnswersButton.VISIBLE);
            resetButton.setVisibility(resetButton.GONE);
        }
    }

    public void resetQuiz(View view) {
        points = 0;
        pieEntries.clear();
        answersPie.setVisibility(answersPie.GONE);
        switchButtons();
    }






}
