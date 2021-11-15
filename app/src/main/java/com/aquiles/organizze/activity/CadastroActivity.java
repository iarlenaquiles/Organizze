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

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNome = campoNome.getText().toString();
                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();

                if (textNome.isEmpty() || textEmail.isEmpty() || textSenha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                } else {
                    usuario = new Usuario(textNome, textEmail, textSenha);
                    cadastrarUsuario();
                }
            }
        });
    }

    public void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(CadastroActivity.this, "Erro ao cadastrar o usuário", Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}