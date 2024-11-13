package br.gustavoIgnacio.easypetvet;

/*
@author: <Gustavo da Silva Ignacio 1110482313006>
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;

     @ Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets)->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.barra)));
        }

        // Carrega o fragmento baseado nos dados do Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            carregaFragment(bundle);
        } else {
            FragmentManager FragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = FragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new FragmentInicial());
            fragmentTransaction.commit();
        }
    }

    private void carregaFragment(Bundle bundle) {
        String tipoItem = bundle.getString("tipoItem");
        if (tipoItem != null) {
            if (tipoItem.equals("Animais")) {
                fragment = new FragmentAnimais();
            } else if (tipoItem.equals("Consultas")) {
                fragment = new FragmentConsultas();
            } else if (tipoItem.equals("Voltar")) {
                fragment = new FragmentInicial();
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }
    }

     @ Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

     @ Override
    public boolean onOptionsItemSelected( @ NonNull MenuItem item) {
        int id = item.getItemId();

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);

        if (id == R.id.menu_animais) {
            bundle.putString("tipoItem", "Animais");

            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        if (id == R.id.menu_consultas) {
            bundle.putString("tipoItem", "Consultas");

            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        if (id == R.id.menu_voltar) {
            bundle.putString("tipoItem", "Voltar");

            intent.putExtras(bundle);
            this.startActivity(intent);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
