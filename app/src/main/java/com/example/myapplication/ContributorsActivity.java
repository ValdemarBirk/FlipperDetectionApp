package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContributorsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);

        TextView contributorsTextView = findViewById(R.id.Contributors);

        String contributorsInfo = "Linaex Joseph Vigneshwar Anandhamuruga\nPhilip Bilbo\nNick B. Blume\nValdemar Nørup Birk\nAlexander Victor Dybendal Koefoed \nJacob Sylvest Krab-Johansen\n"; // Add your contributor names here
        String specialThanks = "Shreyas Srinivasa, Ph.D.";
        contributorsTextView.setText("Contributors: \n" + contributorsInfo + "\nSpecial thanks to: \n" + specialThanks);

        Button backButton = findViewById(R.id.backButton_2);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
