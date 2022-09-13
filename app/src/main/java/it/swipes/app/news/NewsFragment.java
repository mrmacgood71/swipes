package it.swipes.app.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.swipes.app.R;
import it.swipes.app.databinding.FragmentCreateBinding;
import it.swipes.app.databinding.FragmentNewsBinding;
import it.swipes.app.news.model.FullPost;
import it.swipes.app.news.model.PhotoUrl;
import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<FullPost> posts;
    private List<String> publisher;


    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    private SwipesApi api;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SwipesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getSupportFragmentManager().beginTransaction().commit();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater);

        posts = new ArrayList<>();

        api = retrofit.create(SwipesApi.class);

        binding.refresh.setOnRefreshListener(() -> {
            Call<List<FullPost>> posts = api.getPosts();

            posts.enqueue(new Callback<List<FullPost>>() {
                @Override
                public void onResponse(Call<List<FullPost>> call, Response<List<FullPost>> response) {
                    List<FullPost> body = response.body();

                    if (body == null) {
                        return;
                    }
                    List<List<PhotoUrl>> images = new ArrayList<>();

                    body.forEach(fullPost -> images.add(fullPost.getImage()));

                    recyclerView = binding.postsList;
                    postAdapter = new PostAdapter(getContext(), body, images);
                    recyclerView.setAdapter(postAdapter);

                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);

                    postAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<FullPost>> call, Throwable t) {

                }
            });
        });

        Call<List<FullPost>> posts = api.getPosts();

        posts.enqueue(new Callback<List<FullPost>>() {
            @Override
            public void onResponse(Call<List<FullPost>> call, Response<List<FullPost>> response) {
                List<FullPost> body = response.body();

                if (body == null) {
                    return;
                }
                List<List<PhotoUrl>> images = new ArrayList<>();

                body.forEach(fullPost -> images.add(fullPost.getImage()));

                recyclerView = binding.postsList;
                postAdapter = new PostAdapter(getContext(), body, images);
                recyclerView.setAdapter(postAdapter);

                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(linearLayoutManager);


                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<FullPost>> call, Throwable t) {

            }
        });



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}