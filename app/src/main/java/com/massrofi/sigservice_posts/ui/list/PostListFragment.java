package com.massrofi.sigservice_posts.ui.list;

import static com.massrofi.sigservice_posts.data.remote.NetworkResult.Status.ERROR;
import static com.massrofi.sigservice_posts.data.remote.NetworkResult.Status.SUCCESS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.massrofi.sigservice_posts.R;
import com.massrofi.sigservice_posts.data.remote.NetworkResult;
import com.massrofi.sigservice_posts.databinding.FragmentPostListBinding;
import com.massrofi.sigservice_posts.model.Post;
import com.massrofi.sigservice_posts.ui.PostViewModel;
import com.massrofi.sigservice_posts.ui.detail.PostDetailFragment;
import com.massrofi.sigservice_posts.ui.main.MainActivity;


public class PostListFragment extends Fragment {
    private FragmentPostListBinding binding;
    private PostViewModel viewModel;
    private PostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // STEP 1: Inflate the layout
        binding = FragmentPostListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         setupRecyclerView();

         viewModel = new ViewModelProvider(requireActivity()).get(PostViewModel.class);

         viewModel.getPostsData().observe(getViewLifecycleOwner(), result -> {
             if (binding == null) return;

            binding.swipeRefresh.setRefreshing(result.status == NetworkResult.Status.LOADING);

            switch (result.status) {
                case SUCCESS:
                    binding.layoutError.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE); // Ensure list is visible
                    adapter.setPosts(result.data);
                    binding.tvCount.setText(result.data.size() + " articles available");
                    break;
                case ERROR:
                    binding.layoutError.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE); // Hide list on error
                    Snackbar.make(binding.getRoot(), "Network error", Snackbar.LENGTH_LONG).show();
                    break;
            }
        });

        // Click listeners
        binding.btnRetry.setOnClickListener(v -> viewModel.loadPosts());
        binding.swipeRefresh.setOnRefreshListener(() -> viewModel.loadPosts());
        binding.btnLogout.setOnClickListener(v -> showLogoutConfirmation());

         if (viewModel.getPostsData().getValue() == null) {
            viewModel.loadPosts();
        }
    }

    private void showLogoutConfirmation() {
        new com.google.android.material.dialog.MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to exit ?")
                .setPositiveButton("Logout", (dialog, which) -> {
                     if (getActivity() instanceof MainActivity) {
                        ((MainActivity) requireActivity()).performLogout();
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void setupRecyclerView() {
        adapter = new PostAdapter(this::navigateToDetail);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setHasFixedSize(true);
    }

    private void navigateToDetail(Post post) {
         if (post == null) return;

         PostDetailFragment detailFragment = PostDetailFragment.newInstance(
                post.getId(),
                post.getTitle()
        );

         if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).loadFragment(detailFragment, true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Prevent memory leaks
    }
}