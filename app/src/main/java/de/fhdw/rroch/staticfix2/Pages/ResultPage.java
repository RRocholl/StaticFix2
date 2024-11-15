package de.fhdw.rroch.staticfix2.Pages;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
    private final String[] tableHeader = {"ai", "h(ai)", "f(ai)", "H(ai)", "F(ai)", "HR(ai)", "FR(ai)"};
    private TextView mRawData, mOrganizedData;
    private TextView mMedian, mModus, mQuantile25, mQuantile75, mArithmeticMean, mGeometricMean;
    private TextView mSpan, mMeanAbsoluteDeviation, mEmpiricalVariance, mEmpiricalStandardDeviation, mCoefficientsOfVariation, mInterquartileRange;
    private TableLayout tableLayout;
    private final Calculate mCalculate = new Calculate();
    private ArrayList<Integer> mItems, mOrderedInputData;
    private List<Double[]> mFrequencyDistribution;
    private double[] mLocationParameters;
    private double[] mScatteringParameters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        initializeViews();

        if (savedInstanceState != null) {
            retrieveSavedData(savedInstanceState);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                mItems = extras.getIntegerArrayList("INPUT_DATA");
                processData();
            }
        }

        if (mItems != null) {
            displayData();
        }
    }

    private void initializeViews() {
        mRawData = findViewById(R.id.tv_result_raw_input);
        mOrganizedData = findViewById(R.id.tv_result_organized_input);
        tableLayout = findViewById(R.id.tl_result_page);
        mMedian = findViewById(R.id.tv_result_median_data);
        mModus = findViewById(R.id.tv_result_modus_data);
        mQuantile25 = findViewById(R.id.tv_result_quantile_25_data);
        mQuantile75 = findViewById(R.id.tv_result_quantile_75_data);
        mArithmeticMean = findViewById(R.id.tv_result_arithmetic_mean_data);
        mGeometricMean = findViewById(R.id.tv_result_geometric_mean_data);
        mSpan = findViewById(R.id.tv_result_span_data);
        mMeanAbsoluteDeviation = findViewById(R.id.tv_result_mean_absolute_deviation_data);
        mEmpiricalVariance = findViewById(R.id.tv_result_empirical_variance_data);
        mEmpiricalStandardDeviation = findViewById(R.id.tv_result_empirical_standard_deviation_data);
        mCoefficientsOfVariation = findViewById(R.id.tv_result_coefficients_of_variation_data);
        mInterquartileRange = findViewById(R.id.tv_result_interquartile_range_data);
    }

    private void processData() {
        mOrderedInputData = mCalculate.organizedData(mItems);
        mFrequencyDistribution = mCalculate.createFrequencyData(mItems);
        mLocationParameters = mCalculate.createLocationParameters(mOrderedInputData);
        mScatteringParameters = mCalculate.createScatteringParameters(mOrderedInputData);
    }

    private void displayData() {
        mRawData.setText(mItems.toString());
        mOrganizedData.setText(mOrderedInputData.toString());
        addDataToTableFrequency(mFrequencyDistribution);
        addDataToLocationParameters(mLocationParameters);
        addDataToScatteringParameters(mScatteringParameters);
    }

    private void addDataToTableFrequency(List<Double[]> data) {
        int headerCounter = 0;

        for (Double[] row : data) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            TextView headerView = new TextView(this);
            headerView.setText(tableHeader[headerCounter]);
            headerView.setPadding(16, 16, 16, 16);
            headerView.setGravity(Gravity.CENTER);
            headerView.setTextColor(getColor(R.color.normal_text_color));
            tableRow.addView(headerView);
            headerCounter++;

            for (Double cell : row) {
                TextView textView = new TextView(this);
                textView.setText(cell.toString());
                textView.setPadding(16, 16, 16, 16);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(getColor(R.color.normal_text_color));
                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }
    }

    private void addDataToLocationParameters(double[] input) {
        if (input != null) {
            mMedian.setText(String.valueOf(input[0]));
            mModus.setText(String.valueOf(input[1]));
            mQuantile25.setText(String.valueOf(input[2]));
            mQuantile75.setText(String.valueOf(input[3]));
            mArithmeticMean.setText(String.valueOf(input[4]));
            mGeometricMean.setText(String.valueOf(input[5]));
        }
    }

    private void addDataToScatteringParameters(double[] input) {
        if (input != null) {
            mSpan.setText(String.valueOf(input[0]));
            mMeanAbsoluteDeviation.setText(String.valueOf(input[1]));
            mEmpiricalVariance.setText(String.valueOf(input[2]));
            mEmpiricalStandardDeviation.setText(String.valueOf(input[3]));
            mCoefficientsOfVariation.setText(String.valueOf(input[4]));
            mInterquartileRange.setText(String.valueOf(input[5]));
        }
    }

    private void retrieveSavedData(Bundle savedInstanceState) {
        mItems = (ArrayList<Integer>) savedInstanceState.getSerializable("INPUT_DATA");
        mOrderedInputData = (ArrayList<Integer>) savedInstanceState.getSerializable("ORDERED_INPUT_DATA");
        mFrequencyDistribution = (List<Double[]>) savedInstanceState.getSerializable("FREQUENCY_DISTRIBUTION");
        mLocationParameters = savedInstanceState.getDoubleArray("LOCATION_PARAMETERS");
        mScatteringParameters = savedInstanceState.getDoubleArray("SCATTERING_PARAMETERS");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("INPUT_DATA", mItems);
        outState.putSerializable("ORDERED_INPUT_DATA", mOrderedInputData);
        outState.putSerializable("FREQUENCY_DISTRIBUTION", (ArrayList<Double[]>) mFrequencyDistribution);
        outState.putDoubleArray("LOCATION_PARAMETERS", mLocationParameters);
        outState.putDoubleArray("SCATTERING_PARAMETERS", mScatteringParameters);
    }
}
