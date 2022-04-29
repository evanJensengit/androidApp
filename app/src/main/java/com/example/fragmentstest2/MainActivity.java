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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static String previousFrag;
    public static final String firstFrag = "firstFragment";
    public static final String secondFrag = "secondFragment";
    public static final String thirdFrag = "thirdFragment";
    public static final String exitStr = "exit";
    public static final String frag1JsonFileName = "fragment1json.json";
    public static final String frag2JsonFileName = "fragment2json.json";
    public static final String frag3JsonFileName = "fragment3json.json";

    private static GsonParser json1;
    private static GsonParser json2;
    private static GsonParser json3;

    private void setGsonparser(String jsonFileName) {
        try {
            //get json object
            InputStream ims = getAssets().open(jsonFileName);
            //AssetManager assetManager = getAssets();
            //InputStream ims = assetManager.open("fragment1json.json");
            //DEBUG Log.i(TAG, "IN TRY AFTER INPUTSTREAM");
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);
            if (jsonFileName.equalsIgnoreCase(frag1JsonFileName)) {
                json1 = gson.fromJson(reader, GsonParser.class);
            }
            else if (jsonFileName.equalsIgnoreCase(frag2JsonFileName)) {
                json2 = gson.fromJson(reader, GsonParser.class);
            }
            else if (jsonFileName.equalsIgnoreCase(frag3JsonFileName)) {
                json3 = gson.fromJson(reader, GsonParser.class);
            }


            //DEBUG Log.i(TAG, classPath);
            reader.close();
            //DEBUG
            Log.i(TAG, (((Object) R.id.flFragment).getClass().getSimpleName()));

        } catch(IOException e) {
            Log.i(TAG, e.toString());

        }
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setGsonparser(frag1JsonFileName);
        setGsonparser(frag2JsonFileName);
        setGsonparser(frag3JsonFileName);

        //DEBUG Log.i(TAG, "BEFORE TRY");
        //get json object and correlate json object data to the fragment class, animation and layout
        Button myFirstButton = (Button) findViewById(R.id.mainbutton);
        myFirstButton.setOnClickListener(this);

        String classPath = json1.getClassPath();
        //DEBUG Log.i(TAG, classPath);
        //get first fragment to begin transaction
        firstFragment o = null;
        try {
            o = (firstFragment) Class.forName(classPath).newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }

        //have a variable for the layout, enterAnimation and exitAnimation, based off of those
        //I can set the id to those variables and send them in to the transaction

        //DEBUG
        Log.i(TAG, (((Object)R.id.flFragment).getClass().getSimpleName()));

        //render the fragment to user
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, o).commit();

        //firstFragment frag1 = new firstFragment();
      //  TextView textv = (TextView) findViewById(R.id.fragmentfirst);
        //textv.setText("hi");

        //sets initial fragment to container
        //getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, frag1).commit();
    }

    public static void setPreviousFrag(String prev) {
        previousFrag = prev;
    }
    public static String getPreviousFrag() {
        return previousFrag;
    }
    @Override
    public void onClick(View view)
    {
        Log.i(TAG, "main activity on click");
        Log.i(TAG, getPreviousFrag());
        Log.i(TAG, (view.toString()));

        if (getPreviousFrag().equalsIgnoreCase( exitStr)) {
            System.exit(0);
        }
        else if (getPreviousFrag().equalsIgnoreCase(firstFrag)) {
            String classPath = json1.getClassPath();
            //DEBUG Log.i(TAG, classPath);
            //get first fragment to begin transaction
            Fragment o = null;
            try {
                o = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            String enterAnimStr = json1.getEnterAnim();
            String exitAnimStr = json1.getExitAnim();
            int enterAnim = MainActivity.getEnterAnimation(enterAnimStr);
            int exitAnim = MainActivity.getExitAnimation(exitAnimStr);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnim,exitAnim);
            transaction.replace(R.id.flFragment, o); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            setPreviousFrag(exitStr);
        }
        else if (getPreviousFrag().equalsIgnoreCase(secondFrag))  {
            String classPath = json2.getClassPath();
            //DEBUG Log.i(TAG, classPath);
            //get first fragment to begin transaction
            Fragment o = null;
            try {
                o = (Fragment) Class.forName(classPath).newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            String enterAnimStr = json2.getEnterAnim();
            String exitAnimStr = json2.getExitAnim();
            int enterAnim = MainActivity.getEnterAnimation(enterAnimStr);
            int exitAnim = MainActivity.getExitAnimation(exitAnimStr);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(enterAnim,exitAnim);
            transaction.replace(R.id.flFragment, o); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            setPreviousFrag(firstFrag);
        }

    }

    public static int getButtonID(int layoutID) {
        int button2ID = 0;
        if (layoutID == R.layout.fragment_first) {
            button2ID = R.id.f1button2;
        }
        else if (layoutID == R.layout.fragment_second) {
            button2ID = R.id.f1button2;
        }
        else if (layoutID == R.layout.fragment_third) {
            button2ID = R.id.f1button2;
        }
        return button2ID;
    }
    public static int getLayoutID(String theLayout) {
            int theLayoutID = 0;
            if (theLayout.equalsIgnoreCase( "first_fragment")) {
                theLayoutID = R.layout.fragment_first;
            }
            else if (theLayout.equalsIgnoreCase( "second_fragment")) {
                theLayoutID = R.layout.fragment_second;
            }
            else if (theLayout.equalsIgnoreCase( "third_fragment")) {
                theLayoutID = R.layout.fragment_third;
            }

        return theLayoutID;

    }
    public static int getExitAnimation(String animStr) {
        if (animStr.equalsIgnoreCase("slide_out")) {
            return R.anim.slide_out;
        }
        else if (animStr.equalsIgnoreCase("fade_out")) {
            return R.anim.fade_out;
        }
        return 0;
    }

    public static int getEnterAnimation(String animStr) {
        if (animStr.equalsIgnoreCase("fade_in")) {
            return R.anim.fade_in;
        }
        else if (animStr.equalsIgnoreCase("slide_in")) {
            return R.anim.slide_in;
        }
        return 0;
    }
    public static String getClassPathMain(String name) {
        if (name.equalsIgnoreCase( "firstFragment")) {
            return json1.getClassPath();
        }
        else if (name.equalsIgnoreCase( "secondFragment")) {
            return json1.getClassPath();
        }
        else if (name.equalsIgnoreCase( "thirdFragment")) {
            return json1.getClassPath();
        }
        return null;
    }

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