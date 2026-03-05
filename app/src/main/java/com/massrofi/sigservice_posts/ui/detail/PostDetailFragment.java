package com.massrofi.sigservice_posts.ui.detail;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
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

    // Inside PostDetailFragment.java
    public static PostDetailFragment newInstance(int postId, String postTitle) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POST_ID, postId);
        args.putString(ARG_POST_TITLE, postTitle);
        fragment.setArguments(args);
        return fragment;
    }

    private static final String ARG_POST_ID = "post_id";
    private static final String ARG_POST_TITLE = "post_title";

    // Font scale constants
    private static final float FONT_STEP = 2.0f;
    private static final float MIN_FONT_SIZE = 12.0f;
    private static final float MAX_FONT_SIZE = 32.0f;

    private int mPostId;
    private String mPostTitle;
    private float mCurrentFontSize = 16.0f; // Default size in sp

    private FragmentPostDetailBinding binding;
    private PostViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPostId = getArguments().getInt(ARG_POST_ID);
            mPostTitle = getArguments().getString(ARG_POST_TITLE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // This part is crucial: it actually creates the UI on the screen
        binding = FragmentPostDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvDetailTitle.setText(mPostTitle);
        viewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);

        // 1. Observe Data
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

        // 2. Trigger Network Call
        viewModel.loadPostById(mPostId);

        // 3. Setup Font Controls
        setupFontControls();

        // 4. Back Button Logic
        binding.btnBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void setupFontControls() {
        // Increase Font Size
        binding.btnIncreaseFont.setOnClickListener(v -> {
            if (mCurrentFontSize < MAX_FONT_SIZE) {
                mCurrentFontSize += FONT_STEP;
                updateTextSize();
            }
        });

        // Decrease Font Size
        binding.btnDecreaseFont.setOnClickListener(v -> {
            if (mCurrentFontSize > MIN_FONT_SIZE) {
                mCurrentFontSize -= FONT_STEP;
                updateTextSize();
            }
        });
    }

    private void updateTextSize() {
        // setTextSize(unit, size) -> TypedValue.COMPLEX_UNIT_SP is preferred
        binding.tvDetailBody.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCurrentFontSize);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}