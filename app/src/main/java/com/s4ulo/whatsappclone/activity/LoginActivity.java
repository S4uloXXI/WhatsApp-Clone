package com.s4ulo.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.s4ulo.whatsappclone.R;
import com.s4ulo.whatsappclone.helper.Base64Custom;
import com.s4ulo.whatsappclone.helper.Preferencias;
import com.s4ulo.whatsappclone.model.Usuario;
import com.s4ulo.whatsappclone.config.ConfiguracaoFirebase;

public class LoginActivity extends AppCompatActivity {

    private EditText textEmail;
    private EditText textPassword;
    private Button btLogar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);
        btLogar = (Button) findViewById(R.id.btLogar);

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new Usuario();
                usuario.setEmail(textEmail.getText().toString());
                usuario.setSenha(textPassword.getText().toString());
                validarLogin();
            }
        });
    }

        private void verificarUsuarioLogado (){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }


    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());
                    preferencias.salvarDados( identificadorUsuarioLogado );

                    abrirTelaPrincipal();

                    Toast.makeText(LoginActivity.this, "Login realizado com sucesso!", Toast.LENGTH_LONG).show();

                }else {

                    Toast.makeText(LoginActivity.this, "Login nao realizado! Verifique se o E-mail ou a Senha estao corretos.", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    // Link para outra activity pelo onClick "abrirCadastroUsuario"
   public void abrirCadastroUsuario (View view){
       Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
       startActivity(intent);}
}