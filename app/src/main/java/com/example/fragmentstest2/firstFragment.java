package com.example.fragmentstest2;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;

import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A simple {@link Fragment} subclass.
 */
public class firstFragment extends Fragment  {

    public firstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int theLayoutID = 0;
        int button2ID = 0;
        //get gson object from json file
        GsonParser gson = MainActivity.getGson("json1");

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
        //inflate view of layout correlated with layoutID
        View view = inflater.inflate(theLayoutID, container, false);
        //Change the text displayed
        TextView viewOfTextCurrentFragment  = (TextView) view.findViewById(R.id.fragmentfirst);
        viewOfTextCurrentFragment.setText(MainActivity.getCumulativeClassName());

        //get button correlated with layout
        button2ID = MainActivity.getButtonID(theLayoutID);
        Button button2 = (Button) view.findViewById(button2ID);

        button2.setOnClickListener(v -> {
            //if button clicked render fragment2 with json2 data
            GsonParser gson2 = MainActivity.getGson("json2");
            assert gson2 != null;
            String classPath = gson2.getClassPath();

            //get first fragment to begin transaction
            Fragment fragment = null;
            try {
                fragment = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | java.lang.InstantiationException | ClassNotFoundException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            //get animations from json from gson object for this fragment
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
            MainActivity.setPreviousFrag(MainActivity.firstFrag);
        });
        return view;
    }
}