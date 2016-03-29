package com.antonioejemplo.controlpersonalizado;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/*http://www.proyectosimio.com/es/programacion-android-creacion-de-un-control-personalizado/*/


public class MainActivity extends AppCompatActivity {

    private SliderLayout slider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slider=(SliderLayout)findViewById(R.id.slider);

    }

    public void onClickPrevButton(View v) {
        slider.showPrevText();
    }

    public void onClickNextButton(View v) {
        slider.showNextText();
    }
}
