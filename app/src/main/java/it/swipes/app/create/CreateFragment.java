package it.swipes.app.create;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import it.swipes.app.EditActivity;
import it.swipes.app.databinding.FragmentCreateBinding;
import it.swipes.app.news.model.FullPost;
import it.swipes.app.service.SwipesApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateFragment extends Fragment {

    FragmentCreateBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateBinding.inflate(inflater);


        api = retrofit.create(SwipesApi.class);

        Call<List<FullPost>> posts = api.getPosts();

        posts.enqueue(new Callback<List<FullPost>>() {
            @Override
            public void onResponse(Call<List<FullPost>> call, Response<List<FullPost>> response) {
                List<FullPost> body = response.body();

                if (body == null) {
                    Toast.makeText(getContext(), "Pizda", Toast.LENGTH_SHORT).show();
                    binding.text.setText("Pizda");
                    return;
                }

                body.forEach(fullPost -> {
                    binding.text.setText(
                            binding.text.getText().toString() + "\n" +
                                    fullPost.getImage().get(1).getUrl() + "\n" +
                            fullPost.getId() + "\n" +
                                    fullPost.getAuthor() + "\n" +
                            fullPost.getCommentsCount() + "\n\n\n");
                });

            }

            @Override
            public void onFailure(Call<List<FullPost>> call, Throwable t) {

            }
        });


        Intent intent = new Intent(getContext(), EditActivity.class);


        getActivity().startActivityFromFragment(this, intent, 121);



        return binding.getRoot();
    }
}