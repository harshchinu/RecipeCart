package com.example.recipecart.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.recipecart.Adapter.RecyclerViewAdapter;
import com.example.recipecart.Interfaces.addCartInterface;
import com.example.recipecart.Model.recipeModel;
import com.example.recipecart.NetworkCalls.APIClient;
import com.example.recipecart.NetworkCalls.JsonApiCalls;
import com.example.recipecart.R;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements addCartInterface {

    private RecyclerViewAdapter recyclerViewAdapter ;
    List<recipeModel> recipeModelList;
    Set<recipeModel> cartList;
    private RecyclerView recyclerView;
    private Button cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }
    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);


        recipeModelList=new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setItemPrefetchEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        cartList=new HashSet<>();
        getRecipes();
        recyclerViewAdapter = new RecyclerViewAdapter(recipeModelList,getApplicationContext(),this);
        recyclerViewAdapter.setType(1);
        recyclerView.setAdapter(recyclerViewAdapter);
        cart=findViewById(R.id.cartActivity);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,CartActivity.class);
                intent.putExtra("Cart", (Serializable) cartList);
                startActivity(intent);
            }
        });


    }

    private void getRecipes() {

        JsonApiCalls jsonApiCalls = APIClient.getClient().create(JsonApiCalls.class);
        Call<List<recipeModel>> call = jsonApiCalls.getRecipes();
        call.enqueue(new Callback<List<recipeModel>>() {
            @Override
            public void onResponse(Call<List<recipeModel>> call, Response<List<recipeModel>> response) {
                recipeModelList.addAll(response.body());
                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<recipeModel>> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }


    @Override
    public void addtoCartListener(int position) {
        cartList.add(recipeModelList.get(position));
    }
}