package com.fergutierrez.retrofit_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.fergutierrez.retrofit_android.api.PostApi;
import com.fergutierrez.retrofit_android.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView tvTextAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTextAPI = findViewById(R.id.tvTextAPI);
        getPosts();
    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://jsonplaceholder.typicode.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
        // Llamar a la interface
        PostApi postApi = retrofit.create(PostApi.class);

        Call<List<Post>> call = postApi.getPosts();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()) {
                    tvTextAPI.setText("Código: " + response.code());
                    return;
                }
                System.out.println("ACÁ ES: " + response.body());
                List<Post> postList = response.body();

                for(Post posts: postList){
                    String content = "";
                    content += "userId:" + posts.getUserId() + "\n";
                    content += "id:" + posts.getId() + "\n";
                    content += "title:" + posts.getTitle() + "\n";
                    content += "body:" + posts.getBody() + "\n\n";
                    tvTextAPI.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                tvTextAPI.setText(t.getMessage());
            }
        });
    }
}