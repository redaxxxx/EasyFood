package com.example.easyfood.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easyfood.data.pojo.CategoryList.Category;
import com.example.easyfood.databinding.CategoryListItemBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<Category> mCategoryList = new ArrayList<>();

    public OnItemClickListener onItemClickListener;

    public CategoriesAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setCategoryList(List<Category> categoryList){
        mCategoryList = categoryList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesViewHolder(CategoryListItemBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(mCategoryList.get(position).getStrCategoryThumb())
                .into(holder.categoryListItemBinding.imgCategory);

        holder.categoryListItemBinding.categoryNameTv.setText(mCategoryList.get(position).getStrCategory());

        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClickListener(mCategoryList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder{

        CategoryListItemBinding categoryListItemBinding;
        public CategoriesViewHolder(CategoryListItemBinding binding) {
            super(binding.getRoot());
            categoryListItemBinding = binding;
        }
    }

    public interface OnItemClickListener{
        void onItemClickListener(Category category);
    }
}
