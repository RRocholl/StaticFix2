package de.fhdw.rroch.staticfix2.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.fhdw.rroch.staticfix2.MainAdapter;
import de.fhdw.rroch.staticfix2.R;

import java.util.ArrayList;
import java.util.Random;

public class MainPage extends AppCompatActivity {

    // initialize the global objects
    private Button mBtnNavigateToResults;
    private ListView mListView;
    private ArrayList<Integer> mItems;
    private MainAdapter mAdapter;

    //create the Object
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        // initialize and treat the objects
        Button mBtnNavigateToFormsCollection = findViewById(R.id.btn_form_page);
        mBtnNavigateToResults = findViewById(R.id.btn_result_page);
        Button mBtnAddItem = findViewById(R.id.btn_add_item);
        Button mBtnRandomItems = findViewById(R.id.btn_random_items);

        mListView = findViewById(R.id.lv_main_page);
        mItems = new ArrayList<>();
        mAdapter = new MainAdapter(this, mItems);
        mListView.setAdapter(mAdapter);

        updateButtonState();

        mBtnNavigateToFormsCollection.setOnClickListener(v -> navigateToFormsCollection());
        mBtnNavigateToResults.setOnClickListener(v -> navigateToResults());
        mBtnAddItem.setOnClickListener(v -> makeSimpleStaticPopUpWindow());
        mBtnRandomItems.setOnClickListener(v -> makeGenerateRandomPopUpWindow());

    }

    // save the data
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // data saved in a list
        outState.putIntegerArrayList("ITEMS_LIST", mItems);
    }

    //resored the data
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // data get resored
        mItems = savedInstanceState.getIntegerArrayList("ITEMS_LIST");

        // Adapter actualise
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        mAdapter = new MainAdapter(this, mItems);
        mListView.setAdapter(mAdapter);

        updateButtonState();
    }

    // navigate to the forms page
    private void navigateToFormsCollection() {
        Intent intent = new Intent(this, FormCollectionPage.class);
        startActivity(intent);
    }

    // navigate to the result page
    private void navigateToResults() {
        Intent intent = new Intent(this, ResultPage.class);
        intent.putExtra("INPUT_DATA", mItems);
        startActivity(intent);
    }

    // the button result shouldn't be available wenn the list is empty
    private void updateButtonState() {
        boolean isEmpty = !mItems.isEmpty();
        mBtnNavigateToResults.setEnabled(isEmpty);
        mBtnNavigateToResults.setBackgroundColor(isEmpty ? getColor(R.color.light_black) : getColor(R.color.default_btn_color));
    }

    // make a pop-up window to add simple data
    private void makeSimpleStaticPopUpWindow() {

        //genrate the view for the pop-up
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_add_simple_static, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // initialize and treat the objects
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

        // if the editText is empty, the Button is not available
        editTextNumber.addTextChangedListener(new TextWatcher() {
                                                  @Override
                                                  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                                  }

                                                  @Override
                                                  public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                      // make a boolean for is the text empty
                                                      boolean mEditTextIsNotEmpty =!s.toString().trim().isEmpty();
                                                      // if the editText is empty, the Button is not available
                                                      buttonAdd.setEnabled(mEditTextIsNotEmpty);
                                                      // the perfect color for it
                                                      buttonAdd.setBackgroundColor(mEditTextIsNotEmpty ? getColor(R.color.light_black) : getColor(R.color.default_btn_color));
                                                  }

                                                  @Override
                                                  public void afterTextChanged(Editable s) {
                                                  }
                                              }
        );

        //the logic behind the button "add"
        buttonAdd.setOnClickListener(v -> {

            Integer inputText = Integer.parseInt(editTextNumber.getText().toString());
            boolean isChecked = checkBox.isChecked();
            mItems.add(inputText);

            if (isChecked) {                // the user can add more items without closing the window
                editTextNumber.setText("");
                updateButtonState();
            } else {
                popupWindow.dismiss();  // close the pop-up
                updateButtonState();    // update the available the "Calculate" button
            }

            Toast.makeText(MainPage.this,
                    "Eingabe: " + inputText,
                    Toast.LENGTH_SHORT).show();
        });
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    // make a pop-up window to generate random data
    private void makeGenerateRandomPopUpWindow() {

        //genrate the view for the pop-up
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_randomvalues, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // initialize and treat the objects
        EditText editTextCount = popupView.findViewById(R.id.etn_count_random);
        EditText editTextMin = popupView.findViewById(R.id.etn_min_random);
        EditText editTextMax = popupView.findViewById(R.id.et_max_random);
        Button buttonGenerateRandom = popupView.findViewById(R.id.btn_genrate_radnom);


        // logic for the button
        buttonGenerateRandom.setOnClickListener(v -> {
            int inputTextCount;
            int inputTextMin;
            int inputTextMax;

            // input "Count" shouldn't be empty
            if (editTextCount.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Das Feld COUNT darf nicht leer sein!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                inputTextCount = Integer.parseInt(editTextCount.getText().toString());
            }

            // input "Min" shouldn't be empty
            if (editTextMin.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Das Feld MIN darf nicht leer sein!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                inputTextMin = Integer.parseInt(editTextMin.getText().toString());
            }

            // input "Max" shouldn't be empty
            if (editTextMax.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Das Feld MAX darf nicht leer sein!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                inputTextMax = Integer.parseInt(editTextMax.getText().toString());
            }

            // "Min" shouldn't be higher than "Max"
            if (inputTextMin > inputTextMax) {
                Toast.makeText(this, "Die Min-Zahl darf nicht größer sein als die Max-Zahl!", Toast.LENGTH_SHORT).show();
                return;
            }

            // make the Random Data
            Random random = new Random();
            for (int i = 0; i < inputTextCount; i++) {
                mItems.add(random.nextInt((inputTextMax - inputTextMin) + 1) + inputTextMin);
            }
            popupWindow.dismiss();  // close pop-up window
            updateButtonState();    // update the available the "Calculate" button

            Toast.makeText(MainPage.this,
                    inputTextCount + " Zahlen generiert",
                    Toast.LENGTH_SHORT).show();
        });

        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }
}
