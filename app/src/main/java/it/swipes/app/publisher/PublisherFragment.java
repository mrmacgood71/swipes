package it.swipes.app.publisher;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import it.swipes.app.MainActivity;
import it.swipes.app.R;
import it.swipes.app.databinding.FragmentPublisherBinding;
import it.swipes.app.publisher.model.Publisher;
import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublisherFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PublisherFragment() {
        // Required empty public constructor
    }

    public static PublisherFragment newInstance(String param1, String param2) {
        PublisherFragment fragment = new PublisherFragment();
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
    }

    FragmentPublisherBinding binding;

    private SwipesApi api;

    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl("https://swipes-dev.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPublisherBinding.inflate(inflater);
        Bundle arguments = getArguments();

        binding.appBarTitle.setText(arguments.getString("publisherName"));

        api = mRetrofit.create(SwipesApi.class);

        Call<Publisher> publisher = api.getPublisher(arguments.getString("publisherId"));

        publisher.enqueue(new Callback<Publisher>() {
            @Override
            public void onResponse(Call<Publisher> call, Response<Publisher> response) {
                Publisher body = response.body();

                Picasso
                        .get()
                        .load(body.getProfilePic())
                        .into(binding.circleImageView);


            }

            @Override
            public void onFailure(Call<Publisher> call, Throwable t) {

            }
        });

        binding.singlePublisherName.setText(arguments.getString("publisherName"));
        binding.singlePublisherDescription.setText(arguments.getString("publisherDescription"));

        binding.back.setOnClickListener(view -> {
            MainActivity activity = (MainActivity) getContext();
            NavController navController = Navigation.findNavController(activity, R.id.app_placeholder);
            Bundle bundle = new Bundle();

            navController.navigate(R.id.back_to_publisher, bundle);
        });


        return binding.getRoot();
    }
}