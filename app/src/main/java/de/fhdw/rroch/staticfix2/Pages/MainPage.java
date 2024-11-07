package de.fhdw.rroch.staticfix2.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.fhdw.rroch.staticfix2.R;

import java.util.ArrayList;
import java.util.Random;

public class MainPage extends AppCompatActivity {

    private Button mBtnNavigateToFormsCollection, mBtnNavigateToResults, mBtnAddItem, mBtnRandomItems;
    private Spinner mSpnInputParameters;

    private ListView mListView;
    private ArrayList<Integer> mItems;
    private ArrayAdapter<Integer> mAdapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);


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

        updateButtonState();

        mBtnNavigateToFormsCollection.setOnClickListener(v -> navigateToFormsCollection());
        mBtnNavigateToResults.setOnClickListener(v -> navigateToResults());
        mBtnAddItem.setOnClickListener(v -> makeSimpleStaticPopUpWindow());
        mBtnRandomItems.setOnClickListener(v -> makeGenerateRandomPopUpWindow());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.input_spinner_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnInputParameters.setAdapter(adapter);
    }

    private void navigateToFormsCollection() {
        Intent intent = new Intent(this, FormCollectionPage.class);
        startActivity(intent);
    }

    private void navigateToResults() {
        Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra("INPUT_DATA", mItems);
        startActivity(intent);
    }

    private void updateButtonState() {
        mBtnNavigateToResults.setEnabled(!mItems.isEmpty());
        // mBtnNavigateToResults.setBackground((!mItems.isEmpty())?10011097:getColor(R.color.light_blue));
    }

    private void makeSimpleStaticPopUpWindow() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_add_simple_static, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        EditText editTextNumber = popupView.findViewById(R.id.etn_simple_static);
        CheckBox checkBox = popupView.findViewById(R.id.cb_simple_static);
        Button buttonAdd = popupView.findViewById(R.id.btn_add_simple_static);

        buttonAdd.setEnabled(false);

        checkBox.setChecked(true);

        editTextNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                buttonAdd.performClick();
            }
            return false;
        });

        editTextNumber.addTextChangedListener(new TextWatcher() {
                                                  @Override
                                                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                  }

                                                  @Override
                                                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                      // Enable "Add" button only if EditText is not empty
                                                      boolean mEditTextIsNotEmpty =!s.toString().trim().isEmpty();
                                                      buttonAdd.setEnabled(mEditTextIsNotEmpty);
                                                      buttonAdd.setBackgroundColor(mEditTextIsNotEmpty ? getColor(R.color.light_black) : getColor(R.color.organge));
                                                  }

                                                  @Override
                                                  public void afterTextChanged(Editable s) {
                                                  }
                                              }
        );

        buttonAdd.setOnClickListener(v -> {

            Integer inputText = Integer.parseInt(editTextNumber.getText().toString());
            boolean isChecked = checkBox.isChecked();
            mItems.add(inputText);
            if (isChecked) {
                editTextNumber.setText("");
                updateButtonState();
            } else {
                popupWindow.dismiss();
                updateButtonState();
            }

            Toast.makeText(MainPage.this,
                    "Eingabe: " + inputText,
                    Toast.LENGTH_SHORT).show();
        });
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    private void makeGenerateRandomPopUpWindow() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_randomvalues, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        EditText editTextCount = popupView.findViewById(R.id.etn_count_random);
        EditText editTextMin = popupView.findViewById(R.id.etn_min_random);
        EditText editTextMax = popupView.findViewById(R.id.et_max_random);
        Button buttonGenerateRandom = popupView.findViewById(R.id.btn_genrate_radnom);


        buttonGenerateRandom.setOnClickListener(v -> {

            int inputTextCount = Integer.parseInt(editTextCount.getText().toString());
            int inputTextMin = Integer.parseInt(editTextMin.getText().toString());
            int inputTextMax = Integer.parseInt(editTextMax.getText().toString());
            Random random = new Random();

            for (int i = 0; i < inputTextCount; i++) {
                mItems.add(random.nextInt((inputTextMax - inputTextMin) + 1) + inputTextMin);
            }
            popupWindow.dismiss();
            buttonGenerateRandom.setEnabled(!mItems.isEmpty());
            updateButtonState();

            Toast.makeText(MainPage.this,
                    inputTextCount + " Zahlen generiert",
                    Toast.LENGTH_SHORT).show();
        });

        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }
}
