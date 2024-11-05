package de.fhdw.rroch.staticfix2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainPage extends AppCompatActivity {


    Button btnNavigateToFormsCollection;
    Button btnNavigateToResults;
    Button btnAddItem;
    Button btnRandomItems;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        btnNavigateToFormsCollection = findViewById(R.id.btn_form_page);
        btnNavigateToResults = findViewById(R.id.btn_result_page);
        btnAddItem = findViewById(R.id.btn_add_item);
        btnRandomItems = findViewById(R.id.btn_random_items);

        btnNavigateToFormsCollection.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormCollectionPage.class);
            startActivity(intent);
        });

        btnNavigateToResults.setOnClickListener(v -> {
            Intent intent = new Intent(this, ResultPage.class);
            startActivity(intent);
        });
    }
}
