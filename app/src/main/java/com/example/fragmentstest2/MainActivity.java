package com.example.fragmentstest2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.AssetManager;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//Run in android studio
//        Program developed and tested with Pixel 3 XL API 30 Android Emulator
//        JSON files are in assets folder
//
//        To add new animations:
//        add xml files with animation content to res/anim folder
//        add the animation name to json file
//        add animation name to MainActivity.getEnterAnim and MainActivity.getExitAnim and return the
//        R.anim.<animation_file_name>
//
//To add new layouts:
//        add xml files with layout content to res/layout folder
//        add the layout name to json file
//        add layout name to MainActivity.getLayoutID and return the
//        R.layout.<layout_file_name>
//
//If new layouts include buttons add buttonID to MainActivity.getButtonID
//        and return the R.id.<buttonID> correlated with the layout that has that button
//ASSUMPTIONS
//class path has format of "app.java.com.example.<classnameaslaststring>"
// by deliminated by "."
//values referenced in json files are implemented in file structure of project


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static String previousFrag;
    public static final String firstFrag = "firstFragment";
    public static final String secondFrag = "secondFragment";
    public static final String exitStr = "exit";
    public static final String frag1JsonFileName = "fragment1json.json";
    public static final String frag2JsonFileName = "fragment2json.json";
    public static final String frag3JsonFileName = "fragment3json.json";

    private static String classNamesString = "";
    private static GsonParser json1;
    private static GsonParser json2;
    private static GsonParser json3;
    private static String firstClassName;
    private static String secondClassName;
    private static String thirdClassName;

    public static String getFirstClassName() {
        return firstClassName;
    }
    public static String getSecondClassName() {
        return secondClassName;
    }
    public static String getThirdClassName() {
        return thirdClassName;
    }
    public static String getCumulativeClassName() {
        return classNamesString;
    }
    public static void setCumulativeClassName(String toSet) {
        classNamesString = toSet;
    }

    private void setGsonparser(String jsonFileName) {
        try {
            //get json object
            InputStream ims = getAssets().open(jsonFileName);

            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);
            //create json1 GsonParser object
            if (jsonFileName.equalsIgnoreCase(frag1JsonFileName)) {
                json1 = gson.fromJson(reader, GsonParser.class);
                String classPath1 = json1.getClassPath();
                String[] className = classPath1.split("");
                int count = 0;
                //get class name from string after last period
                for (int i = 0; i < className.length; i++) {
                    if (className[i].equalsIgnoreCase(".")) {
                        count = i;
                    }
                }
                firstClassName = classPath1.substring(count+1);
            }
            //create json2 GsonParser object
            else if (jsonFileName.equalsIgnoreCase(frag2JsonFileName)) {
                json2 = gson.fromJson(reader, GsonParser.class);
                String classPath1 = json2.getClassPath();
                String[] className = classPath1.split("");
                int count = 0;
                //get class name from string after last period
                for (int i = 0; i < className.length; i++) {
                    if (className[i].equalsIgnoreCase(".")) {
                        count = i;
                    }
                }
                secondClassName = classPath1.substring(count+1);
            }
            //create json3 GsonParser object
            else if (jsonFileName.equalsIgnoreCase(frag3JsonFileName)) {
                json3 = gson.fromJson(reader, GsonParser.class);
                String classPath1 = json3.getClassPath();
                String[] className = classPath1.split("");
                int count = 0;
                //get class name from string after last period
                for (int i = 0; i < className.length; i++) {
                    if (className[i].equalsIgnoreCase(".")) {
                        count = i;
                    }
                }
                thirdClassName = classPath1.substring(count+1);
            }
            reader.close();
        } catch(IOException e) {
            Log.i(TAG, e.toString());
        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreviousFrag("exit");

        setContentView(R.layout.activity_main);
        //create the GsonParser data members from json files
        setGsonparser(frag1JsonFileName);
        setGsonparser(frag2JsonFileName);
        setGsonparser(frag3JsonFileName);


        //create the back button on MainActivity to be shown on each fragment layout
        Button myFirstButton = (Button) findViewById(R.id.mainbutton);
        myFirstButton.setOnClickListener(this);

        String classPath = json1.getClassPath();

        //get first fragment to begin transaction
        firstFragment o = null;
        try {
            o = (firstFragment) Class.forName(classPath).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
        //render the fragment to user
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, o).commit();
    }
    //sets previous fragment
    public static void setPreviousFrag(String prev) {
        previousFrag = prev;
    }
    //returns previous fragment as string
    public static String getPreviousFrag() {
        return previousFrag;
    }
    //onClick method refers to if the back button is clicked on the app
    //this method decides what layout and fragment to display based on the previous fragment
    //displayed which is stored in previousFrag variable
    @Override
    public void onClick(View view)
    {
        //previous fragment was nothing (meaning program is displaying fragment1
        //exit app
        if (getPreviousFrag().equalsIgnoreCase(exitStr)) {
            System.exit(0);
        }
        //previous fragment was fragment1 so go back to fragment1
        else if (getPreviousFrag().equalsIgnoreCase(firstFrag)) {
            String classPath = json1.getClassPath();

            //get first fragment to begin transaction
            Fragment fragment = null;
            try {
                fragment = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            //get animations from json from gson object for this fragment
            String enterAnimStr = json1.getEnterAnim();
            String exitAnimStr = json1.getExitAnim();
            int enterAnim = MainActivity.getEnterAnimation(enterAnimStr);
            int exitAnim = MainActivity.getExitAnimation(exitAnimStr);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnim,exitAnim);
            assert fragment != null;
            transaction.replace(R.id.flFragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            setPreviousFrag(exitStr);
        }
        //previous fragment was fragment2 (meaning fragment3 is being displayed
        // so go back to fragment2
        else if (getPreviousFrag().equalsIgnoreCase(secondFrag))  {
            String classPath = json2.getClassPath();

            //get first fragment to begin transaction
            Fragment fragment = null;
            try {
                fragment = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            //get animations from json from gson object for this fragment
            String enterAnimStr = json2.getEnterAnim();
            String exitAnimStr = json2.getExitAnim();
            int enterAnim = MainActivity.getEnterAnimation(enterAnimStr);
            int exitAnim = MainActivity.getExitAnimation(exitAnimStr);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnim,exitAnim);
            assert fragment != null;
            transaction.replace(R.id.flFragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            setPreviousFrag(firstFrag);
        }

    }
    //returns buttonid correlated with layoutid passed
    public static int getButtonID(int layoutID) {
        int button2ID = 0;
        if (layoutID == R.layout.fragment_first) {
            button2ID = R.id.f1button2;
        }
        else if (layoutID == R.layout.fragment_second) {
            button2ID = R.id.f2button2;
        }
        else if (layoutID == R.layout.fragment_third) {
            button2ID = R.id.f3button2;
        }
        return button2ID;
    }
    //returns layoutID correlated with the layout string passed to method
    public static int getLayoutID(String theLayout) {
        int theLayoutID = -1;
        if (theLayout.equalsIgnoreCase( "fragment_first")) {
            theLayoutID = R.layout.fragment_first;
        }
        else if (theLayout.equalsIgnoreCase( "fragment_second")) {
            theLayoutID = R.layout.fragment_second;
        }
        else if (theLayout.equalsIgnoreCase( "fragment_third")) {
            theLayoutID = R.layout.fragment_third;
        }

        return theLayoutID;

    }

    //returns animationID correlated with the animation string passed to method
    public static int getExitAnimation(String animStr) {
        if (animStr.equalsIgnoreCase("slide_out")) {
            return R.anim.slide_out;
        }
        else if (animStr.equalsIgnoreCase("fade_out")) {
            return R.anim.fade_out;
        }
        return -1;
    }
    //returns animationID correlated with the animation string passed to method
    public static int getEnterAnimation(String animStr) {
        if (animStr.equalsIgnoreCase("fade_in")) {
            return R.anim.fade_in;
        }
        else if (animStr.equalsIgnoreCase("slide_in")) {
            return R.anim.slide_in;
        }
        return -1;
    }
    //returns gsonParser object correlated with the string passed to method
    public static GsonParser getGson(String jsonName) {
        if (jsonName.equalsIgnoreCase("json1")) {
            return new GsonParser(json1);
        }
        else if (jsonName.equalsIgnoreCase("json2")) {
            return new GsonParser(json2);
        }
        else if (jsonName.equalsIgnoreCase("json3")) {
            return new GsonParser(json3);
        }
        return null;
    }
}