package com.massrofi.sigservice_posts.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.massrofi.sigservice_posts.data.remote.NetworkResult;
import com.massrofi.sigservice_posts.data.repo.PostRepository;
import com.massrofi.sigservice_posts.model.Post;

import java.util.List;

public class PostViewModel extends ViewModel {

    private final PostRepository repository;

     private final MutableLiveData<NetworkResult<List<Post>>> postsData = new MutableLiveData<>();

     private final MutableLiveData<NetworkResult<Post>> singlePostData = new MutableLiveData<>();

    public PostViewModel() {
         this.repository = new PostRepository();
    }


    public LiveData<NetworkResult<List<Post>>> getPostsData() {
        return postsData;
    }

    public LiveData<NetworkResult<Post>> getSinglePostData() {
        return singlePostData;
    }


    public void loadPosts() {
        repository.fetchPosts(postsData);
    }

    public void loadPostById(int postId) {
        repository.fetchPostById(postId, singlePostData);
    }
}