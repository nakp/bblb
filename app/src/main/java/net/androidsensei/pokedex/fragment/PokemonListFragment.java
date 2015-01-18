package net.androidsensei.pokedex.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.androidsensei.pokedex.R;
import net.androidsensei.pokedex.api.PokedexApi;
import net.androidsensei.pokedex.model.Pokemon;
import net.androidsensei.pokedex.model.PokemonAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PokemonListFragment extends Fragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    private static Callbacks sDummyCallbacks = new Callbacks() {

        @Override
        public void onItemSelected(Pokemon pokemon) {

        }
    };
    private Callbacks mCallbacks = sDummyCallbacks;
    private PokemonAdapter pokemonAdapter;
    private ProgressBar progressBarLoading;
    private ListView listView;

    public PokemonListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Activity must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        progressBarLoading = (ProgressBar) rootView.findViewById(R.id.progressbar_loading);
        listView = (ListView) rootView.findViewById(R.id.listview_pokemon);

        if (pokemonAdapter == null) {
            List<Pokemon> pokemonList = new ArrayList<Pokemon>();
            pokemonAdapter =
                    new PokemonAdapter(pokemonList, getActivity());
            getPokemonsForApi();
        }

        listView.setAdapter(pokemonAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pokemon pokemon = (Pokemon) listView.getAdapter().getItem(i);
                mCallbacks.onItemSelected(pokemon);
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        listView.setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            listView.setItemChecked(0, false);
        } else {
            listView.setItemChecked(position, true);
        }
    }

    public void getPokemonsForApi() {
        /*PokemonListApiTask apiTask = new PokemonListApiTask(pokemonAdapter,
                listView,progressBarLoading);
        apiTask.execute();*/
        listView.setVisibility(View.INVISIBLE);
        progressBarLoading.setVisibility(View.VISIBLE);

        PokedexApi.getPokemons(new Response.Listener<Pokemon[]>() {
            @Override
            public void onResponse(Pokemon[] response) {
                List<Pokemon> pokemons = Arrays.asList(response);
                if (pokemons != null) {
                    pokemonAdapter.clear();
                    pokemonAdapter.addAll(pokemons);
                }
                progressBarLoading.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBarLoading.setVisibility(View.INVISIBLE);
                listView.setVisibility(View.VISIBLE);
            }
        });
    }


    public interface Callbacks {
        public void onItemSelected(Pokemon pokemon);
    }

}
