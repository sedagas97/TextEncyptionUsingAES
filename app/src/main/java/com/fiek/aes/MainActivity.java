package com.fiek.aes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button   btnDecrypt;
    Button   btnEncrypt;
    EditText encryptText;
    String   decryptedText;
    String   encryptedText;
    TextView decryptTextView;
    TextView encryptTextView;
    View     layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeItems();
        btnEncrypt.setOnClickListener(
                view -> {
                    encryptedText = encrypt("AlanTuring", encryptText.getText().toString());
                    encryptTextView.setText(encryptedText);
                });
        btnDecrypt.setOnClickListener(
                view -> {
                    decryptedText = decrypt("AlanTuring", encryptedText);
                    decryptTextView.setText(decryptedText);
                });

    }

    private void initializeItems() {
        btnEncrypt = findViewById(R.id.encryptButton);
        btnDecrypt = findViewById(R.id.decryptButton);
        encryptText = findViewById(R.id.encryptText);
        encryptTextView = findViewById(R.id.encryptedTextView);
        decryptTextView = findViewById(R.id.decryptedTextView);
        layout = findViewById(R.id.mainLayout);

    }

    protected String encrypt(String SEED, String password) {
        return CypherUtils.encryptText(SEED, password, layout);
    }

    protected String decrypt(String SEED, String encryptedPassword) {
        return CypherUtils.decryptText(SEED, encryptedPassword, layout);
    }
}