package com.massrofi.sigservice_posts.ui.list;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.massrofi.sigservice_posts.databinding.ItemPostBinding;
import com.massrofi.sigservice_posts.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList = new ArrayList<>();
    private final OnPostClickListener listener;

    public interface OnPostClickListener {
        void onPostClick(Post post);
    }

    public PostAdapter(OnPostClickListener listener) {
        this.listener = listener;
    }

    public void setPosts(List<Post> posts) {
        this.postList = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostBinding binding = ItemPostBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);


        holder.binding.getRoot().setCardBackgroundColor(Color.WHITE);
        holder.binding.getRoot().setUseCompatPadding(true);


        String originalTitle = post.getTitle();
        if (originalTitle != null && !originalTitle.isEmpty()) {
            StringBuilder capitalizedTitle = new StringBuilder();
            String[] wordsArray = originalTitle.split("\\s+");
            for (String word : wordsArray) {
                if (word.length() > 0) {
                    capitalizedTitle.append(Character.toUpperCase(word.charAt(0)))
                            .append(word.substring(1).toLowerCase())
                            .append(" ");
                }
            }
            holder.binding.tvTitle.setText(capitalizedTitle.toString().trim());
        }

        holder.binding.chipId.setText("Post #" + post.getId());

        int words = post.getWordCount();
        int readTime = Math.max(1, words / 200);
        holder.binding.chipCounter.setText(readTime + " min read");

        // 3. Dynamic Styling (Avatar + Counter)
        int[] bgColors = {0xFFE3F2FD, 0xFFF3E5F5, 0xFFFFF3E0};
        int[] iconColors = {0xFF1976D2, 0xFF7B1FA2, 0xFFF57C00};
        int colorIndex = position % 3;

        // Apply to Avatar
        holder.binding.avatarCard.setCardBackgroundColor(bgColors[colorIndex]);


        holder.binding.chipCounter.setChipBackgroundColor(
                android.content.res.ColorStateList.valueOf(bgColors[colorIndex])
        );
        holder.binding.chipCounter.setTextColor(iconColors[colorIndex]);

        holder.itemView.setOnClickListener(v -> listener.onPostClick(post));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        final ItemPostBinding binding;

        PostViewHolder(ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
