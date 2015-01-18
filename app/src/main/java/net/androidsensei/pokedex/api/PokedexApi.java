package net.androidsensei.pokedex.api;

import com.android.volley.Response;

import net.androidsensei.pokedex.GsonRequest;
import net.androidsensei.pokedex.PokedexApplication;
import net.androidsensei.pokedex.model.Pokemon;

/**
 * Created by nakp on 1/18/15.
 */
public class PokedexApi {
    public static final String MI_POKEDEX_URL = "http://mi-pokedex.herokuapp.com/api/";

    public static void getPokemons (Response.Listener listener, Response.ErrorListener errorListener)
    {
        GsonRequest<Pokemon[]> getPersons =
                new GsonRequest<Pokemon[]>(PokedexApi.MI_POKEDEX_URL + "/pokemons", Pokemon[].class,
                        listener,errorListener);

        PokedexApplication.getInstance().addToRequestQueue(getPersons);
    }
}
