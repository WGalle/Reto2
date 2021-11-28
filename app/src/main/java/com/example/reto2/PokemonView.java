package com.example.reto2;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PokemonView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;
    private TextView name;
    private ImageView image;
    private String nameUser;
    private Pokemon pokemon;

    private Activity activity;

    public PokemonView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.pokemonRowName);
        image = root.findViewById(R.id.pokemonRowImage);


        root.setOnClickListener(
                v -> {

                    Intent intent = new Intent(activity ,PokemonActivity.class);
                    intent.putExtra("trainer", nameUser);

                    intent.putExtra("name",pokemon.getName());
                    intent.putExtra("img",pokemon.getImage());
                    intent.putExtra("type",pokemon.getType());
                    intent.putExtra("attack",pokemon.getAttack());
                    intent.putExtra("defense",pokemon.getDefense());
                    intent.putExtra("speed",pokemon.getSpeed());
                    intent.putExtra("hp",pokemon.getLife());
                    intent.putExtra("url", nameUser);

                    activity.startActivityForResult(intent, 20);

                });

    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getName() {
        return name;
    }

    public ImageView getImage() {
        return image;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public void setnameUser(String nameUser){
        this.nameUser = nameUser;
    }

    public void setPokemon(Pokemon pokemon) { this.pokemon = pokemon; }
}
