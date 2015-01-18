package net.androidsensei.pokedex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import net.androidsensei.pokedex.model.Pokemon;
import net.androidsensei.pokedex.fragment.PokemonDetailFragment;
import net.androidsensei.pokedex.fragment.PokemonListFragment;
import net.androidsensei.pokedex.R;

public class MainActivity extends ActionBarActivity implements
        PokemonListFragment.Callbacks {

    private boolean mTwoPane;

    private Pokemon mCurrentPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        if (findViewById(R.id.pokemon_detail_container) != null) {
            mTwoPane = true;

            ((PokemonListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.pokemon_list))
                    .setActivateOnItemClick(true);
        }

        if (savedInstanceState == null && mTwoPane) {
            PokemonDetailFragment fragment = PokemonDetailFragment.newInstance(null);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.pokemon_detail_container, fragment).commit();
        }
    }

    @Override
    public void onItemSelected(Pokemon pokemon) {
        mCurrentPokemon = pokemon;
        if (mTwoPane) {
            PokemonDetailFragment fragment = PokemonDetailFragment.newInstance(mCurrentPokemon);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pokemon_detail_container, fragment).commit();
        } else {
            Intent detailIntent = new Intent(this, PokemonDetailActivity.class);
            detailIntent.putExtra("pokemon", pokemon);
            startActivity(detailIntent);
        }
    }
}
