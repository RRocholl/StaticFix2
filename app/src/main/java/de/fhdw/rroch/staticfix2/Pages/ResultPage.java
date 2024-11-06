package de.fhdw.rroch.staticfix2.Pages;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.fhdw.rroch.staticfix2.Calculate;
import de.fhdw.rroch.staticfix2.R;

import java.util.ArrayList;

public class ResultPage extends AppCompatActivity {
    private ArrayList<Integer> mItems;

    private TextView mRawData, mOrganizedData;
    private Calculate mCalculate = new Calculate();
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.result_layout);
        mRawData = findViewById(R.id.tv_result_raw_input);
        mOrganizedData = findViewById(R.id.tv_result_organized_input);
        String outPutHelper;


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                mItems = null;
            } else {
                mItems = extras.getIntegerArrayList("INPUT_DATA");
            }
        } else {
            mItems = (ArrayList<Integer>) savedInstanceState.getSerializable("INPUT_DATA");
        }
        // TODO: folgende 4 zeilen Löschen
        mItems.add(333);
        mItems.add(2);
        mItems.add(3334);
        mItems.add(1);

        outPutHelper = "Eingabe: " + mCalculate.outPutData(mItems);
        mRawData.setText(outPutHelper);
        outPutHelper = "Geordnet: " + mCalculate.organizedData(mItems);
        mOrganizedData.setText(outPutHelper);
    }
}
