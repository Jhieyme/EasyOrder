package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.ItemWorkerBinding;
import com.jennifer.easyorder.model.Gender;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.viewmodel.MethodPayViewModel;

import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.ShowViewHolder> {
    private List<Worker> workersList;

    private MethodPayViewModel methodPayViewModel;

    public WorkerAdapter(List<Worker> workersList, MethodPayViewModel methodPayViewModel) {
        this.workersList = workersList;
        this.methodPayViewModel = methodPayViewModel;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWorkerBinding binding = ItemWorkerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        holder.bind(workersList.get(position));
    }

    @Override
    public int getItemCount() {
        return workersList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private ItemWorkerBinding binding;

        public ShowViewHolder(@NonNull ItemWorkerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Worker worker) {
            binding.txtName.setText(worker.getNombres());
            binding.txtLastname.setText(worker.getApellidos());

            //Imagen por genero
            Gender gender = worker.getIdGeneroNavigation();
            if (gender.getIdGenero() == 1) {
                Glide.with(itemView.getContext()).load("https://cdn-icons-png.flaticon.com/512/236/236831.png").into(binding.imgWorker);
            } else {
                Glide.with(itemView.getContext()).load("https://cdn-icons-png.flaticon.com/512/6997/6997662.png").into(binding.imgWorker);
            }

            binding.radioButton.setOnClickListener(v -> {
                methodPayViewModel.setWorker(worker);
            });
        }
    }
}