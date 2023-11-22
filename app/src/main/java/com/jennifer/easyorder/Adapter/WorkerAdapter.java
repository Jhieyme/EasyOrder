package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.Fragments.WorkerFragment;
import com.jennifer.easyorder.databinding.ItemWorkerBinding;
import com.jennifer.easyorder.model.Gender;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.utils.ShowAlertCustom;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;

import java.util.Arrays;
import java.util.List;

public class    WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.ShowViewHolder> {
    private List<Worker> workersList;

    private PaymentViewModel paymentViewModel;

    private WorkerFragment workerFragment;
    private ShowAlertCustom alertCustom = new ShowAlertCustom();

    public WorkerAdapter(List<Worker> workersList, PaymentViewModel paymentViewModel, WorkerFragment workerFragment) {
        this.workersList = workersList;
        this.paymentViewModel = paymentViewModel;
        this.workerFragment = workerFragment;

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

            binding.imgWorker.setOnClickListener(v -> {
                String nombreUPPER = worker.getNombres();
                List<String> nombresList = Arrays.asList(nombreUPPER.split("\\s+"));
                StringBuilder result = new StringBuilder();

                for (String nombre : nombresList) {
                    result.append(Character.toUpperCase(nombre.charAt(0)));
                    result.append(nombre.substring(1).toLowerCase());
                    result.append(" ");
                }
                String strCustom = result.toString();
                alertCustom.showCustomAlert(binding.getRoot().getContext(), "Seleccionaste al personal", strCustom, worker, paymentViewModel, workerFragment);
            });


        }
    }
}