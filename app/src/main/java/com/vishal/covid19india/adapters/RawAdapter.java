package com.vishal.covid19india.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.covid19.rawData.Raw_data;
import com.vishal.covid19india.view.activity.RawDataPatientDetailsActivity;
import java.util.ArrayList;

public class RawAdapter extends RecyclerView.Adapter<RawAdapter.ViewHolder> {

  private ArrayList<Raw_data> raw_dataArrayList;
  private Context context;

  public RawAdapter(Context context,
      ArrayList<Raw_data> raw_dataArrayList) {
    this.context = context;
    this.raw_dataArrayList = raw_dataArrayList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_item_raw_data, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    if (raw_dataArrayList != null) {
      raw_dataArrayList.get(position).setGenderImageUrl();
      if (raw_dataArrayList.get(position).getGenderImageUrl() != 0) {
        Glide.with(context).load(raw_dataArrayList.get(position).getGenderImageUrl())
            .into(holder.ivPatient);
      } else {
        Glide.with(context).load(raw_dataArrayList.get(position).getUnKnownImageUrl())
            .into(holder.ivPatient);
      }
      holder.tvPatientNumber
          .setText("P ".concat(raw_dataArrayList.get(position).getPatientnumber()));
      holder.tvPatientCurrentStage
          .setText(raw_dataArrayList.get(position).getCurrentstatus());
      holder.tvPatientAge
          .setText(raw_dataArrayList.get(position).getAgebracket());
      holder.tvPatientCity
          .setText(raw_dataArrayList.get(position).getDetectedcity().equals("-") ? raw_dataArrayList
              .get(position).getDetecteddistrict()
              : raw_dataArrayList.get(position).getDetectedcity());
      holder.itemView.setOnClickListener(view -> {
        Intent intent = new Intent(context, RawDataPatientDetailsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("data", raw_dataArrayList.get(position));
        intent.putExtras(b);
        context.startActivity(intent);
      });
    }
  }

  @Override
  public int getItemCount() {
    return raw_dataArrayList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivPatient;
    private TextView tvPatientNumber, tvPatientAge, tvPatientCurrentStage, tvPatientCity;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      ivPatient = itemView.findViewById(R.id.iv_patient);
      tvPatientNumber = itemView.findViewById(R.id.tv_patient_number);
      tvPatientAge = itemView.findViewById(R.id.tv_patient_age);
      tvPatientCurrentStage = itemView.findViewById(R.id.tv_patient_current);
      tvPatientCity = itemView.findViewById(R.id.tv_patient_city);
    }
  }
}
