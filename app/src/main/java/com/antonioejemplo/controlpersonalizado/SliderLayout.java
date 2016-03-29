package com.antonioejemplo.controlpersonalizado;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Susana on 15/03/2016.
 */
public class SliderLayout extends LinearLayout {
    private TextView tvText;
    private Button btPrev, btNext;
    // Almacenará los textos que mostraremos en nuestro control personalizado.
    private CharSequence[] entries;
    private int index = 0;

    public SliderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        init();
        //Recogemos los atributos definidos en el xml y los cargamos mediante un for
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SliderAtributes);

        //Cargamos los atributos
        for (int i = 0; i < a.getIndexCount(); i++) {
            switch (a.getIndex(i)) {
                case R.styleable.SliderAtributes_android_entries:
                    break;
                case R.styleable.SliderAtributes_onClickPrevButton:
                    break;
                case R.styleable.SliderAtributes_onClickNextButton:
                    break;
            }
        }

//1. Recogemos la colección para el atributo entries
        entries = a.getTextArray(R.styleable.SliderAtributes_android_entries);
// Si se han obtenido datos (se han definido en xml), establecemos el texto inicial
// en el TextView.
        if(entries.length > 0) {
            tvText.setText(entries[0]);
        }


//2. Recogemos el método SliderAtributes_onClickPrevButton
// Comprobamos que el context no este restringido, de estarlo no podríamos usar
// nuestro método, por lo que lanzamos la exepción.
        if (context.isRestricted()) {
            throw new IllegalStateException();
        }
// Recogemos el nombre de nuestro método.
        final String handlerNamePrevButton = a
                .getString(R.styleable.SliderAtributes_onClickPrevButton);
// Si se ha definido un método, configuramos el evento onClick de nuestro botón.
        if (handlerNamePrevButton != null) {
            btPrev.setOnClickListener(new OnClickListener() {
                // Manejador para llamar al método en la Activity.
                private Method mHandler;

                @Override
                public void onClick(View v) {
                    if (mHandler == null) {
                        try {
                            // Tratamos de llamar al método con el nombre que hemos definido.
                            // A partir del contexto recogemos la clase, y posteriormente
                            // recogemos la referencia al método que tiene el nombre que
                            // definimos en xml.
                            mHandler = getContext().getClass()
                                    .getMethod(handlerNamePrevButton,
                                            View.class);
                        } catch (NoSuchMethodException e) {
                            // Si el método no existe, lanzamos excepción.
                            throw new IllegalStateException();
                        }
                    }

                    try {
                        // Intentamos ejecutar el método.
                        mHandler.invoke(getContext(), SliderLayout.this);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException();
                    } catch (InvocationTargetException e) {
                        throw new IllegalStateException();
                    }
                }
            });
        }

//3. Recogemos el método SliderAtributes_onClickNextButton
        if (context.isRestricted()) {
            throw new IllegalStateException();
        }

        final String handlerNameNextButton = a
                .getString(R.styleable.SliderAtributes_onClickNextButton);
        if (handlerNameNextButton != null) {
            btNext.setOnClickListener(new OnClickListener() {
                private Method mHandler;

                @Override
                public void onClick(View v) {
                    if (mHandler == null) {
                        try {
                            mHandler = getContext().getClass()
                                    .getMethod(handlerNameNextButton,
                                            View.class);
                        } catch (NoSuchMethodException e) {
                            throw new IllegalStateException();
                        }
                    }

                    try {
                        mHandler.invoke(getContext(), SliderLayout.this);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException();
                    } catch (InvocationTargetException e) {
                        throw new IllegalStateException();
                    }
                }
            });
        }



    }

    private void init() {

        // En primer lugar inflamos la vista de nuestro control personalizado. Al método iniflate
        // le pasamos el layout de nuestro control, el ViewGroup al que pertenecerá la vista (this)
        // y si se debe añadir a este ViewGroup (en este caso sí).
        ((LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.sliderlayout, this, true);

        // Inicializamos nuestros controles.
        tvText = (TextView) findViewById(R.id.tvSliderText_SLIDER_CUSTOMER_DETAILS);
        btPrev = (Button) findViewById(R.id.btPREV_SLIDER_CUSTOMER_DETAILS);
        btNext = (Button) findViewById(R.id.btNext_SLIDER_CUSTOMER_DETAILS);



    }


    public void showNextText() {
        index++;
        if (index >= entries.length) {
            index = 0;
        }

        tvText.setText(entries[index]);
    }

    public void showPrevText() {
        index--;
        if (index < 0) {
            index = entries.length - 1;
        }

        tvText.setText(entries[index]);
    }
}
