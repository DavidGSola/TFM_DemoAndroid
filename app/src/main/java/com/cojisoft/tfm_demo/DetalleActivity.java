package com.cojisoft.tfm_demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * Created by DavidGSola on 15/06/2015.
 */
public class DetalleActivity extends ActionBarActivity implements View.OnClickListener {

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

        if (figura.equals("CITARA")) {
            actionBar.setTitle(getString(R.string.citara_titulo));
            imagen.setImageResource(R.drawable.portada_citara);
            titulo.setText(getString(R.string.citara_titulo));
            lugar.setText(getString(R.string.citara_lugar));
            descripcion.setText(getString(R.string.citara_descripcion));
        } else if (figura.equals("ESTANISLAO")) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Se lanza una hebra asíncrona para enviar la imagen al servidor
        new RetrieveFeedTask().execute();
    }

    class RetrieveFeedTask extends AsyncTask<String, String, Integer> {
        protected Integer doInBackground(String... urls) {
            return UploadFile();
        }
    }

    /**
     * Envía una imagen al servidor donde se procesará
     * @return 0 -> todo OK
     *         -1 -> Error
     */
    public int UploadFile(){
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        DataInputStream inputStream = null;

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(2);
        Log.v("CACA", randomInt + "");
        String nombreFichero = randomInt == 0 ? "cita1_negra.bmp" : "cita2_negra.bmp";
        String pathArchivo = Environment.getExternalStorageDirectory().toString()+"/DCIM/" + nombreFichero;
        String urlServer = "http://192.168.1.108:8080/TFM_Servidor/Lanzador";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        try
        {
            FileInputStream fileInputStream = new FileInputStream(new File(pathArchivo) );

            URL url = new URL(urlServer);
            connection = (HttpURLConnection) url.openConnection();

            // Allow Inputs &amp; Outputs.
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            // Set HTTP method to POST.
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            outputStream = new DataOutputStream( connection.getOutputStream() );
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathArchivo +"\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Leer fichero
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Respuesta del servidor (código y mensaje)
            int serverResponseCode = connection.getResponseCode();
            String serverResponseMessage = connection.getResponseMessage();

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception ex)
        {
            Log.e("Err", ex.toString());
            return -1;
        }

        // Se lanza la actividad detalle
        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra("pathBase", pathArchivo);
        startActivity(intent);

        return 0;
    }
}
