package com.example.recipecart.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;


import com.example.recipecart.Interfaces.addCartInterface;
import com.example.recipecart.Model.recipeModel;
import com.example.recipecart.R;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<recipeModel> itemList;
    private static Context context;
    private addCartInterface addCartInterface;
    private int type;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView,price;
        public ImageView imageView;
        public Button addtoCart;
        public MyViewHolder(View v, com.example.recipecart.Interfaces.addCartInterface addCartInterface,int type) {
            super(v);
            textView = v.findViewById(R.id.itemName);
            imageView = v.findViewById(R.id.imageView);
            price=v.findViewById(R.id.itemprice);
            addtoCart = v.findViewById(R.id.addtoCart);
            if(type==1) {
                addtoCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCartInterface.addtoCartListener(getAdapterPosition());
                        Toast.makeText(context,"Added to Cart",Toast.LENGTH_LONG).show();
                    }
                });
            }else {
                addtoCart.setText("Remove from cart");
                addtoCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addCartInterface.addtoCartListener(getAdapterPosition());
                        Toast.makeText(context,"Remove from Cart",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

    }

    public RecyclerViewAdapter(List myDataset,Context c,addCartInterface addCartInterface) {
        itemList = myDataset;
        context=c;
        this.addCartInterface=addCartInterface;
    }
    public RecyclerViewAdapter(List myDataset,Context c) {
        itemList = myDataset;
        context=c;
    }

    public  void setType(int type){
        this.type=type;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewsampleview, parent, false);
        return new MyViewHolder(itemView,addCartInterface,type);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
            holder.textView.setText(itemList.get(position).getName());
            holder.price.setText("$"+itemList.get(position).getPrice()+" /-");
            Picasso.get().load(itemList.get(position).getImage()).placeholder(circularProgressDrawable).into(holder.imageView);


    }



    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
