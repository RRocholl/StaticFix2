package de.fhdw.rroch.staticfix2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {



    private ListView mListView;
    private ArrayList<Integer> mItems;
    private ArrayAdapter<Integer> mAdapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);
        Button mBtnNavigateToFormsCollection, mBtnNavigateToResults, mBtnAddItem,mBtnRandomItems;
        Spinner mSpnInputParameters;

        mBtnNavigateToFormsCollection = findViewById(R.id.btn_form_page);
        mBtnNavigateToResults = findViewById(R.id.btn_result_page);
        mBtnAddItem = findViewById(R.id.btn_add_item);
        mBtnRandomItems = findViewById(R.id.btn_random_items);
        mSpnInputParameters = findViewById(R.id.spn_input_parameters);

        mListView = findViewById(R.id.lv_main_page);
        mItems = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mItems);
        mListView.setAdapter(mAdapter);

        mSpnInputParameters.setAdapter(mAdapter);

        mBtnNavigateToFormsCollection.setOnClickListener(v -> navigateToFormsCollection());
        mBtnNavigateToResults.setOnClickListener(v -> navigateToResults());


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.input_spinner_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnInputParameters.setAdapter(adapter);

        mBtnAddItem.setOnClickListener(v -> addItemListView());
        mBtnRandomItems.setOnClickListener(v -> {});

    }

    private void navigateToFormsCollection() {
        Intent intent = new Intent(this, FormCollectionPage.class);
        startActivity(intent);
    }
    private void navigateToResults() {
        Intent intent = new Intent(this, FormCollectionPage.class);
        startActivity(intent);
    }

    private void addItemListView() {
        int k = mItems.size();
        mItems.add(k);
        mAdapter.notifyDataSetChanged();
        mListView.smoothScrollToPosition(mItems.size() - 1);
    }
}
