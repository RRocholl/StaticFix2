package de.fhdw.rroch.staticfix2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {


    Button mBtnNavigateToFormsCollection;
    Button mBtnNavigateToResults;
    Button mBtnAddItem;
    Button mBtnRandomItems;
    Spinner mSpnInputParameters;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        mBtnNavigateToFormsCollection = findViewById(R.id.btn_form_page);
        mBtnNavigateToResults = findViewById(R.id.btn_result_page);
        mBtnAddItem = findViewById(R.id.btn_add_item);
        mBtnRandomItems = findViewById(R.id.btn_random_items);
        mSpnInputParameters = findViewById(R.id.spn_input_parameters);

        mBtnNavigateToFormsCollection.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormCollectionPage.class);
            startActivity(intent);
        });

        mBtnNavigateToResults.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResultPage.class);
            startActivity(intent);
        });

        //Spinner config
        // Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.input_spinner_array,
                android.R.layout.simple_spinner_item
        );
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner.
        mSpnInputParameters.setAdapter(adapter);
    }
}
