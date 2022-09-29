package com.ncode.devstresstask.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ncode.devstresstask.AddLocationActivity;
import com.ncode.devstresstask.MainActivity;
import com.ncode.devstresstask.database.DataBaseHelper;
import com.ncode.devstresstask.databinding.ItemLocationListBinding;
import com.ncode.devstresstask.model.LocationModel;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.HOLDER> {
   private Context context;
   private ArrayList<LocationModel> locationList;
   private DataBaseHelper dataBaseHelper;
    private OnDeleteLocation deleteLocation;

    public LocationAdapter(Context context, ArrayList<LocationModel> locationList) {
        this.context = context;
        this.locationList = locationList;
        dataBaseHelper=new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public HOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new HOLDER(ItemLocationListBinding.inflate(LayoutInflater.from(context),parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HOLDER holder, int position) {
        Log.d("onBindView", "onBindViewHolder:"+locationList.get(position).getLocName());
        holder.binding.txtLocName.setText(locationList.get(position).getLocName());
        holder.binding.txtLocLatlng.setText("lat/lng"+locationList.get(position).getLat()+","+locationList.get(position).getLng());
        Log.d("latitude", "onBindViewHolder:"+(locationList.get(position).getLat()));

        holder.binding.imgDel.setOnClickListener(view -> {
            deleteLocation.delete(locationList.get(position),position);
            locationList.remove(position);
           /* dataBaseHelper.deleteLocation(locationList.get(position).getId());
            notifyItemChanged(position);*/

        });
        holder.binding.imgEdit.setOnClickListener(view ->{
            MainActivity.flag=1;
            context.startActivity(new Intent(context, AddLocationActivity.class).putExtra("l_id",locationList.get(position).getId()));
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class HOLDER extends RecyclerView.ViewHolder {
        ItemLocationListBinding binding;
        public HOLDER(@NonNull ItemLocationListBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }

    public void setOnDeleteLocation(OnDeleteLocation onDeleteLocation)
    {
        this.deleteLocation=onDeleteLocation;

    }
    public  interface OnDeleteLocation
    {
        void delete(LocationModel locationModel,int position);
    }
}
