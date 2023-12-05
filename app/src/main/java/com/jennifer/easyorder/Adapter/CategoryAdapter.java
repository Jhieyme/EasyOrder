package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.Fragments.ProductFragment;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.databinding.ItemCategoryBinding;
import com.jennifer.easyorder.model.Category;
import com.jennifer.easyorder.viewmodel.CategoryViewModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ShowViewHolder> {
    private List<Category> categoryList;

    private FragmentManager fragmentManager;


    private CategoryViewModel categoryViewModel;

    public CategoryAdapter(List<Category> categoryList, FragmentManager fragmentManager, CategoryViewModel categoryViewModel) {
        this.categoryList = categoryList;
        this.fragmentManager = fragmentManager;
        this.categoryViewModel = categoryViewModel;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        holder.bind(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;

        public ShowViewHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Category category) {
            binding.txtDescription.setText(category.getDescripcion());
            Glide.with(itemView.getContext()).load(category.getUrlImagen()).into(binding.imgCategory);


            binding.imgCategory.setOnClickListener(v -> {

                categoryViewModel.setCategoryObject(category);
                ProductFragment productFragment = new ProductFragment();
                openFragment(productFragment);

            });
        }

        private void openFragment(Fragment fragment) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fcv_main, fragment);
            fragmentTransaction.commit();
        }
    }
}
