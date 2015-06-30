package com.cojisoft.tfm_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by DavidGSola on 15/06/2015.
 */
public class ResultadoActivity extends FragmentActivity {
    /**
     * El número de páginas a mostrar en la actividad
     */
    private static final int NUM_PAGES = 2;

    /**
     * El ViewPager que se encarga de maejar las animaciones y que permite realizar un slide
     * horizontal para cambiar entre páginas
     */
    private ViewPager mPager;

    /**
     * El PageAdapter, que aporta las páginas que se mostrarán en el ViewPager
     */
    private PagerAdapter mPagerAdapter;

    /**
     * Ruta absoluta a la imagen base que se mostrará en la primera página
     */
    private String pathImagenBase = "";

    /**
     * Ruta absoluta a la imagen del modelo que se mostrará en la segunda página
     */
    private String pathImagenModelo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Bundle extras = getIntent().getExtras();
        pathImagenBase = extras.getString("pathBase");
        pathImagenModelo = extras.getString("pathModelo");

        // Instancia el ViewPager y el PagerAdapater
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * PagerAdapter que representa 2 {@link ScreenSlidePageFragment} objetos en secuencia
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String path = position == 0 ? pathImagenBase : pathImagenModelo;
            return ScreenSlidePageFragment.create(position, path);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }
}
