package it.swipes.app.news.deprecate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.swipes.app.databinding.FragmentRecommendedNewsBinding;

public class RecommendedNewsFragment extends Fragment {

    private FragmentRecommendedNewsBinding binding;

    public RecommendedNewsFragment() {

    }

    public static RecommendedNewsFragment newInstance() {
        RecommendedNewsFragment fragment = new RecommendedNewsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRecommendedNewsBinding.inflate(inflater);
        binding.comment.setOnClickListener(view -> {
        });

        binding.download.setOnClickListener(view -> {
        });
        Log.d("RecommendedNewsFragment", "onCreateView: ");
        return binding.getRoot();
    }
}