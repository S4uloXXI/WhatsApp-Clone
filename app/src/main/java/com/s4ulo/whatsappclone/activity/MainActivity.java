package com.s4ulo.whatsappclone.activity;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.s4ulo.whatsappclone.R;
import com.s4ulo.whatsappclone.config.ConfiguracaoFirebase;
import com.s4ulo.whatsappclone.fragment.ContatosFragment;
import com.s4ulo.whatsappclone.fragment.ConversasFragment;
import com.s4ulo.whatsappclone.helper.Base64Custom;
import com.s4ulo.whatsappclone.helper.Preferencias;
import com.s4ulo.whatsappclone.model.Contato;
import com.s4ulo.whatsappclone.model.Usuario;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth usuarioAutenticacao;
    private Toolbar toolbar;
    private Menu menu;
    // private SlidingTabLyuot slidingTabLyuot;
    // private ViewPager viewPager;
    private String identificadorContato;
    private DatabaseReference firebasereferencia;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioAutenticacao  = ConfiguracaoFirebase.getFirebaseAutenticacao();
        toolbar = (Toolbar) findViewById(R.id.toobar);
        setSupportActionBar(toolbar);


        // Configurar abas

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter
                (getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Conversas", ConversasFragment.class)
                .add("Contatos", ContatosFragment.class)
                .create()
        );
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);

       // slidingTabLyuot = (SlidingTabLyuot) findViewById(R.id.sti_tabs);
        //viewPager = (ViewPager) findViewById(R.id.vp_pagina);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.itemPesquisar:

                Toast.makeText(this, "Item Pesquisar!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.contato:
                abrirCadastroContato ();
                return true;
                // Toast.makeText(this, "Item Adicionar!", Toast.LENGTH_SHORT).show();
                //break;

            case R.id.itemConfiguracao:
                abrirCOnfiguracoes();
                return true;
                //Toast.makeText(this, "Item Configuracao!", Toast.LENGTH_SHORT).show();
                //break;

                case R.id.itemSair:
                    deslogarUsuario();
                    return true;
                 //   Toast.makeText(this, "Item Sair!", Toast.LENGTH_SHORT).show();
                //break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void abrirCadastroContato(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        // Configuracao da Dialog
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("E-mail do usuario");
        alertDialog.setCancelable(false);

        // Incluir caixa de texto no Alert Dialog
        EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        // Configurar botao Positivo
        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Resgatar texto digitado
                String emailContato = editText.getText().toString();
                // Validar se foi digitado algum email
                if (emailContato.isEmpty()){
                    Toast.makeText(MainActivity.this, "Digite o email", Toast.LENGTH_SHORT).show();

                }else{
                    // verificar se o E-mail ja esta cadastrado no App
                    identificadorContato = Base64Custom.codificarBase64(emailContato);

                    // Recuperar estancia Firebase
                    firebasereferencia = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato);
                    firebasereferencia.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null){

                                // Recuperar dados do contato adicionado
                                Usuario usuarioContato = snapshot.getValue(Usuario.class);

                                // Recuperar idenficador usuario logado Base64
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdenficador();

                                firebasereferencia = ConfiguracaoFirebase.getFirebase();
                                firebasereferencia = firebasereferencia
                                        .child("contatos")
                                        .child(identificadorUsuarioLogado) // E-mail em Base64
                                        .child(identificadorContato);// E-mail do contado adicionado em Base64

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario(identificadorContato);
                                contato.setEmail(usuarioContato.getEmail());
                                contato.setNome(usuarioContato.getNome());
                                firebasereferencia.setValue(contato);

                            }else{
                                Toast.makeText(MainActivity.this, "Usuario nao possui cadastro", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }});

        // Configurar botao Negativo
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }});
        alertDialog.create();
        alertDialog.show();
    }

    public void abrirCOnfiguracoes(){
        Intent intent = new Intent(MainActivity.this, ConfiguracaoActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario(){
        usuarioAutenticacao.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}