package de.fhdw.rroch.staticfix2.Pages;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import de.fhdw.rroch.staticfix2.FormsAdapter;
import de.fhdw.rroch.staticfix2.R;

public class FormCollectionPage extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_collection_layout);

        // Header- und Body-Arrays erstellen
        String[] headers = getResources().getStringArray(R.array.attribut_name);
        String[] bodies = getResources().getStringArray(R.array.attribut_description);

        // ListView finden
        listView = findViewById(R.id.lv_forms_page);

        // Adapter erstellen und setzen
        FormsAdapter adapter = new FormsAdapter(this, headers, bodies);
        listView.setAdapter(adapter);
    }
}
