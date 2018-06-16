package mycompany.smartelectricitymonitor;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;


import java.util.Date;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView lblDate,lblCurrentUnits,lblBillAmount,lblLastMonthUnits,lblTodayUnits;

    private String premisesNo;

    private Boolean isDomestic;

    private Integer usedUnits;

    private int todayUsedUnits;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);



        lblDate = (TextView)rootView.findViewById(R.id.lblDate);
        lblCurrentUnits = (TextView)rootView.findViewById(R.id.lblCurrentUnits);
        lblLastMonthUnits = (TextView)rootView.findViewById(R.id.lblLastMonthUnits);
        lblBillAmount = (TextView)rootView.findViewById(R.id.lblBillAmount);
        lblTodayUnits = (TextView)rootView.findViewById(R.id.lblTodayUnits);

       lblDate.setText(DateFormat.getDateTimeInstance().format(new Date()));
        Date myDate = new Date();

        lblLastMonthUnits.setText("103");

        WebAPITask webAPITask = new WebAPITask();
        webAPITask.execute();




        //Toast.makeText(getContext(),new SimpleDateFormat("yyyy-MM-dd").format(myDate),Toast.LENGTH_SHORT).show(); ;

        //setDetails();

        //getWeeklyDetails();

       // getMonthlyDetails();

        //getMonthlyUnits();






       // Toast.makeText(getContext(),premisesNo,Toast.LENGTH_SHORT).show();

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
        premisesNo = getArguments().getString("premisesNo");
        isDomestic = getArguments().getBoolean("isDomestic");



        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           // throw new RuntimeException(context.toString()
           //         + " must implement OnFragmentInteractionListener");
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

    private void getDailyDetails()
    {

        // Send the UserID and Date


      RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        String meterURL = "https://ereaderv10.azurewebsites.net/api/Meters/"+premisesNo;
        //String meterURL = "https://ereaderv10.azurewebsites.net/api/Meters/"+"premisesNo";


        StringRequest stringRequest = new StringRequest(Request.Method.GET, meterURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try
                {
                    JSONObject json = new JSONObject(response);
                    JSONArray jsonArray = json.getJSONArray("usagedetail");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    todayUsedUnits = jsonObject.getInt("dailyunits");


                }

                catch (JSONException je)
                {

                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();

                    }
                });
        requestQueue.add(stringRequest);

    }


    private void getMonthlyDetails()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_MONTH, cal.getFirstDayOfWeek());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Toast.makeText(getContext(), simpleDateFormat.format(cal.getTime()),Toast.LENGTH_SHORT).show();

    }





    // Check this function before call
    private void getYearlyDetails()
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        cal.add(Calendar.DATE,1);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");



    }

    private void getMonthlyUnits()
    {
        String getMonthlyUnitsUrl = "https://ereaderv10.azurewebsites.net/api/Meters/"+premisesNo+"/1/2/3";
        //String getMonthlyUnitsUrl = "http://ereaderv10.azurewebsites.net/api/Meters/"+premisesNo+"/5/4";


        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getMonthlyUnitsUrl,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try
                            {
                                JSONArray jsonArray = response.getJSONArray("usagedetail");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);

                                usedUnits = Integer.parseInt(jsonObject.getString("monthunits").toString());

                                lblCurrentUnits.setText(String.valueOf(usedUnits));



                                if(isDomestic)
                                {
                                    calculateBillDomestic();
                                }
                                else
                                {
                                    calculateBillIndusTrial();
                                }


                            }
                            catch (JSONException ex)
                            {

                                Toast.makeText(getContext(),"Failed to getting usage data", Toast.LENGTH_SHORT).show();

                            }


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
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

    private void calculateBillDomestic()
    {
        double result = 0;

        if(usedUnits>180)
        {
            double val = 45  ;

            result = 540+(val*usedUnits);



        }
        else if(usedUnits>120)
        {
            double val = 32;

            result = 480+(val*usedUnits);




        }
        else if(usedUnits>90)
        {
            double val = 27.75;

            result = 480+(val*usedUnits);


        }
        else if(usedUnits>60)
        {
            double val = 10;

            result = 90+(val*usedUnits);

        }

        else
        {

            double val = 7.85;

            result = (val*usedUnits);

        }

      //  String.format ("%.2f", result);

        lblBillAmount.setText("Rs."+String.format ("%.2f", result));


    }

    private void calculateBillIndusTrial()
    {
        double result = 0;

        if(usedUnits>300)
        {
            double val = 12.20  ;

            result = 600+(val*usedUnits);



        }
        else
        {
            double val = 10.80;

            result = 600+(val*usedUnits);

        }
        lblBillAmount.setText("Rs."+String.valueOf(result));

        //Toast.makeText(getContext(),String.valueOf(result),Toast.LENGTH_SHORT).show();



    }


    private class WebAPITask extends AsyncTask<Void,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... params) {

            try
            {

                getMonthlyUnits();

                getDailyDetails();
                return true;

            }
            catch (Exception ex)
            {

                Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_SHORT).show();
                return  false;
            }



        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(aBoolean)
            {

                startCountAnimation();
            }

        }


    }


    // Animated method for show the Daily usage, should be get the daily usage from the web service
    private void startCountAnimation() {
        try {
            ValueAnimator animator = ValueAnimator.ofInt(0, todayUsedUnits);
            animator.setDuration(1000);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    lblTodayUnits.setText(animation.getAnimatedValue().toString());
                }
            });
            animator.start();
        }
        catch (Exception ex)
        {
            Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }

}
