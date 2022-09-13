package it.swipes.app.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

import it.swipes.app.MainActivity;
import it.swipes.app.R;
import it.swipes.app.account.model.Profile;
import it.swipes.app.account.viewmodel.DataModel;
import it.swipes.app.databinding.FragmentEditProfileBinding;
import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditProfileFragment extends Fragment {

    FragmentEditProfileBinding binding;
    private DataModel data;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        data = new ViewModelProvider(getActivity()).get(DataModel.class);
    }

    private SwipesApi api;

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl("https://swipes-dev.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        api = mRetrofit.create(SwipesApi.class);

        data.getName().observe(getViewLifecycleOwner(), s -> {
            Call<Profile> profile = api.getProfile(s);

            profile.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    Profile body = response.body();

                    binding.name.setText(body.getName());
                    binding.email.setText(body.getEmail());
                    binding.profileDescription.setText(body.getDescription());
                    Picasso.get().load(body.getPicture()).into(binding.profileIconEdit);

                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {

                }
            });


            binding.change.setOnClickListener(view -> {

                data.getPhoto().observe(getViewLifecycleOwner(), p -> {
                    Profile editProfile = new Profile(
                            s,
                            binding.profileDescription.getText().toString(),
                            binding.name.getText().toString(),
                            p,
                            binding.email.getText().toString(),
                            "ru",
                            "Select",
                            null
                    );

                    Call<Profile> call = api.createProfile(editProfile);

                    call.enqueue(new Callback<Profile>() {
                        @Override
                        public void onResponse(Call<Profile> call, Response<Profile> response) {

                        }

                        @Override
                        public void onFailure(Call<Profile> call, Throwable t) {

                        }
                    });

                });

                MainActivity activity = (MainActivity) view.getContext();
                NavController navController = Navigation.findNavController(activity, R.id.app_placeholder);
                navController.navigate(R.id.back_to_accountFragment);

            });


        });

        binding = FragmentEditProfileBinding.inflate(inflater);


        return binding.getRoot();
    }
}