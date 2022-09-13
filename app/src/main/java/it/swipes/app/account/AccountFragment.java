package it.swipes.app.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import it.swipes.app.MainActivity;
import it.swipes.app.R;
import it.swipes.app.account.model.Profile;
import it.swipes.app.account.viewmodel.DataModel;
import it.swipes.app.databinding.FragmentAccountBinding;
import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountFragment extends Fragment {

    FragmentAccountBinding binding;

    private DataModel data;

    public AccountFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new ViewModelProvider(getActivity()).get(DataModel.class);
    }


    private SwipesApi api;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://swipes-dev.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater);

        api = retrofit.create(SwipesApi.class);

        data.getName().observe(getViewLifecycleOwner(), s -> {

            Call<Profile> profile = api.getProfile(s);

            profile.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {

                    Profile body = response.body();
                    binding.accountName.setText(body.getName());

                    Picasso
                            .get()
                            .load(body.getPicture())
                            .into(binding.profileIcon);

                    binding.accountDescription.setText(body.getDescription());
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {

                }
            });
        });

        binding.editProfile.setOnClickListener(view -> {
            MainActivity activity = (MainActivity) view.getContext();
            NavController navController = Navigation.findNavController(activity, R.id.app_placeholder);
            Bundle bundle = new Bundle();

            bundle.putString("id", data.getName().getValue());
            navController.navigate(R.id.to_editProfileFragment, bundle);
        });



        return binding.getRoot();
    }
}