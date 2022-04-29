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
 */
public class thirdFragment extends Fragment  {

    public thirdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int theLayoutID = 0;
        int button2ID = 0;

        GsonParser gson = MainActivity.getGson("json3");
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
        TextView viewOfTextCurrentFragment  = (TextView) view.findViewById(R.id.fragmentthird);
        String tempTextSetter = MainActivity.getFirstClassName() + ", " + MainActivity.getSecondClassName();
        viewOfTextCurrentFragment.setText(tempTextSetter);
        MainActivity.setCumulativeClassName(tempTextSetter + ", " + MainActivity.getThirdClassName());

        button2ID = MainActivity.getButtonID(theLayoutID);
        Button button2 = (Button) view.findViewById(button2ID);
        button2.setOnClickListener(v -> {
            //if button clicked render fragment2 with json2 data
            GsonParser gson2 = MainActivity.getGson("json1");
            assert gson2 != null;
            String classPath = gson2.getClassPath();
            //DEBUG Log.i(TAG, classPath);
            //get first fragment to begin transaction
            Fragment fragment = null;
            try {
                fragment = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | java.lang.InstantiationException |
                    ClassNotFoundException illegalAccessException) {
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