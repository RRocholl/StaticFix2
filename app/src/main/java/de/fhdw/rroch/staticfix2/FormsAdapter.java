package de.fhdw.rroch.staticfix2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FormsAdapter extends BaseAdapter {

    private Context context;
    private String[] headers;
    private String[] bodies;
    private boolean[] isBodyVisible;

    public FormsAdapter(Context context, String[] headers, String[] bodies) {
        this.context = context;
        this.headers = headers;
        this.bodies = bodies;
        this.isBodyVisible = new boolean[headers.length];
    }

    @Override
    public int getCount() {
        return headers.length;
    }

    @Override
    public Object getItem(int position) {
        return headers[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.forms_list_item, parent, false);
        }

        TextView mHeaderText = convertView.findViewById(R.id.headerText);
        TextView mBodyText = convertView.findViewById(R.id.bodyText);
        Button mButtonMore = convertView.findViewById(R.id.moreButton);

        mHeaderText.setText(headers[position]);
        mBodyText.setText(bodies[position]);

        mBodyText.setVisibility(isBodyVisible[position] ? View.VISIBLE : View.GONE);

        mButtonMore.setOnClickListener(v -> {
            isBodyVisible[position] = !isBodyVisible[position];
            notifyDataSetChanged();
        });

        return convertView;
    }
}