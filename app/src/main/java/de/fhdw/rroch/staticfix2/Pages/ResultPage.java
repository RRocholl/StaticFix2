package de.fhdw.rroch.staticfix2.Pages;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.fhdw.rroch.staticfix2.Calculate;
import de.fhdw.rroch.staticfix2.R;

import java.util.ArrayList;
import java.util.List;

public class ResultPage extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.result_layout);

        ArrayList<Integer> mItems;


        TextView mRawData, mOrganizedData;
        TableLayout tableLayout;
        Calculate mCalculate = new Calculate();
        mRawData = findViewById(R.id.tv_result_raw_input);
        mOrganizedData = findViewById(R.id.tv_result_organized_input);
        tableLayout = findViewById(R.id.tl_result_page);
        String outPutHelper;
        List<Double[]> dataList;


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



        outPutHelper = "Eingabe: " + mCalculate.tabelHeader(mItems);
        mRawData.setText(outPutHelper);
        outPutHelper = "Geordnet: " + mCalculate.organizedData(mItems);
        mOrganizedData.setText(outPutHelper);

        dataList = mCalculate.crateTableData(mItems);

        addDataToTable(tableLayout,dataList);
    }

    private void addDataToTable(TableLayout tableLayout, List<Double[]> data) {
        for (Double[] row : data) {
            // Create a new table row
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            // Add each cell to the row
            for (Double cell : row) {
                TextView textView = new TextView(this);
                textView.setText(cell.toString());
                textView.setPadding(16, 16, 16, 16);
                textView.setGravity(Gravity.CENTER);
                tableRow.addView(textView);
            }

            // Add row to TableLayout
            tableLayout.addView(tableRow);
        }
    }
}
