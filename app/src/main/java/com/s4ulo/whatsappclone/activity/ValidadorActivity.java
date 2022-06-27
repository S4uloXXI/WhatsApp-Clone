package com.s4ulo.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.s4ulo.whatsappclone.R;
import com.s4ulo.whatsappclone.helper.Preferencias;

import java.util.HashMap;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button validar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validadorctivity);

        codigoValidacao         = (EditText) findViewById(R.id.codigoValidacao);
        validar                 = (Button) findViewById(R.id.validar);

        /* Definir mascara */
        SimpleMaskFormatter simpleMaskPais = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher maskValidacao = new MaskTextWatcher(codigoValidacao, simpleMaskPais);
        codigoValidacao.addTextChangedListener(maskValidacao);

        //SimpleMaskFormatter simpleMaskCOdigoValidacao = new SimpleMaskFormatter ("NNNN");
        //MaskTextFormatter mascaraCodigoValidacao = new MaskTextWhatcher (codigoValidacao, simpleMaskCOdigoValidacao);

        //codigoValidacao.addTextChangedListener(codigoValidacao);
        validar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                 //Recupera dados das preferencias do usuario
               /* Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getClass();


                String tokenGerado = usuario.get("token");
                String tokenDigitado = codigoValidacao.getText().toString();

                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "Token VALIDADO", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "Token nao VALIDADO", Toast.LENGTH_SHORT).show();
                }
                */
            }
        });
    }
}