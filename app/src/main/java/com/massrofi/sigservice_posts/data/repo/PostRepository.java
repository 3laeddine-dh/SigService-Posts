package com.massrofi.sigservice_posts.data.repo;

import androidx.lifecycle.MutableLiveData;

import com.massrofi.sigservice_posts.data.remote.ApiClient;
import com.massrofi.sigservice_posts.data.remote.ApiService;
import com.massrofi.sigservice_posts.data.remote.NetworkResult;
import com.massrofi.sigservice_posts.model.Post;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {

    private final ApiService apiService;

    public PostRepository() {
        this.apiService = ApiClient.getApiService();
    }

    //  Fetch All Posts 
    public void fetchPosts(MutableLiveData<NetworkResult<List<Post>>> liveData) {
        liveData.postValue(NetworkResult.loading());

        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        liveData.postValue(NetworkResult.success(response.body()));
                    } else {
                        liveData.postValue(NetworkResult.error("Server Error: " + response.code()));
                    }
                } catch (Exception e) { 
                    liveData.postValue(NetworkResult.error("Data Parsing Error: " + e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                liveData.postValue(NetworkResult.error("Network Failure: " + t.getLocalizedMessage()));
            }
        });
    }

    //  Fetch Post by ID
    public void fetchPostById(int postId, MutableLiveData<NetworkResult<Post>> liveData) {
        liveData.postValue(NetworkResult.loading());
 
        apiService.getPostById(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        liveData.postValue(NetworkResult.success(response.body()));
                    } else {
                        liveData.postValue(NetworkResult.error("Post not found: " + response.code()));
                    }
                } catch (Exception e) {
                    liveData.postValue(NetworkResult.error("Error: " + e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                liveData.postValue(NetworkResult.error("Connection Failed: " + t.getMessage()));
            }
        });
    }
}
