package net.androidsensei.pokedex.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import net.androidsensei.pokedex.model.Pokemon;
import net.androidsensei.pokedex.fragment.PokemonDetailFragment;
import net.androidsensei.pokedex.R;

public class PokemonDetailActivity extends ActionBarActivity {

    private Pokemon mCurrentPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            mCurrentPokemon = savedInstanceState.getParcelable("mCurrentPokemon");
        } else {
            Pokemon pokemon = getIntent().getParcelableExtra("pokemon");
            mCurrentPokemon = pokemon;
        }

        PokemonDetailFragment fragment = PokemonDetailFragment.newInstance(mCurrentPokemon);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pokemon_detail_container, fragment).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("mCurrentPokemon", mCurrentPokemon);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
