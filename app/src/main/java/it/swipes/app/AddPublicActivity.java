package it.swipes.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

import it.swipes.app.account.model.Profile;
import it.swipes.app.databinding.ActivityAddPublicBinding;
import it.swipes.app.publisher.model.Publisher;
import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPublicActivity extends AppCompatActivity {

    ActivityAddPublicBinding binding;

    String str = "" + Math.random();

    private SwipesApi api;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SwipesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPublicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        api = retrofit.create(SwipesApi.class);


        binding.loadPublicPicture.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/**");
            startActivityForResult(intent, 100);
        });

        binding.sendNewPublisher.setOnClickListener(view -> {
            Publisher publisher = new Publisher(
                    binding.editTextPersonName.getText().toString(),
                    binding.editTextDescription.getText().toString(),
                    str
            );
            Call<Profile> newPublisher = api.createPublisher(publisher);

            newPublisher.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {

                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {

                }
            });

            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("id", "115105241828159907784");
            intent.putExtra("photo", "https://lh3.googleusercontent.com/a-/AOh14GgTrb04ca8CwETwya3lTr7PSdVE67X6j4NMgwtL");
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            binding.newPublisherLogo.setImageURI(uri);

            Call<String> call = api.sendImage(new File(uri.getPath()), str);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }
}