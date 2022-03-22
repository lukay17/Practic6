package com.lega.practica_6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.lega.practica_6.databinding.ActivityHomeBinding;
import com.lega.practica_6.fragment.HomeFragment;
import com.lega.practica_6.fragment.LoginFragment;
import com.lega.practica_6.repository.User;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private String welcome = "";
    private String name = "";
    private String surname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.HAToolbar);

        binding.HAToolbar.setNavigationIcon(R.drawable.home);

        if(getIntent().getExtras() != null){
            name = getIntent().getExtras().getString("name", "No hay Nombre");
            surname = getIntent().getExtras().getString("surname", "No hay Apellido");
            Log.e("lega",name);
            Log.e("lega",surname);
            Snackbar.make(binding.HAToolbar, welcome, Snackbar.LENGTH_SHORT).show();
        }

        initHomeFragment();
    }

    private void initHomeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("FirstFragment");

        User user = new User();
        user.setUser(name.toString());
        user.setSurName(surname.toString());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.HAContainer.getId(),
                        fragment !=null?fragment:HomeFragment.newInstance(user),
                        "HomeFragment")
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}