package com.s4ulo.whatsappclone.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.s4ulo.whatsappclone.R;

public class ConfiguracaoActivity extends AppCompatActivity {
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.s4ulo.whatsappclone.R.layout.activity_configuracao);

        toolbar = (Toolbar) findViewById(R.id.toobar);
        toolbar.setTitle("Configuracoes");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }
}