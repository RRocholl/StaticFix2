package de.fhdw.rroch.staticfix2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FormsAdapter extends BaseAdapter {

    // initialize the global objects
    private final Context mContext;
    private final String[] mHeaders;
    private final String[] mBodies;
    private final boolean[] mIsBodyVisible;

    // constructor the object
    public FormsAdapter(Context context, String[] headers, String[] bodies) {
        this.mContext = context;
        this.mHeaders = headers;
        this.mBodies = bodies;
        this.mIsBodyVisible = new boolean[headers.length];
    }

    @Override
    public int getCount() {
        return mHeaders.length;
    }

    @Override
    public Object getItem(int position) {
        return mHeaders[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.forms_list_item, parent, false);
        }

        TextView mHeaderText = convertView.findViewById(R.id.headerText);
        TextView mBodyText = convertView.findViewById(R.id.bodyText);
        Button mButtonMore = convertView.findViewById(R.id.moreButton);

        mHeaderText.setText(mHeaders[position]);
        mBodyText.setText(mBodies[position]);

        mBodyText.setVisibility(mIsBodyVisible[position] ? View.VISIBLE : View.GONE);

        // Button show the text wenn it is clicked
        mButtonMore.setOnClickListener(v -> {
            mIsBodyVisible[position] = !mIsBodyVisible[position];       // show the Text
            mButtonMore.setText(mIsBodyVisible[position] ? "Weniger" : "Mehr"); // set the Button text
            notifyDataSetChanged();
        });

        return convertView;
    }
}