package com.example.gsb_medecine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Authentification extends AppCompatActivity {
    private EditText CodeVisiteur, myKey;
    private Button btnValiderCodeVisiteur, btnValiderCle;
    LinearLayout layoutCle;
    String myRandomKey;

    private static final String PREF_NAME = "UserPrefs";

    private static final String KEY_USER_STATUS = "userStatus";
    private static final String SECURETOKEN = "EuroForma3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        CodeVisiteur = findViewById(R.id.code_visiteur);
        myKey = findViewById(R.id.my_key);
        layoutCle = findViewById(R.id.layoutCle);
        layoutCle.setVisibility(View.INVISIBLE);
        setUserStatus("authentification=KO");

    }

    public void AfficheLayout(View v){
        myRandomKey = genererChaineAleatoire(5);
        Log.d("APPLI", "myKey="+myRandomKey);

        String codeVisiteur = CodeVisiteur.getText().toString();
        String secureKey = myRandomKey;
        String token = SECURETOKEN;
        SendKeyTask sendKeyTask = new SendKeyTask(getApplicationContext());
        sendKeyTask.execute(codeVisiteur, secureKey, token);
        layoutCle.setVisibility(View.VISIBLE);

    }

    private String genererChaineAleatoire(int longueur) {
        String caracteresPermis = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder chaineAleatoire = new StringBuilder();

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < longueur; i++) {
            int index = random.nextInt(caracteresPermis.length());
            char caractereAleatoire = caracteresPermis.charAt(index);
            chaineAleatoire.append(caractereAleatoire);
        }

        return chaineAleatoire.toString();
    }

    public void comparerChaineCaractere(View v){
        String cleSecrete = myKey.getText().toString().trim();
        if (cleSecrete.equals(myRandomKey)) {
            showToast("La clé correspond à la chaine aléatoire ");
            setUserStatus("authentification=OK");
            Intent authIntent = new Intent(this, MainActivity.class);
            startActivity(authIntent);
            finish(); // Terminez l'activité principale pour éviter qu'elle ne soit accessible avec le bouton "Retour"

        } else {
            showToast("La clé et la chaine aléatoire ne sont pas égales.");
            setUserStatus("authentification=KO");
        }
    }
    private void showToast(String montexte){

        Toast.makeText(this, montexte, Toast.LENGTH_LONG).show();

    }

    private void setUserStatus(String status) {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(currentTime);
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_STATUS, status);
        editor.putString("TIME",strDate);
        editor.apply();
    }
}