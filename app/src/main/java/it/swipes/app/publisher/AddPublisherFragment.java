package it.swipes.app.publisher;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.swipes.app.MainActivity;
import it.swipes.app.R;
import it.swipes.app.account.viewmodel.DataModel;
import it.swipes.app.databinding.FragmentAddPublisherBinding;
import it.swipes.app.service.SwipesApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPublisherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPublisherFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST_CODE = 121;

    private FragmentAddPublisherBinding binding;
    private DataModel mData;

    private String mParam1;
    private String mParam2;

    public AddPublisherFragment() {
    }

    public static AddPublisherFragment newInstance(String param1, String param2) {
        AddPublisherFragment fragment = new AddPublisherFragment();
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

        mData = new ViewModelProvider(getActivity()).get(DataModel.class);

    }

    SwipesApi api;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SwipesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ActivityResultLauncher<String> mPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddPublisherBinding.inflate(inflater);

        api = retrofit.create(SwipesApi.class);

        binding.loadPublicPicture.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }



        });




//        retrofit.create()

        binding.sendNewPublisher.setOnClickListener(view -> {

        });

        binding.backFromNewPublisher.setOnClickListener(view -> {

            MainActivity activity = (MainActivity) view.getContext();
            NavController navController = Navigation.findNavController(activity, R.id.app_placeholder);
            navController.navigate(R.id.back_from_add_publisher);

        });


        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }
}