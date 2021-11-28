package com.example.reto2;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonView> {

    private ArrayList<Pokemon> pokemons;
    private ArrayList<Pokemon> originalPokemons;
    private Activity activity;
    private String nameUser;

    public PokemonAdapter(Activity activity, String nameUser){
        pokemons = new ArrayList<>();
        originalPokemons = new ArrayList<>();
        originalPokemons.addAll(pokemons);
        this.activity = activity;
        this.nameUser = nameUser;
    }

    public void filterSearch(String search){
        if(search.length()==0){
            pokemons.clear();
            pokemons.addAll(originalPokemons);
        } else {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                pokemons.clear();
                List<Pokemon> collect = originalPokemons.stream()
                        .filter(i -> i.getName().toLowerCase().contains(search))
                        .collect(Collectors.toList());

                pokemons.addAll(collect);
            }
        }
    }

    public void addPokemon(Pokemon pokemon){
        pokemons.add(pokemon);
        pokemons.sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                if (p1.getCatchDate().after(p2.getCatchDate())) {
                    return 1;
                } else if (p2.getCatchDate().after(p1.getCatchDate())) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        originalPokemons.add(pokemon);
        originalPokemons.sort(new Comparator<Pokemon>() {
            @Override
            public int compare(Pokemon p1, Pokemon p2) {
                if (p1.getCatchDate().after(p2.getCatchDate())) {
                    return 1;
                } else if (p2.getCatchDate().after(p1.getCatchDate())) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    @NonNull
    @Override
    public PokemonView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //XML --> View
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.pokemonrow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        PokemonView pokemonView = new PokemonView(rowroot);

        return pokemonView;
    }

    @Override
    public void onBindViewHolder(PokemonView holder, int position) {
        holder.setActivity(activity);
        holder.setnameUser(nameUser);
        holder.getName().setText(pokemons.get(position).getName());
        Glide.with(activity).load(pokemons.get(position).getImage()).fitCenter().into(holder.getImage());
        holder.setPokemon(pokemons.get(position));

    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }
    public ArrayList<Pokemon> getOriginalPokemons() { return originalPokemons; }

    public void setPokemons(ArrayList<Pokemon> originalPokemons, ArrayList<Pokemon> pokemons){
        this.originalPokemons = originalPokemons;
        this.pokemons = pokemons;
    }

}
