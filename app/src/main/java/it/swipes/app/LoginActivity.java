package it.swipes.app;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.time.LocalDateTime;

import it.swipes.app.account.model.Profile;
import it.swipes.app.account.viewmodel.DataModel;
import it.swipes.app.databinding.ActivityLoginBinding;
import it.swipes.app.account.viewmodel.DataModel;
import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final String ACCOUNT_TOKEN = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private DataModel data;
    private ActivityLoginBinding binding;
    private GoogleSignInAccount mAccount;

    private SwipesApi api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://swipes-dev.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(SwipesApi.class);

        mAccount = GoogleSignIn.getLastSignedInAccount(this);

        data = new ViewModelProvider(this).get(DataModel.class);

        if (mAccount != null) {
            Intent mainActivity = new Intent(this, MainActivity.class);

            if (mAccount.getPhotoUrl().getPath() != null) {
                Log.d("onCreate: ", mAccount.getPhotoUrl().getPath());
            }


            Profile profile = new Profile(
                    mAccount.getId(),
                    "",
                    mAccount.getDisplayName(),
                    mAccount.getPhotoUrl().toString(),
                    mAccount.getEmail(),
                    "ru",
                    "Select",
                    null
            );

            Call<Profile> call = api.createProfile(profile);

            call.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {

                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mainActivity.putExtra("id", mAccount.getId());
            data.getId().setValue(mAccount.getId());
            data.getName().setValue(mAccount.getDisplayName());
            data.getPhoto().setValue(mAccount.getPhotoUrl().toString());

            mainActivity.putExtra("photo", mAccount.getPhotoUrl().toString());


            startActivity(mainActivity);
        } else {
            binding.getStarted.setOnClickListener(view -> {
                GoogleSignInOptions gso = new GoogleSignInOptions
                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build();

                GoogleSignInClient client = GoogleSignIn.getClient(this, gso);

                mAccount = GoogleSignIn.getLastSignedInAccount(this);
                Intent signInIntent = client.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent mainActivity = new Intent(this, MainActivity.class);

        mAccount = GoogleSignIn.getLastSignedInAccount(this);

        mainActivity.putExtra("id", mAccount.getId());
        mainActivity.putExtra("photo", mAccount.getPhotoUrl().toString());

        startActivity(mainActivity);

    }
}