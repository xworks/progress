package com.example.lin.progress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ProgressView progressView;
    private int progressViewType = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressView = (ProgressView)findViewById(R.id.progress_view);

        findViewById(R.id.button_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressViewType == 1) {
                    progressViewType = 2;
                    progressView.setProgressType(progressViewType);

                }
                else if (progressViewType == 2){
                    progressViewType = 1;
                    progressView.setProgressType(progressViewType);
                }
            }
        });
    }
}
