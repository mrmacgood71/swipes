package it.swipes.app.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import it.swipes.app.LoginActivity;
import it.swipes.app.databinding.FragmentPreferencesBinding;
import it.swipes.app.databinding.FragmentRecommendedNewsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferencesFragment extends Fragment {

    FragmentPreferencesBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PreferencesFragment() {
        // Required empty public constructor
    }


    public static PreferencesFragment newInstance(String param1, String param2) {
        PreferencesFragment fragment = new PreferencesFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPreferencesBinding.inflate(inflater);

        binding.signOut.setOnClickListener(view -> {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(container.getContext());
            GoogleSignInOptions gso = new GoogleSignInOptions
                    .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            GoogleSignInClient client = GoogleSignIn.getClient(container.getContext(), gso);

            client.signOut().addOnCompleteListener(task -> {
                if (task.isSuccessful()) startActivity(new Intent(container.getContext(), LoginActivity.class));
            });

        });

        return binding.getRoot();
    }
}