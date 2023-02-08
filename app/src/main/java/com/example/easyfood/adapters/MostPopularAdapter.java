package com.example.easyfood.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyfood.data.pojo.MealsByCategoryList.MealsByCategory;
import com.example.easyfood.databinding.PopularListItemBinding;

import java.util.ArrayList;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularAdapter.PopularViewHolder>{

    public OnItemClickListener onItemClickListener;

    private ArrayList<MealsByCategory> mMealsList = new ArrayList<>();


    public MostPopularAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;

    }
    public void setMeals(ArrayList<MealsByCategory> mealsList){
        this.mMealsList = mealsList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularViewHolder(PopularListItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mMealsList.get(position).getStrMealThumb())
                .into(holder.mPopularBinding.imgPopularMealItem);


        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemListener(mMealsList.get(position));
        });

        holder.itemView.setOnLongClickListener(view -> {
            onItemClickListener.onLongItemListener(mMealsList.get(position));
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mMealsList.size();
    }

    class PopularViewHolder extends RecyclerView.ViewHolder{
        PopularListItemBinding mPopularBinding;
        public PopularViewHolder(@NonNull PopularListItemBinding binding) {
            super(binding.getRoot());
            mPopularBinding = binding;
        }
    }

    public interface OnItemClickListener{
        void onItemListener(MealsByCategory meal);
        void onLongItemListener(MealsByCategory meal);
    }
}
