package it.swipes.app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;

import it.swipes.app.account.viewmodel.DataModel;
import it.swipes.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private DataModel data;
    private ActivityMainBinding binding;
    private AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        data = new ViewModelProvider(this).get(DataModel.class);

        if (extras.size() > 0) {
            String id = extras.getString("id");
            String photo = extras.getString("photo");
            data.getName().setValue(id);
        }


        MobileAds.initialize(this, initializationStatus -> {});

        adView = binding.adView;
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);



        BottomNavigationView bottomBar = binding.bottomNavigationBar;

        NavController navController = Navigation.findNavController(this, R.id.app_placeholder);
        NavigationUI.setupWithNavController(bottomBar, navController);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}