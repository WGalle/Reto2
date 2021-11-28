package com.example.reto2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ActionsActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private RecyclerView listAtrapados;
    private LinearLayoutManager layoutManager;
    private PokemonAdapter adapter;

    private ImageButton btnBack;
    private EditText editTextAtrapar;
    private SearchView searchView;
    private Button btnAtrapar;

    private String nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        btnBack = findViewById(R.id.btnBack);
        listAtrapados = findViewById(R.id.listAtrapados);
        editTextAtrapar = findViewById(R.id.editTextAtrapar);
        searchView = findViewById(R.id.searchView);
        btnAtrapar = findViewById(R.id.btnAtrapar);

        nameUser = getIntent().getExtras().getString("trainer");

        searchView.setOnQueryTextListener(this);
        btnAtrapar.setOnClickListener(this);
        btnBack.setOnClickListener(
                v -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                });

        listAtrapados.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        listAtrapados.setLayoutManager(layoutManager);

        adapter = new PokemonAdapter(this, nameUser);
        listAtrapados.setAdapter(adapter);

        getOwnPokemons();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAtrapar:
                atraparPokemon();
                editTextAtrapar.setText("");
                break;
        }
    }

    private void atraparPokemon(){
        HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();
        Gson gson = new Gson();

        new Thread(
                () -> {
                    String response = https.GETrequest(Constants.APIURL + editTextAtrapar.getText().toString().toLowerCase());

                    try {
                        JSONObject pokemonJson = new JSONObject(response);

                        String name = pokemonJson.getString("name");
                        String image = pokemonJson.getJSONObject("sprites").getString("front_default");
                        StringBuilder type = new StringBuilder();

                        JSONArray types = pokemonJson.getJSONArray("types");

                        for(int i = 0; i < types.length(); i++){
                            type.append(types.getJSONObject(i).getJSONObject("type").getString("name")).append(" ");
                        }

                        JSONArray stats = pokemonJson.getJSONArray("stats");

                        String life = stats.getJSONObject(0).getString("base_stat");
                        String attack = stats.getJSONObject(1).getString("base_stat");
                        String defense = stats.getJSONObject(2).getString("base_stat");
                        String speed = stats.getJSONObject(3).getString("base_stat");

                        Pokemon pokemon = new Pokemon(name, image, type.toString(), defense, attack, speed, life);

                        String uploadPokemon = gson.toJson(pokemon);

                        new Thread(
                                ()->{
                                    https.PUTrequest(Constants.FBURL+ "trainers/" + nameUser + "/" + pokemon.getName() + ".json", uploadPokemon );
                                }
                        ).start();

                        adapter.addPokemon(pokemon);

                        runOnUiThread(() -> adapter.notifyDataSetChanged());

                    } catch (JSONException e) {
                        runOnUiThread(() -> {
                            Toast.makeText(this, "No existe tal pokemon", Toast.LENGTH_SHORT).show();});
                    }

                }
        ).start();

    }

    private void getOwnPokemons(){
        HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();


        new Thread(
                () -> {
                    String response = https.GETrequest(Constants.FBURL + "trainers/" + nameUser + ".json");
                    Type tipo = new TypeToken<HashMap<String, Pokemon>>() {}.getType();
                    try{

                        Gson gson = new Gson();
                        HashMap<String, Pokemon> pokemons = gson.fromJson(response, tipo);

                        pokemons.forEach(
                                (key, value) -> {
                                    adapter.addPokemon(value);
                                }
                        );

                        runOnUiThread(
                                () ->{
                                    adapter.notifyDataSetChanged();
                                });

                    }catch (Exception e){
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Inicia a atrapar pokemones!", Toast.LENGTH_SHORT).show();
                        });
                    }

                }
        ).start();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ArrayList<Pokemon> original = adapter.getOriginalPokemons();
        ArrayList<Pokemon> pokemon = adapter.getPokemons();

        adapter.filterSearch(newText);
        adapter = new PokemonAdapter(this, nameUser);
        adapter.setPokemons(original, pokemon);
        listAtrapados.setAdapter(adapter);

        for(int i=0; i<adapter.getPokemons().size(); i++){
            System.out.println(adapter.getPokemons().get(i).getName());
        }
        return false;
    }
}
