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

    private static GsonParser json1;
    private GsonParser json2;
    private GsonParser json3;

    private boolean setGsonparser1(String jsonFileName) {
        try {
            //get json object
            InputStream ims = getAssets().open(jsonFileName);
            //AssetManager assetManager = getAssets();
            //InputStream ims = assetManager.open("fragment1json.json");
            //DEBUG Log.i(TAG, "IN TRY AFTER INPUTSTREAM");
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);
            json1 = gson.fromJson(reader, GsonParser.class);


            //DEBUG Log.i(TAG, classPath);
            reader.close();
            //DEBUG
            Log.i(TAG, (((Object) R.id.flFragment).getClass().getSimpleName()));

        } catch(IOException e) {
            Log.i(TAG, e.toString());
            return false;
        }
        return true;
    }

    private boolean setGsonparser2(String jsonFileName) {
        try {
            //get json object
            InputStream ims = getAssets().open(jsonFileName);
            //AssetManager assetManager = getAssets();
            //InputStream ims = assetManager.open("fragment1json.json");
            //DEBUG Log.i(TAG, "IN TRY AFTER INPUTSTREAM");
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);
            json2 = gson.fromJson(reader, GsonParser.class);
            //get classpath from json object

            //DEBUG Log.i(TAG, classPath);

            //DEBUG
            Log.i(TAG, (((Object) R.id.flFragment).getClass().getSimpleName()));
            reader.close();
        } catch(IOException e) {
            Log.i(TAG, e.toString());
            return false;
        }
        return true;
    }

    private boolean setGsonparser3(String jsonFileName) {
        try {
            //get json object
            InputStream ims = getAssets().open(jsonFileName);
            //AssetManager assetManager = getAssets();
            //InputStream ims = assetManager.open("fragment1json.json");
            //DEBUG Log.i(TAG, "IN TRY AFTER INPUTSTREAM");
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);
            json3 = gson.fromJson(reader, GsonParser.class);
            //get classpath from json object

            //DEBUG Log.i(TAG, classPath);
            reader.close();
            //DEBUG
            Log.i(TAG, (((Object) R.id.flFragment).getClass().getSimpleName()));

        } catch(IOException e) {
            Log.i(TAG, e.toString());
            return false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setGsonparser1("fragment1json.json");
        setGsonparser2("fragment2json.json");
        setGsonparser3("fragment3json.json");

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
        Log.i(TAG, "mainactivity on click");
        Log.i(TAG, getPreviousFrag());
        Log.i(TAG, (view.toString()));

        if (getPreviousFrag().equalsIgnoreCase( "exit")) {
            System.exit(0);
        }
        else if (getPreviousFrag().equalsIgnoreCase( "firstfragment")) {
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
            setPreviousFrag("exit");
        }
        else if (getPreviousFrag().equalsIgnoreCase( "secondfragment"))  {
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
            setPreviousFrag("firstfragment");
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
}