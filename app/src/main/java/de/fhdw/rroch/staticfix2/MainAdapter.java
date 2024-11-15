package de.fhdw.rroch.staticfix2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import java.util.ArrayList;

// the single item
public class MainAdapter extends BaseAdapter {

    // initialize the global objects
    private final Context mContext;
    private final ArrayList<Integer> mItems;

    // constructor the object
    public MainAdapter(Context context, ArrayList<Integer> items) {
        this.mContext = context;
        this.mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.indexOf(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // delete the clicked Item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_list_item, parent, false);
        }

        TextView itemText = convertView.findViewById(R.id.tv_main_items);
        Button buttonDelete = convertView.findViewById(R.id.btn_main_list_item);

        itemText.setText(mItems.get(position).toString());

        buttonDelete.setOnClickListener(v-> {
            mItems.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
