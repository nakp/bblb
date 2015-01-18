package net.androidsensei.pokedex.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;

import net.androidsensei.pokedex.PokedexApplication;
import net.androidsensei.pokedex.R;

import java.util.List;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    public static final String TAG = PokemonAdapter.class
            .getSimpleName();

    private Context context;

    ImageLoader imageLoader = PokedexApplication.getInstance().getImageLoader();

    public PokemonAdapter(List<Pokemon> pokemons, Context ctx) {
        super(ctx, R.layout.list_item_pokemon, pokemons);
        this.context = ctx;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_pokemon, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.list_item_pokemon_textview);
            viewHolder.imageView = (ImageView) view
                    .findViewById(R.id.list_item_pokemon_imageview);
            view.setTag(viewHolder);
        }
        final ViewHolder holder = (ViewHolder) view.getTag();
        Pokemon p = (Pokemon)getItem(i);
        holder.textView.setText(p.getNombre());
        if(p.getAvatar()==null || p.getAvatar().length()==0){
            holder.imageView.setImageResource(R.drawable.ic_launcher);
        }else{
            imageLoader.get(p.getAvatar(), ImageLoader.getImageListener(
                    holder.imageView, R.drawable.ic_launcher, R.drawable.ic_launcher));
        }

        return view;
    }

    static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }
}
