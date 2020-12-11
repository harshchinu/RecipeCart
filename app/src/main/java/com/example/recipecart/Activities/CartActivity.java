package com.example.recipecart.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.recipecart.Adapter.RecyclerViewAdapter;
import com.example.recipecart.Interfaces.addCartInterface;
import com.example.recipecart.Model.recipeModel;
import com.example.recipecart.NetworkCalls.APIClient;
import com.example.recipecart.NetworkCalls.JsonApiCalls;
import com.example.recipecart.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements addCartInterface {

    private RecyclerViewAdapter recyclerViewAdapter ;
    List<recipeModel> recipeModelList;
    private RecyclerView recyclerView;
    private Button totalvalue;
    double total=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();


    }
    private void initView() {
        totalvalue=findViewById(R.id.cartValue);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recipeModelList=new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mLayoutManager.setItemPrefetchEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        getRecipes();
        recyclerViewAdapter = new RecyclerViewAdapter(recipeModelList,getApplicationContext(),this);
        recyclerViewAdapter.setType(2);
        recyclerView.setAdapter(recyclerViewAdapter);


    }

    private void getRecipes() {

       recipeModelList.addAll((Collection<? extends recipeModel>) getIntent().getSerializableExtra("Cart"));
       for(recipeModel recipeModel:recipeModelList){
           total+= Double.parseDouble(recipeModel.getPrice());
       }
       totalvalue.setText("Total Cart Value : $"+total);
    }

    @Override
    public void addtoCartListener(int position) {
        total-=Double.parseDouble(recipeModelList.get(position).getPrice());
        recipeModelList.remove(position);
        recyclerViewAdapter.notifyDataSetChanged();
        totalvalue.setText("Total Cart Value : $"+String.format("%.2f",total));
    }
}