package de.fhdw.rroch.staticfix2;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import de.fhdw.rroch.staticfix2.Pages.MainPage;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class MainAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Integer> mItems;

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
