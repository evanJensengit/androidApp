package com.example.fragmentstest2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link thirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class thirdFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "ThirdFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public thirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment thirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static thirdFragment newInstance(String param1, String param2) {
        thirdFragment fragment = new thirdFragment();
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
        int theLayoutID = 0;
        int button2ID = 0;
        int exitAnimID = 0;
        int enterAnimID = 0;

        try {

            //get json object
            InputStream ims = getActivity().getAssets().open("fragment3json.json");
            //AssetManager assetManager = getAssets();
            //InputStream ims = assetManager.open("fragment1json.json");
            //DEBUG Log.i(TAG, "IN TRY AFTER INPUTSTREAM");
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);
            GsonParser gsonObj = gson.fromJson(reader, GsonParser.class);
            //get classpath from json object
            String classPath = gsonObj.getClassPath();
            //DEBUG Log.i(TAG, classPath);
            //get first fragment to begin transaction
            Fragment o = (Fragment) Class.forName(classPath).newInstance();

            String theLayout = gsonObj.getLayoutResource();
            if (theLayout.equalsIgnoreCase( "first_fragment")) {
                theLayoutID = R.layout.fragment_first;
                button2ID = R.id.f1button2;
            }
            else if (theLayout.equalsIgnoreCase( "second_fragment")) {
                theLayoutID = R.layout.fragment_second;
                button2ID = R.id.f2button2;
            }
            else if (theLayout.equalsIgnoreCase( "third_fragment")) {
                theLayoutID = R.layout.fragment_third;
                button2ID = R.id.f3button2;
            }

            //DEBUG
            Log.i(TAG, (((Object) R.id.flFragment).getClass().getSimpleName()));

        } catch(IOException | ClassNotFoundException  | IllegalAccessException | java.lang.InstantiationException e) {
            Log.i(TAG, e.toString());
        }
        View view = inflater.inflate(theLayoutID, container, false);
        //Button backButton = (Button) view.findViewById(R.id.mainbutton);
        Button button2 = (Button) view.findViewById(button2ID);

        //next button clicked
        button2.setOnClickListener(v -> {
            InputStream ims = null;
            //get data for first fragment json
            try {
                ims = getActivity().getAssets().open("fragment1json.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //AssetManager assetManager = getAssets();
            //InputStream ims = assetManager.open("fragment1json.json");
            //DEBUG Log.i(TAG, "IN TRY AFTER INPUTSTREAM");
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);
            GsonParser gsonObj = gson.fromJson(reader, GsonParser.class);
            //get classpath from json object
            String classPath = gsonObj.getClassPath();
            //DEBUG Log.i(TAG, classPath);
            //get first fragment to begin transaction
            Fragment o = null;
            try {
                o = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | java.lang.InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            String enterAnimStr = gsonObj.getEnterAnim();
            String exitAnimStr = gsonObj.getExitAnim();
            int enterAnim = MainActivity.getEnterAnimation(enterAnimStr);
            int exitAnim = MainActivity.getEnterAnimation(exitAnimStr);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction().setCustomAnimations(enterAnim,exitAnim);
            transaction.replace(R.id.flFragment, o); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            MainActivity.setPreviousFrag("exit");
        });

        return view;
    }

//    @Override
//    public void onClick(View view) {
//        Fragment fragment= new firstFragment();
//        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//        transaction.replace(R.id.flFragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
//        transaction.addToBackStack(null);  // this will manage backstack
//        transaction.commit();
//
//    }
}