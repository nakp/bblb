package net.androidsensei.pokedex.api;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import net.androidsensei.pokedex.model.Pokemon;
import net.androidsensei.pokedex.model.PokemonAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PokemonListApiTask extends AsyncTask<Void, Void, ArrayList<Pokemon>> {

    private final String LOG_TAG = PokemonListApiTask.class.getSimpleName();


    private PokemonAdapter mPokemonAdapter;
    private ListView pokemonList;
    private ProgressBar progressBarLoading;

    public PokemonListApiTask(PokemonAdapter mPokemonAdapter,
                               ListView pokemonList, ProgressBar progressBarLoading){
        this.mPokemonAdapter = mPokemonAdapter;
        this.pokemonList = pokemonList;
        this.progressBarLoading = progressBarLoading;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.pokemonList.setVisibility(View.INVISIBLE);
        this.progressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(ArrayList<Pokemon> result) {
        if (result != null) {
            mPokemonAdapter.clear();
            for(Pokemon pokemon : result) {
                mPokemonAdapter.add(pokemon);
            }
        }
        this.progressBarLoading.setVisibility(View.INVISIBLE);
        this.pokemonList.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Pokemon> doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String pokemonJsonStr = null;

        try {

            URL url = new URL(PokedexApi.MI_POKEDEX_URL + "pokemons");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            pokemonJsonStr = buffer.toString();

            JSONArray pokemonsArray = new JSONArray(pokemonJsonStr);
            ArrayList<Pokemon> results = new ArrayList<Pokemon>();
            for(int i = 0; i < pokemonsArray.length(); i++) {
                JSONObject pokemonJson = pokemonsArray.getJSONObject(i);
                Pokemon pokemon = new Pokemon();
                pokemon.setNombre(pokemonJson.getString("nombre"));
                pokemon.setAvatar(pokemonJson.getString("avatar"));
                results.add(pokemon);
            }
            return results;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }

    }
}
