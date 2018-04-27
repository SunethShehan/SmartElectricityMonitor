package mycompany.smartelectricitymonitor;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class DeviceUsageListAdapter extends ArrayAdapter<ArrayList>
{

    private Activity context;
    private ArrayList<ArrayList> items;

    public DeviceUsageListAdapter(Activity context, ArrayList objects) {
        super(context, R.layout.device_list, objects);

        this.context = context;
        this.items = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.device_list, null, true);







        return rowView;
    }
}
