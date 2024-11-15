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
import de.fhdw.rroch.staticfix2.MainAdapter;
import de.fhdw.rroch.staticfix2.R;

import java.util.ArrayList;
import java.util.Random;

public class MainPage extends AppCompatActivity {

    private Button mBtnNavigateToFormsCollection, mBtnNavigateToResults, mBtnAddItem, mBtnRandomItems;

    private ListView mListView;
    private ArrayList<Integer> mItems;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);


        mBtnNavigateToFormsCollection = findViewById(R.id.btn_form_page);
        mBtnNavigateToResults = findViewById(R.id.btn_result_page);
        mBtnAddItem = findViewById(R.id.btn_add_item);
        mBtnRandomItems = findViewById(R.id.btn_random_items);

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
        boolean isEmpty = !mItems.isEmpty();
        mBtnNavigateToResults.setEnabled(isEmpty);
        mBtnNavigateToResults.setBackgroundColor(isEmpty ? getColor(R.color.light_black) : getColor(R.color.default_btn_color));
    }

    private void makeSimpleStaticPopUpWindow() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_add_simple_static, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
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
                                                      buttonAdd.setBackgroundColor(mEditTextIsNotEmpty ? getColor(R.color.light_black) : getColor(R.color.default_btn_color));
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
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        EditText editTextCount = popupView.findViewById(R.id.etn_count_random);
        EditText editTextMin = popupView.findViewById(R.id.etn_min_random);
        EditText editTextMax = popupView.findViewById(R.id.et_max_random);
        Button buttonGenerateRandom = popupView.findViewById(R.id.btn_genrate_radnom);


        buttonGenerateRandom.setOnClickListener(v -> {
            int inputTextCount;
            int inputTextMin;
            int inputTextMax;

            if (editTextCount.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Das Feld COUNT darf nicht leer sein!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                inputTextCount = Integer.parseInt(editTextCount.getText().toString());
            }

            if (editTextMin.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Das Feld MIN darf nicht leer sein!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                inputTextMin = Integer.parseInt(editTextMin.getText().toString());
            }

            if (editTextMax.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Das Feld MAX darf nicht leer sein!", Toast.LENGTH_SHORT).show();
                return;
            } else {
                inputTextMax = Integer.parseInt(editTextMax.getText().toString());
            }

            if (inputTextMin > inputTextMax) {
                Toast.makeText(this, "Die Min-Zahl darf nicht größer sein als die Max-Zahl!", Toast.LENGTH_SHORT).show();
                return;
            }

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
