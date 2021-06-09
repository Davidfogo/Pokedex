package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    public static Activity act;
    public static TextView txtDisplay;
    public static ImageView imgPok;
    public static SharedPreferences sharedpreferences;
    public boolean algo = false;
    public static ImageView [] imgType;

    public int MAX_POKEMONS = 898;
    public int pokemonCount;

    public fetchDataType processType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getApplicationContext().getSharedPreferences("infopokemon", Context.MODE_PRIVATE);
        act = this;
        imgType = new ImageView[2];

        txtDisplay = findViewById(R.id.txtDisplay);
        imgPok = findViewById(R.id.imgPok);
        imgType[0] = findViewById(R.id.imgType0);
        imgType[1] = findViewById(R.id.imgType1);

        pokemonCount = 1;

        fetchData pokemon = new fetchData(String.valueOf(pokemonCount));
        pokemon.execute();

       ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                algo = false;
                showTxtSearch();
            }
        });

        ImageButton btnTypes = findViewById(R.id.btnTypes);
        btnTypes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Buscartipopokemon();
            }
        });

        Button btnRight = findViewById(R.id.btnRight);
        btnRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!algo) {
                    if ((pokemonCount + 1) > MAX_POKEMONS) {
                        pokemonCount = 1;
                    }else{
                        pokemonCount++;
                    }

                    fetchData process = new fetchData(String.valueOf(pokemonCount));
                    process.execute();
                }

               /* ArrayList<String> pokeNames = processType.getStrPokemons().size();
                fetchData process = new fetchData(pokeNames.get(contador));*/
            }
        });

        Button btnup = findViewById(R.id.btnUp);
        btnup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> pokeNames = processType.getStrPokemons().size();

                if (!algo) {
                    if (pokemonCount <= 0) {
                        pokemonCount = 0;
                    }else{
                        pokemonCount--;
                    }
                }
                fetchDataType process = new fetchDataType( pokeNames[pokemonCount]);
                process.execute();
            }
        });

        Button btndown = findViewById(R.id.btnDown);
        btndown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> pokeNames = processType.getStrPokemons().size();


                if (!algo) {
                    if (pokemonCount > pokeNames) {
                        pokemonCount = 0;
                    }else{
                        pokemonCount++;
                    }
                }
                fetchDataType process = new fetchDataType(pokeNames[pokemonCount]);
                process.execute();
            }
        });

        Button btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!algo) {
                    if (pokemonCount <= 1) {
                        pokemonCount = MAX_POKEMONS;
                    }else{
                        pokemonCount--;
                    }

                    fetchData process = new fetchData(String.valueOf(pokemonCount));
                    process.execute();
                }
            }
        });
    }

    public void showTxtSearch(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Search a Pokemon");

        final EditText input = new EditText(this);
        input.setHint("Pokemon / ID");
        input.setText("pikachu");
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String pokSearch = input.getText().toString().toLowerCase();
                if(!pokSearch.isEmpty()){
                    fetchData process = new fetchData(pokSearch);
                    process.execute();
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

    public void Buscartipopokemon() {
        String[] tiposPokemon = {"Normal ", "Bicho", "Agua", "Electrico", "Hada", "Lucha", "Fuego", "Volador", "Fantasma", "Planta",
                "Tierra", "Hielo", "Siniestro", "Veneno", "Psiquico", "Roca", "Acero", "Dragon"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,  android.R.layout.simple_spinner_item, tiposPokemon);
        Spinner spinner = new Spinner(MainActivity.this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        spinner.setAdapter(arrayAdapter);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Tipos");



        alert.setView(spinner);
        ;
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                processType = new fetchDataType(spinner.getSelectedItem().toString());
                processType.execute();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
        alert.show();
    }

}
