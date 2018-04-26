package mycompany.smartelectricitymonitor;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    TextView lblName,lblPremisesNo,lblUserType,lblAddress,lblAccountNo,lblModuleStatus,lblModuleNo;

    ImageView iVUserType;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        getProfileDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, 0);
        }

                lblName = (TextView)rootView.findViewById(R.id.lblName);
                lblPremisesNo = (TextView)rootView.findViewById(R.id.lblPremisesNo);
                lblUserType = (TextView)rootView.findViewById(R.id.lblUserType);
                lblAddress = (TextView)rootView.findViewById(R.id.lblAddress);
                lblAccountNo = (TextView)rootView.findViewById(R.id.lblAccountNo);
                lblModuleStatus = (TextView)rootView.findViewById(R.id.lblModuleStatus);
                lblModuleNo = (TextView)rootView.findViewById(R.id.lblModuleNo);




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

//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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

    public void getProfileDetails()
    {

        //String getUSerUrl = "https://ereaderv10.azurewebsites.net/api/Users/P-100/TEST";
        String getUserUrl = "https://ereaderv10.azurewebsites.net/api/Users/P-100/";

        try {

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getUserUrl,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try
                            {
                                JSONArray jsonArray = response.getJSONArray("Userdetail");
                                setUserDetails(jsonArray.getJSONObject(0));

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


    private void setUserDetails(JSONObject jsonObject)
    {
        try
        {

            lblName.setText(jsonObject.getString("Name"));
            //lblPremisesNo.setText(jsonObject.getString("Name"));
            lblUserType.setText(jsonObject.getString("User_Type"));
            lblAddress.setText(jsonObject.getString("Address"));
            lblAccountNo.setText(jsonObject.getString("Account_no"));
            lblModuleStatus.setText(jsonObject.getString("Health"));
            lblModuleNo.setText(jsonObject.getString("ModuleId"));



        }
        catch (JSONException ex)
        {

            Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_SHORT).show();

        }

    }



}
