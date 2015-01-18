package net.androidsensei.pokedex.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import net.androidsensei.pokedex.data.PokedexContract.PokemonEntry;
import net.androidsensei.pokedex.data.PokedexContract.LugarEntry;

/**
 * Created by hkadejo on 12-10-14.
 */
public class PokedexDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "pokedex.db";
    public static final int DATABASE_VERSION = 1;

    public PokedexDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_POKEMON_TABLE = "CREATE TABLE " + PokemonEntry.TABLE_NAME + " (" +
                PokemonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PokemonEntry.COLUMN_AVATAR + " TEXT NOT NULL, " +
                PokemonEntry.COLUMN_NOMBRE + " TEXT NOT NULL, " +
                PokemonEntry.COLUMN_NUMERO + " INTEGER NOT NULL, " +
                PokemonEntry.COLUMN_ALTURA + " REAL NOT NULL, " +
                PokemonEntry.COLUMN_PESO + " REAL NOT NULL );";

        final String SQL_CREATE_LUGAR_TABLE = "CREATE TABLE " + LugarEntry.TABLE_NAME + " (" +
                LugarEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LugarEntry.COLUMN_LATITUD + " REAL NOT NULL, " +
                LugarEntry.COLUMN_POKEMON_ID + " INTEGER NOT NULL, " +
                LugarEntry.COLUMN_LONGITUD + " REAL NOT NULL," +
                // Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + LugarEntry.COLUMN_POKEMON_ID + ") REFERENCES " +
                PokemonEntry.TABLE_NAME + " (" + PokemonEntry._ID + "));";

        sqLiteDatabase.execSQL(SQL_CREATE_POKEMON_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_LUGAR_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PokemonEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LugarEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
