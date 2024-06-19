package com.example.androiddecompile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private MaterialButton menu_BTN_start;
    private TextInputEditText menu_EDT_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initViews();

    }


    private void initViews() {
        this.menu_BTN_start.setOnClickListener(new View.OnClickListener() { // from class: com.classy.survivegame.Activity_Menu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                makeServerCall();
            }
        });
    }

    private void findViews() {
        this.menu_BTN_start = (MaterialButton) findViewById(R.id.menu_BTN_start);
        this.menu_EDT_id = (TextInputEditText) findViewById(R.id.menu_EDT_id);
    }

    public void makeServerCall() {
        Thread thread = new Thread() { // from class: com.classy.survivegame.Activity_Menu.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                String url = "https://pastebin.com/raw/T67TVJG9";
                String data = MainActivity.getJSON(url);
                Log.d("pttt", data);
                if (data != null) {
                    MainActivity activity_Menu = MainActivity.this;
                    activity_Menu.startGame(activity_Menu.menu_EDT_id.getText().toString(), data);
                }
            }
        };
        thread.start();
    }

    public void startGame(String id, String data) {
        String[] splits = data.split(",");
        String state = splits[Integer.valueOf(String.valueOf(id.charAt(7))).intValue()];
        Intent intent = new Intent(getBaseContext(), (Class<?>) GameActivity.class);
        Log.d("INFO", "startGame: " + id + " " + state);
        intent.putExtra(GameActivity.EXTRA_ID, id);
        intent.putExtra(GameActivity.EXTRA_STATE, state);
        startActivity(intent);
    }

    public static String getJSON(String url) {
        String data = "";
        HttpsURLConnection con = null;
        try {
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            try {
                URL u = new URL(url);
                con = (HttpsURLConnection) u.openConnection();
                con.connect();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line + "\n");
                }
                br.close();
                data = sb.toString();
            } catch (MalformedURLException ex2) {
                ex2.printStackTrace();
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException ex3) {
                ex3.printStackTrace();
                if (con != null) {
                    con.disconnect();
                }
            }
            if (con != null) {
                con.disconnect();
            }
            return data;
        } catch (Throwable th) {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex4) {
                    ex4.printStackTrace();
                }
            }
            throw th;
        }
    }

}