package com.example.pokedex;


import android.os.AsyncTask;
import android.util.Log;

import com.ahmadrosid.svgloader.SvgLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class fetchDataType extends AsyncTask<Void, Void, Void> {

    protected String data = "";
    protected String results = "";
    protected ArrayList<String> strPokemons;
    protected String TipoPokemon;
    protected String idpokemon;


    public fetchDataType(String TipoPokemon) {
        this.TipoPokemon = TipoPokemon.toLowerCase();
        strPokemons = new ArrayList<String>();
    }

    public ArrayList<String> getStrPokemons() {
        return strPokemons;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Make API connection
            URL url = new URL("https://pokeapi.co/api/v2/type/" + TipoPokemon);
            Log.i("logtest", "https://pokeapi.co/api/v2/type/" + TipoPokemon);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Read API results
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sBuilder = new StringBuilder();

            // Build JSON String
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }

            inputStream.close();
            data = sBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid){
        JSONObject jObject = null;
        String img = "";
        String typeName = "";
        String typeObj="";

        try {
            jObject = new JSONObject(data);

            JSONArray pokemons = new JSONArray(jObject.getString("pokemon"));
            for (int i = 0; i < pokemons.length(); i++) {
                JSONObject pokemon = new JSONObject(pokemons.getString(i));
                JSONObject poke = new JSONObject(pokemon.getString("pokemon"));

                String name = poke.getString("name");
                strPokemons.add(name);
            }

            fetchData pokemon = new fetchData(strPokemons.get(0));
            pokemon.execute();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /*
    try {
        jObject = new JSONObject(data);

        // Get JSON name, height, weight
        results += "Name: " + jObject.getString("name").toUpperCase() + "\n" +
                    "Height: " + jObject.getString("height") + "\n" +
                    "Weight: " + jObject.getString("weight");

        // Get img SVG
        JSONObject sprites = new JSONObject(jObject.getString("sprites"));
        JSONObject other = new JSONObject(sprites.getString("other"));
        JSONObject dream_world = new JSONObject(other.getString("dream_world"));
        img  = dream_world.getString("front_default");

        // Get type/types
        JSONArray types = new JSONArray(jObject.getString("types"));
        for(int i=0; i<types.length(); i++){
            JSONObject type = new JSONObject(types.getString(i));
            JSONObject type2  = new JSONObject(type.getString("type"));
            strTypes.add(type2.getString("name"));
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
*/

}
