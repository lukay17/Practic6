package com.lega.practica_6.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lega.practica_6.R;
import com.lega.practica_6.databinding.FragmentLoginBinding;
import com.lega.practica_6.databinding.FragmentRegisterBinding;
import com.lega.practica_6.repository.User;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private int contador =0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.RAImage.setImageResource(R.drawable.imagen);

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragment = getFragmentManager().beginTransaction();

                fragment.replace(R.id.P6_container, new LoginFragment(), "LoginFragment").commitAllowingStateLoss();
            }
        });

        binding.RAImage.setImageResource(R.drawable.imagen);
        binding.RAImageCamara.setImageResource(R.drawable.ic_camara_foreground);

        dropdownBinding();

        listener();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void listener() {
        binding.RANameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (binding.RANameText.getText().toString() != null && binding.RANameText.getText().toString().length() > 0){
                    if(binding.RANameText.getText().toString().contains("@") || binding.RANameText.getText().toString().contains("!")) {
                        binding.RANameText.setError("Ups, no creo que sea correcto, resviselo");
                        contador = 0;
                    }
                    contador +=1 ;
                    if(contador > 1){
                        binding.RABtnAction.setEnabled(true);
                    }
                }else{
                    binding.RABtnAction.setEnabled(false);
                }
            }
        });

        binding.RASurnameText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(binding.RASurnameText.getText().toString() != null && binding.RASurnameText.getText().toString().length() > 0) {
                    if(binding.RASurnameText.getText().toString().contains("@") || binding.RASurnameText.getText().toString().contains("!")) {
                        binding.RASurnameText.setError("Ups, no creo que sea correcto, resviselo");
                        contador = 0;
                    }
                    contador +=1 ;
                    if(contador > 1){
                        binding.RABtnAction.setEnabled(true);
                    }else{
                        binding.RABtnAction.setEnabled(false);
                    }
                }
            }
        });

        binding.RAAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String optionSelected = "";
                if (String.valueOf(adapterView.getItemAtPosition(i)).equals("0-5")) {
                    Log.e("lega", "1");
                    binding.RADrop.setError("Esto no es para ti");
                }else  if (String.valueOf(adapterView.getItemAtPosition(i)).equals("6-11")) {
                    Log.e("lega", "2");
                    binding.RADrop.setError("Esto no es para ti");
                }else if (String.valueOf(adapterView.getItemAtPosition(i)).equals("12-17")) {
                    Log.e("lega", "3");
                    binding.RADrop.setError("Esto no es para ti");
                }else{
                    binding.RADrop.setError(null);
                }
            }
        });

        binding.RABtnAction.setOnClickListener(view -> {
            if(binding.RANameText.getText().length() > 0 && binding.RANameText.getText() != null && binding.RASurnameText.getText().length() > 0 && binding.RASurnameText.getText() != null){
                Toast.makeText(getContext(), binding.RANameText.getText().toString(), Toast.LENGTH_SHORT).show();

                User user = new User();
                user.setUser(binding.RANameText.getText().toString());
                user.setSurName(binding.RASurnameText.getText().toString());

                FragmentTransaction fragment = getFragmentManager().beginTransaction();
                fragment.replace(R.id.P6_container,  LoginFragment.newInstance(user),"LoginFragment").commitAllowingStateLoss();
            }else{
                Toast.makeText(getContext(), "Debes Llenar todos los datos solicitados.", Toast.LENGTH_SHORT).show();
            }
        });


        binding.RATxtCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWeb("https://developers.google.com/ml-kit/terms");
            }
        });

        binding.RAImageCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePhoto();
            }
        });
    }

    private void dropdownBinding() {
        String []  listFruits = getResources().getStringArray(R.array.ListOption);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listFruits);
        binding.RAAutocomplete.setAdapter(adapter);
    }

    public void openWeb(String url){
        Uri web= Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,web);
        startActivity(intent);
    }

    public void capturePhoto() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivityForResult(intent, 12);
    }
}