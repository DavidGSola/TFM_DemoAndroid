package com.cojisoft.tfm_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    enum Figura {CITARA, ESTANISLAO};

    CardView cardCitara;
    CardView cardEstanislao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardCitara = (CardView)this.findViewById(R.id.portada_citara_card);
        cardEstanislao = (CardView)this.findViewById(R.id.portada_estanislao_card);

        cardCitara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityDetalle(Figura.CITARA);
            }
        });

        cardEstanislao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivityDetalle(Figura.ESTANISLAO);
            }
        });
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

    private void abrirActivityDetalle(Figura figura)
    {
        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra("figura", figura.toString());

        startActivity(intent);
    }
}
