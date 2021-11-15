package com.aquiles.organizze.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aquiles.organizze.R;
import com.aquiles.organizze.config.ConfiguracaoFirebase;
import com.aquiles.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private Button btnEntrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        campoEmail = findViewById(R.id.editEmailLogin);
        campoSenha = findViewById(R.id.editSenhaLogin);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();

                if (textEmail.isEmpty() || textSenha.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                } else {
                    usuario = new Usuario();
                    usuario.setEmail(textEmail);
                    usuario.setSenha(textSenha);
                    validarLogin();
                }
            }
        });
    }

    public void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "sucesso ao fazer login", Toast.LENGTH_LONG).show();

                                } else {
                                    String excecao = "";
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        excecao = "E-mail e senha nao correspondem";
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        excecao = "Usuário nao está cadastrado";
                                    } catch (Exception e) {
                                        excecao = "Erro ao cadastrar usuário " + e.getMessage();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_LONG).show();

                                }
                            }
                        }
                );
    }
}