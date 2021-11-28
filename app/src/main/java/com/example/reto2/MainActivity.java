package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUser;
    private Button btnIngresar;
    private String nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUser = findViewById(R.id.editTextUser);
        btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnIngresar:

                nameUser = editTextUser.getText().toString();
                System.out.println(nameUser);

                if(nameUser.length()>0) {
                    Gson gson = new Gson();
                    String json = gson.toJson(nameUser);
                    HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();
                    new Thread(
                            () -> {
                                if(https.GETrequest(Constants.FBURL + "trainers/" + nameUser + ".json").equals("null"))

                                    https.PUTrequest(Constants.FBURL + "trainers/" + nameUser + ".json", json);
                            }
                    ).start();

                    Intent intent = new Intent(this, ActionsActivity.class);
                    intent.putExtra("trainer", nameUser);
                    startActivity(intent);

                    break;
                }
        }
    }
    public String getNameUser() {
        return nameUser;
    }
}