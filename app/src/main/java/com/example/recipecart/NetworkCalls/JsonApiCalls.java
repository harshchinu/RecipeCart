package com.example.recipecart.NetworkCalls;

import com.example.recipecart.Model.recipeModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonApiCalls {

    @GET("reciped9d7b8c.json")
    Call<List<recipeModel>> getRecipes();
}
