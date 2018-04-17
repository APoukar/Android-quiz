package com.example.android.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
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

    /**
     * Number of points
     * User gets one point for each correct answer
     */
    int points;
    int numberOfQuestions = 5;

    public void submitAnswers(View view) {
        checkRadioButtons();
        checkEditText();
        checkCheckBoxes();
        displayToast();
        TextView pointsResult = findViewById(R.id.points_view);
        pointsResult.setText(points + "/" + numberOfQuestions);
        pointsResult.setVisibility(View.VISIBLE);
        setupPieChart();
        switchButtons();
    }

    /**
     * Checks RadioButtons and adds
     * one point for every correct answer
     */
    private void checkRadioButtons() {
        for (int i = 1; i < 4; i++) {
            int id = getResources().getIdentifier("answer_" + i, "id", getPackageName());
            RadioButton button = findViewById(id);
            if (button.isChecked()) {
                points += 1;
            }
        }

    }

    /**
     * Checks EditText and adds
     * one point for every correct answer
     */
    private void checkEditText() {
        EditText answerEditText = (EditText) findViewById(R.id.edit_text);
        String answerString = answerEditText.getText().toString().trim();
        if (answerString.equalsIgnoreCase("activity")) {
            points += 1;
        }
    }

    /**
     * Checks CheckBoxes and adds
     * one point for every correct answer
     */
    private void checkCheckBoxes() {
        CheckBox checkBox1 = findViewById(R.id.check_1);
        CheckBox checkBox2 = findViewById(R.id.check_2);
        CheckBox checkBox3 = findViewById(R.id.check_3);
        CheckBox checkBox4 = findViewById(R.id.check_4);
        if (!checkBox1.isChecked() && checkBox2.isChecked()
                && checkBox3.isChecked() && checkBox4.isChecked()) {
            points += 1;
        }
    }

    //Displays toast with a number of received points
    private void displayToast() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Congrats! You have achieved " + points + " points!",
                Toast.LENGTH_LONG);
        toast.show();
    }

    private void setupPieChart() {
        //Creates list of entries
        List<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry((numberOfQuestions - points), getString(R.string.wrong_answers)));
        pieEntries.add(new PieEntry(points, getString(R.string.correct_answers)));

        //Creates DataSet from the list of entries
        PieDataSet answersDataSet = new PieDataSet(pieEntries, getString(R.string.correct_answers));
        PieData answers = new PieData(answersDataSet);
        answersDataSet.setColors(new int[]{R.color.colorFrame, R.color.colorChart}, MainActivity.this);
        answersDataSet.setDrawValues(false);    //Disables numbers of right/wrong answers in the chart

        //Settings of the chart
        PieChart answersPie = findViewById(R.id.chart);
        answersPie.setData(answers);
        answersPie.setVisibility(View.VISIBLE);
        answersPie.setDrawEntryLabels(false);  //Disables labels of the chart
        answersPie.getDescription().setEnabled(false);  //Disables description of the chart
        answersPie.animateXY(1500, 1500);

        //Disables legend of the Pie chart
        Legend legend = answersPie.getLegend();
        legend.setEnabled(false);


    }

    /**
     * Checks which of the two buttons is visible,
     * hides it and shows the other one
     */
    private void switchButtons() {
        Button checkAnswersButton = findViewById(R.id.check_answers);
        Button resetButton = findViewById(R.id.reset);
        if (checkAnswersButton.getVisibility() == View.VISIBLE) {
            checkAnswersButton.setVisibility(View.GONE);
            resetButton.setVisibility(View.VISIBLE);
        } else {
            checkAnswersButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.GONE);
        }
    }

    //Resets the whole activity
    public void resetQuiz(View view) {
        Intent MainActivity = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        MainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(MainActivity);
    }
}
