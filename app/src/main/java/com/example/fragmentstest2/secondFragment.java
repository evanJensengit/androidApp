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
 * Use the {@link secondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class secondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String className = "secondFragment";
    private static final String TAG = "SecondFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public secondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment secondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static secondFragment newInstance(String param1, String param2) {
        secondFragment fragment = new secondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int theLayoutID = 0;
        int button2ID = 0;

        GsonParser gson = MainActivity.getGson("json2");
        assert gson != null;
        String theLayout = gson.getLayoutResource();

        //factory which sets the layout that will be inflated in this fragment
        theLayoutID = MainActivity.getLayoutID(theLayout);

        if (theLayoutID == -1) {
            try {
                throw new Exception( "LayoutID " + theLayout + " not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        View view = inflater.inflate(theLayoutID, container, false);
        TextView viewOfTextCurrentFragment  = (TextView) view.findViewById(R.id.fragmentsecond);
        String tempTextSetter = MainActivity.getFirstClassName();
        viewOfTextCurrentFragment.setText(tempTextSetter);
        MainActivity.setCumulativeClassName("");


        button2ID = MainActivity.getButtonID(theLayoutID);
        Button button2 = (Button) view.findViewById(button2ID);
        button2.setOnClickListener(v -> {
            //if button clicked render fragment2 with json2 data
            GsonParser gson2 = MainActivity.getGson("json3");
            assert gson2 != null;
            String classPath = gson2.getClassPath();
            //DEBUG Log.i(TAG, classPath);
            //get first fragment to begin transaction
            Fragment fragment = null;
            try {
                fragment = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | java.lang.InstantiationException | ClassNotFoundException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            String enterAnimStr = gson2.getEnterAnim();
            String exitAnimStr = gson2.getExitAnim();
            int enterAnim = MainActivity.getEnterAnimation(enterAnimStr);
            int exitAnim = MainActivity.getExitAnimation(exitAnimStr);
            FragmentTransaction transaction;
            assert fragment != null;

            if (enterAnim == -1 || exitAnim == -1) {
                transaction = getParentFragmentManager().beginTransaction();
            }
            else {
                transaction = getParentFragmentManager().beginTransaction().setCustomAnimations(enterAnim, exitAnim);
            }
            transaction.replace(R.id.flFragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();

            MainActivity.setPreviousFrag(MainActivity.secondFrag);
        });

        return view;
    }
}