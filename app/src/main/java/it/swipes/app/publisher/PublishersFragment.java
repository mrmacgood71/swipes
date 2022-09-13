package it.swipes.app.publisher;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.swipes.app.AddPublicActivity;
import it.swipes.app.MainActivity;
import it.swipes.app.R;
import it.swipes.app.databinding.FragmentPublishersBinding;
import it.swipes.app.publisher.model.Publisher;

import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublishersFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PublishersFragment() {

    }

    public static PublishersFragment newInstance(String param1, String param2) {
        PublishersFragment fragment = new PublishersFragment();
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

    private SwipesApi api;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SwipesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    FragmentPublishersBinding binding;
    List<Publisher> body;
    PublishersRecycleViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPublishersBinding.inflate(inflater);

        api = retrofit.create(SwipesApi.class);

        Call<List<Publisher>> publishers = api.getPublishers();

        publishers.enqueue(new Callback<List<Publisher>>() {
            @Override
            public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {

                body = response.body();

                RecyclerView recyclerView = binding.publishersList;
                adapter = new PublishersRecycleViewAdapter(getContext(), body);

                recyclerView.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<Publisher>> call, Throwable t) {

            }
        });

        binding.search.clearFocus();
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);

                return true;
            }
        });

        binding.addPublisher.setOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), AddPublicActivity.class);
            getActivity().startActivityFromFragment(this, intent, 200);
//
//            MainActivity activity = (MainActivity) view.getContext();
//            NavController navController = Navigation.findNavController(activity, R.id.app_placeholder);
//            navController.navigate(R.id.addPublisherFragment);
        });
        
        return binding.getRoot();
    }

    private void filter(String text) {
        List<Publisher> filteredList = new ArrayList<>();

        if (body != null) {
            for (Publisher item : body) {

                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }

                if (!filteredList.isEmpty()) {
                    adapter.setFilteredList(filteredList);
                }
            }
        } else {
            Call<List<Publisher>> publishers = api.getPublishers();

            publishers.enqueue(new Callback<List<Publisher>>() {
                @Override
                public void onResponse(Call<List<Publisher>> call, Response<List<Publisher>> response) {

                    List<Publisher> pubs = response.body();

                    RecyclerView recyclerView = binding.publishersList;
                    adapter = new PublishersRecycleViewAdapter(getContext(), pubs);

                    recyclerView.setAdapter(adapter);

                    for (Publisher item : pubs) {

                        if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                            filteredList.add(item);
                        }

                        if (!filteredList.isEmpty()) {
                            adapter.setFilteredList(filteredList);
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<Publisher>> call, Throwable t) {

                }
            });


        }


    }
}