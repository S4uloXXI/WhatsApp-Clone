package com.s4ulo.whatsappclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crashlytics.internal.model.CrashlyticsReport;
import com.s4ulo.whatsappclone.R;
import com.s4ulo.whatsappclone.helper.Base64Custom;
import com.s4ulo.whatsappclone.helper.Preferencias;
import com.s4ulo.whatsappclone.model.Usuario;
import com.s4ulo.whatsappclone.config.ConfiguracaoFirebase;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText textCadastroNome;
    private EditText textCadastroEmail;
    private EditText textCadastroPassword;
    private Button btCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.s4ulo.whatsappclone.R.layout.activity_cadastro_usuario);
        textCadastroNome = (EditText) findViewById(R.id.textCadastroNome);
        textCadastroEmail = (EditText) findViewById(R.id.textCadastroEmail);
        textCadastroPassword = (EditText) findViewById(R.id.textCadastroPassword);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                usuario = new Usuario();
                usuario.setNome(textCadastroNome.getText().toString());
                usuario.setEmail(textCadastroEmail.getText().toString());
                usuario.setSenha(textCadastroPassword.getText().toString());
                cadastrarUsuario();
            }

        });
    }

    private void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()

        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadastrar usuario", Toast.LENGTH_LONG).show();
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();

                    Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                    preferencias.salvarDados(identificadorUsuario);
                    abrirLoginUsuario();
                }else {
                    String erroExcecao = "";


                    /* Faz o usuario se deslogar apos criar conta, para que ele tenha que logar novamente com os dados criados */
                    //autenticacao.signOut();

                    /* Faz o usuario voltar a tela de login se para que ele logar novamente com os dados criados */
                    //Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class); startActivity(intent);finish();
                try {
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao =
                                "Escolha uma senha com 8 digitos que contenha pelo menos 1 letra maiscula e um numero";
                        //e.printStackTrace();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao =
                                "O email digitado e invalido, digite um novo email";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao =
                                "Este e-mail ja esta em uso no App";
                    } catch (Exception e) {
                        erroExcecao =
                                "Erro ao cadastrar usuario! verifique os dados digitados!";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroUsuarioActivity.this,
                            "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

            public void abrirLoginUsuario (){
            Intent intent = new Intent(CadastroUsuarioActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
    }
}



