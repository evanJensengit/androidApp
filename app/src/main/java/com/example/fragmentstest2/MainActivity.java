package com.example.fragmentstest2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.AssetManager;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static String previousFrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //DEBUG Log.i(TAG, "BEFORE TRY");
        //get json object and correlate json object data to the fragment class, animation and layout
        Button myFirstButton = (Button) findViewById(R.id.mainbutton);
        myFirstButton.setOnClickListener(this);
        try {
            //get json object
            AssetManager assetManager = getAssets();
            InputStream ims = assetManager.open("fragment1json.json");
            //DEBUG Log.i(TAG, "IN TRY AFTER INPUTSTREAM");
            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);

            GsonParser gsonObj = gson.fromJson(reader, GsonParser.class);
            //get classpath from json object
            String classPath = gsonObj.getClassPath();
            //DEBUG Log.i(TAG, classPath);
            //get first fragment to begin transaction
            firstFragment o = (firstFragment) Class.forName(classPath).newInstance();

            //have a variable for the layout, enterAnimation and exitAnimation, based off of those
            //I can set the id to those variables and send them in to the transaction

            //DEBUG
            Log.i(TAG, (((Object)R.id.flFragment).getClass().getSimpleName()));

            //render the fragment to user
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, o).commit();

//POTENTIAL RESOURCE FOR REFLECTION
//            ClassLoader loader = MainActivity.class.getClassLoader();
//            URL cp;
//            cp = loader.getResource(classPath);
//            String cpString = cp.toString();
//            Log.i(TAG, "CLASSPATH string :" + cpString);
//            firstFragment aFrag = (firstFragment) Class.forName(cpString).getConstructor(String.class).newInstance();
//            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, aFrag).commit();
            //Class.forName(className).getConstructor(String.class).newInstance();

        }catch(IOException | ClassNotFoundException  | IllegalAccessException | InstantiationException e) {
            Log.i(TAG, e.toString());
        }

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
            Fragment fragment = new firstFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            transaction.replace(R.id.flFragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            setPreviousFrag("exit");
        }
        else if (getPreviousFrag().equalsIgnoreCase( "secondfragment"))  {
            Fragment fragment = new secondFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            transaction.replace(R.id.flFragment, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            setPreviousFrag("firstfragment");
        }

    }
}