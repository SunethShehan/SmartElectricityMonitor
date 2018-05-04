package mycompany.smartelectricitymonitor;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {


    BarChart chart ;
    ArrayList<BarEntry> BARENTRY ;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    Button btnInfo;

    Spinner spnTime;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }

        chart = (BarChart) rootView.findViewById(R.id.bcUnits);
        btnInfo = (Button)rootView.findViewById(R.id.btnInfo);
        spnTime = (Spinner)rootView.findViewById(R.id.spnTime);

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

       // AddValuesToBARENTRY();

       // AddValuesToBarEntryLabels();



        AddValuesToWeekly();
        AddValuesToLabelsWeekly();


        Bardataset = new BarDataSet(BARENTRY,"Weekly");


        BARDATA = new BarData(Bardataset);

        // New Component for the MPAndroid Chart
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(BarEntryLabels));

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(3000);

        chart.getDescription().setText("");

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getActivity(), DeviceDetailsActivity.class);
                getActivity().startActivity(myIntent);
            }
        });

        spnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // getspnTime(String.valueOf(spnTime.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        //throw new RuntimeException(context.toString()
        // + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void AddValuesToBARENTRY(){

        BARENTRY.add(new BarEntry(0f, 3));
        BARENTRY.add(new BarEntry(1f, 1));
        BARENTRY.add(new BarEntry(2f, 2));
        BARENTRY.add(new BarEntry(3f, 3));
        BARENTRY.add(new BarEntry(4f, 4));
        BARENTRY.add(new BarEntry(5f, 5));

    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("Jan");
        BarEntryLabels.add("Feb");
        BarEntryLabels.add("Mar");
        BarEntryLabels.add("Apr");
        BarEntryLabels.add("May");
        BarEntryLabels.add("Jun");


    }


    // getWeekly Data
    private void AddValuesToWeekly()
    {
        BARENTRY.add(new BarEntry(0, 3));
        BARENTRY.add(new BarEntry(1, 1));
        BARENTRY.add(new BarEntry(2, 2));
        BARENTRY.add(new BarEntry(3, 3));


    }

    private void AddValuesToLabelsWeekly()
    {

        BarEntryLabels.add("1");
        BarEntryLabels.add("2");
        BarEntryLabels.add("3");
        BarEntryLabels.add("4");


    }

    // getMonthly Data

    private void AddValuesToMonthly()
    {
        BARENTRY.add(new BarEntry(0, 40));
        BARENTRY.add(new BarEntry(1, 43));
        BARENTRY.add(new BarEntry(2, 40));


    }

    private void AddValuesToLabelMonthly()
    {
        BarEntryLabels.add("Jan");
        BarEntryLabels.add("Feb");
        BarEntryLabels.add("Mar");


    }

    // Are we creating a separate dashboard for the Industrial User?

    private void getDetails()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String chartURL = "https://getfeed.azurewebsites.net/api/Questions/";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, chartURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject json = new JSONObject(response);
                    JSONArray jsonarr = json.getJSONArray("Question");
                    for(int i =0 ; i< jsonarr.length(); i++){

                        JSONObject jsonObject = jsonarr.getJSONObject(i);
                        //setAdapterValues(jsonObject.getString("question_Title"));

                    }

                    Toast.makeText(getContext(), "on the Response", Toast.LENGTH_SHORT).show();




                } catch (JSONException je) {

                    Toast.makeText(getContext(),"JSON Exception", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), " Volley Error", Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(stringRequest);

    }

    private void getspnTime(String selectedItem)
    {

        if(selectedItem.toString().equals("Daily"))
        {
            String getUsageUrl = "http://ereaderv10.azurewebsites.net/api/Meters/P-101";

            try {

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUsageUrl,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try
                                {
                                    JSONArray jsonArray = response.getJSONArray("usagedetail");
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);


                                    Toast.makeText(getContext(), jsonObject.getString("units"), Toast.LENGTH_SHORT).show();

                                    //BARENTRY.add(new BarEntry(0f,Integer.parseInt(jsonObject.getString("units"))));

                                    //BarEntryLabels.add("Today");

                                    // set Today Chart Values


                                }
                                catch (JSONException ex)
                                {

                                    Toast.makeText(getContext(), ex.toString(), Toast.LENGTH_SHORT).show();

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),"Volley Error",Toast.LENGTH_SHORT).show();
                        System.out.println("Error -->>"+error.toString());


                    }
                });

                request.setRetryPolicy(new

                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                Volley.newRequestQueue(getContext()).add(request);

            }
            catch (Exception ex)
            {

                System.out.print("Exception"+ex.toString());

            }

        }

    }


    private void getWeeklyDetails()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.DATE,1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // this will return the Starting day of the week
        //Toast.makeText(getContext(), simpleDateFormat.format(cal.getTime()),Toast.LENGTH_SHORT).show();

        // http://ereaderv10.azurewebsites.net/api/Meters/P-101/5

    }

}
