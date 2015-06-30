package com.cojisoft.tfm_demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Clase que hereda de Fragment para representar una imagen en un  ScreenSlidePage. Cada fragmento
 * de eeste tipo se representa por un número de página y una ruta absoluta a la imagen que
 * debe mostrar
 */
public class ScreenSlidePageFragment extends Fragment {
    /**
     * La clave para el número de página de este fragmento
     */
    public static final String ARG_PAGE = "page";

    /**
     * La clave para la ruta absoluta de la imagen de este fragmento
     */
    public static final String ARG_PATH = "path";

    /**
     * Número del fragmento, se setea al argumento dado por {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Ruta absoluta de la imagen del fragmento, se setea al argumento dado por {@link #ARG_PATH}.
     */
    private String mPathFile;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    /**
     * Método factoría para esta clase fragmento. Construye un nuevo fragmento para el número de página dada y el pathFile
     * @param pageNumber Número de página del fragmento
     * @param pathFile Ruta de la imagen a mostrar
     * @return Fragmento creado
     */
    public static ScreenSlidePageFragment create(int pageNumber, String pathFile) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(ARG_PATH, pathFile);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mPathFile = getArguments().getString(ARG_PATH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        ImageView imagenView = (ImageView) rootView.findViewById(R.id.resultado_imagen);
        Bitmap bitmapOriginal = BitmapFactory.decodeFile(mPathFile);

        // Escalamos la imagen
        Bitmap bitmapEscalado = escalarBitmap(bitmapOriginal);

        imagenView.setImageBitmap(bitmapEscalado);
        imagenView.setScaleType(ImageView.ScaleType.FIT_XY);

        return rootView;
    }

    /**
     * Devuelve un bitmap escalado al tamaño de la pantalla
     * @param bm Bitmap a escalar
     * @return Bitmap escalado al tamaño de la pantalla
     */
    private Bitmap escalarBitmap(Bitmap bm)
    {
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int scaledHeight = (width*bm.getHeight())/bm.getWidth();
        return Bitmap.createScaledBitmap(bm,width, scaledHeight, true);
    }

    /**
     * Devuelve el número de página que representa este Fragment
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
