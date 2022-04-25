package com.example.fragmentstest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.se.omapi.Reader;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            AssetManager assetManager = getAssets();
            InputStream ims = assetManager.open("fragment1json.json");

            Gson gson = new Gson();
            InputStreamReader reader = new InputStreamReader(ims);

            GsonParser gsonObj = gson.fromJson(reader, GsonParser.class);
            String classPath = gsonObj.getClassPath();


            Log.i(TAG, classPath);
            ClassLoader loader = MainActivity.class.getClassLoader();
            URL cp = loader.getResource(classPath);
            String cpString = cp.toString();
            System.out.println(cpString);
            Class.forName(cpString).getConstructor(String.class).newInstance();
            //Class.forName(className).getConstructor(String.class).newInstance();

        }catch(IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            Log.i(TAG, );
        }

        firstFragment frag1 = new firstFragment();
      //  TextView textv = (TextView) findViewById(R.id.fragmentfirst);
        //textv.setText("hi");

        //sets initial fragment to container
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, frag1).commit();


    }
}