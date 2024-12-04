package de.fhdw.rroch.staticfix2.Pages;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import de.fhdw.rroch.staticfix2.FormsAdapter;
import de.fhdw.rroch.staticfix2.R;

public class FormCollectionPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_collection_layout);

        // Header- and Body-Arrays created
        String[] headers = getResources().getStringArray(R.array.attribut_name);
        String[] bodies = getResources().getStringArray(R.array.attribut_description);

        // ListView treat the objects
        ListView listView = findViewById(R.id.lv_forms_page);

        // create and set the Adapter
        FormsAdapter adapter = new FormsAdapter(this, headers, bodies);
        listView.setAdapter(adapter); //
    }
}
