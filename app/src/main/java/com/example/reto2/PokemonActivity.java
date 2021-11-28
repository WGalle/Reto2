package com.example.reto2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PokemonActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imagePokemon;
    private TextView namePokemon;
    private TextView typePokemon;
    private TextView defensaPokemon;
    private TextView ataquePokemon;
    private TextView velocidadPokemon;
    private TextView vidaPokemon;
    private ImageButton btnBack2;
    private Button btnLiberar;

    private String nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        imagePokemon = findViewById(R.id.imagePokemon);
        namePokemon = findViewById(R.id.namePokemon);
        typePokemon = findViewById(R.id.typePokemon);
        defensaPokemon = findViewById(R.id.defensaPokemon);
        ataquePokemon = findViewById(R.id.ataquePokemon);
        velocidadPokemon = findViewById(R.id.velocidadPokemon);
        vidaPokemon = findViewById(R.id.vidaPokemon);
        btnBack2 = findViewById(R.id.btnBack2);
        btnLiberar = findViewById(R.id.btnLiberar);

        namePokemon.setText(getIntent().getExtras().getString("name"));
        typePokemon.setText(getIntent().getExtras().getString("type"));
        defensaPokemon.setText(getIntent().getExtras().getString("defense"));
        ataquePokemon.setText(getIntent().getExtras().getString("attack"));
        velocidadPokemon.setText(getIntent().getExtras().getString("speed"));
        vidaPokemon.setText(getIntent().getExtras().getString("hp"));

        nameUser = getIntent().getExtras().getString("trainer");

        String url = getIntent().getExtras().getString("img");
        Glide.with(this).load(url).fitCenter().into(imagePokemon);

        btnBack2.setOnClickListener(
                v -> {
                    Intent intent = new Intent(this, ActionsActivity.class);
                    intent.putExtra("trainer", nameUser);
                    startActivity(intent);
                });

        btnLiberar.setOnClickListener(
                v -> {
                    setFreePokemon();
                }
        );

    }

    public void setFreePokemon(){

        HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();

        new Thread(
                ()->{
                    https.DELETErequest(Constants.FBURL+ "trainers/" + nameUser + "/" + namePokemon.getText().toString() + ".json");
                }
        ).start();

        Toast.makeText(getBaseContext(), "Se ha liberado tu pokemon", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK, null);

        Intent intent = new Intent(this, ActionsActivity.class);
        intent.putExtra("trainer", nameUser);
        startActivity(intent);

        finish();
    }

    @Override
    public void onClick(View v) {

    }
}
