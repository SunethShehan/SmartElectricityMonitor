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

        String[] values=new String[]{"25\" colour TV",
                "Ceiling Fan",
                "Clothes Dryer",
                "Desktop Computer",
                "Fridge / Freezer",
                "Game Console",
                "Home Air Conditioner",
                "Iron",
                "Laptop Computer",
                "Microwave",
                "Rice Cooker",
                "Table Fan",
                "Vacuum Cleaner",
                "Washing Machine",
                "Water Filter and Cooler"};


        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<values.length;i++)
        {
            list.add(values[i]);
            //items.add();
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        DeviceUsageListAdapter deviceUsageListAdapter = new DeviceUsageListAdapter(this,items);




        lstDevices.setAdapter(deviceUsageListAdapter);





    }
}
