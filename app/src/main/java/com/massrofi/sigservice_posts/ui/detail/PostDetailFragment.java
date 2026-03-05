package com.massrofi.sigservice_posts.ui.detail;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.massrofi.sigservice_posts.R;
import com.massrofi.sigservice_posts.databinding.FragmentPostDetailBinding;
import com.massrofi.sigservice_posts.ui.PostViewModel;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PostDetailFragment extends Fragment {

    // Keys for the Bundle
    private static final String ARG_POST_ID = "post_id";
    private static final String ARG_POST_TITLE = "post_title";

    private int mPostId;
    private String mPostTitle;

    private FragmentPostDetailBinding binding;
    private PostViewModel viewModel;

    public PostDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Updated Factory Method to pass the ID and Title
     */
    public static PostDetailFragment newInstance(int postId, String postTitle) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POST_ID, postId);
        args.putString(ARG_POST_TITLE, postTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = getArguments().getInt(ARG_POST_ID);
            mPostTitle = getArguments().getString(ARG_POST_TITLE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Use ViewBinding for cleaner UI access
        binding = FragmentPostDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Set the title immediately (passed from the list)
        binding.tvDetailTitle.setText(mPostTitle);

        // 2. Initialize ViewModel (Shared with Activity)
        viewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);

        // 3. Observe the specific post data from the API
        viewModel.getSinglePostData().observe(getViewLifecycleOwner(), result -> {
            if (result == null) return;

            switch (result.status) {
                case LOADING:
                    binding.progressBar.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    binding.progressBar.setVisibility(View.GONE);
                    if (result.data != null) {
                        binding.tvDetailBody.setText(result.data.getBody());
                    }
                    break;
                case ERROR:
                    binding.progressBar.setVisibility(View.GONE);
                    binding.tvDetailBody.setText("Error loading content: " + result.message);
                    break;
            }
        });

        // 4. Trigger the network call for this specific post
        viewModel.loadPostById(mPostId);

        // 5. Back Button Logic
        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}