package com.cojisoft.tfm_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DavidGSola on 15/06/2015.
 */
public class DetalleActivity extends ActionBarActivity implements View.OnClickListener{

    ImageView imagen;

    TextView titulo;
    TextView lugar;
    TextView descripcion;

    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        ActionBar actionBar = (ActionBar) getSupportActionBar();

        Bundle extras = getIntent().getExtras();
        String figura = extras.getString("figura");

        boton = (Button) findViewById(R.id.detalle_boton);
        boton.setOnClickListener(this);

        imagen = (ImageView) findViewById(R.id.detalle_imagen);
        titulo = (TextView) findViewById(R.id.detalle_titulo);
        lugar = (TextView) findViewById(R.id.detalle_lugar);
        descripcion = (TextView) findViewById(R.id.detalle_descripcion);

        if(figura.equals("CITARA"))
        {
            actionBar.setTitle(getString(R.string.citara_titulo));
            imagen.setImageResource(R.drawable.portada_citara);
            titulo.setText(getString(R.string.citara_titulo));
            lugar.setText(getString(R.string.citara_lugar));
            descripcion.setText(getString(R.string.citara_descripcion));
        }else if(figura.equals("ESTANISLAO"))
        {
            actionBar.setTitle(getString(R.string.estanislao_titulo));

            imagen.setImageResource(R.drawable.portada_estanislao);
            titulo.setText(getString(R.string.estanislao_titulo));
            lugar.setText(getString(R.string.estanislao_lugar));
            descripcion.setText(getString(R.string.estanislao_descripcion));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this, "HEY", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ResultadoActivity.class);
        startActivity(intent);
    }
}
