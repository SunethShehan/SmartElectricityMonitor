package mycompany.smartelectricitymonitor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;

public class DeviceDetailsActivity extends Activity {

    ListView lstDevices;
    ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_details);

        lstDevices = (ListView)findViewById(R.id.lstDevices);

        String[] values=new String[]{"25\" colour TV - 20.09 kWh",
                "Desktop Computer - 6kWh",
                "Fridge / Freezer - 16.75kWh",
                "Game Console - 5.79kWh",
                "Iron - 21.67kWh",
                "Microwave - 7.5kWh",
                "Rice Cooker - 2.08kWh",
                "Vacuum Cleaner - 5.83 kWh",
                "Washing Machine - 21.6kWh"};


        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<values.length;i++)
        {
            list.add(values[i]);
            //items.add();
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

       // DeviceUsageListAdapter deviceUsageListAdapter = new DeviceUsageListAdapter(this,items);


        lstDevices.setAdapter(adapter);

    }
}
