package com.jennifer.easyorder.Adapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.ItemCategoryBinding;
import com.jennifer.easyorder.model.Category;

import java.util.List;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ShowViewHolder> {

    private List<Category> categoryList;
    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
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
            /*if (category.getDescripcion() != null && !category.getDescripcion().isEmpty()) {
                Bitmap image = generateImage(category.getDescripcion());
                binding.imgCategory.setImageBitmap(image);
            }*/
        }

    }

    // Generar imagen con inicial
    private Bitmap generateImage(String text) {
        int width = 100;
        int height = 100;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawText(text.substring(0, 1), 40, 60, paint);
        return bitmap;
    }

}
