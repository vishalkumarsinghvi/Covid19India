package com.android.covid19india.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.covid19india.R;
import com.android.covid19india.model.Covid19.City.CityModel;
import java.util.ArrayList;

public class CityWiseAdapter extends RecyclerView.Adapter<CityWiseAdapter.ViewHolder> {

  private ArrayList<CityModel> cityModelArrayList;
  private Context context;

  public CityWiseAdapter(Context context,
      ArrayList<CityModel> cityModelArrayList) {
    this.context = context;
    this.cityModelArrayList = cityModelArrayList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_item_city_wise_data, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    if (cityModelArrayList != null) {
      holder.tvState.setText(cityModelArrayList.get(position).getState());
      holder.tvCity
          .setText(cityModelArrayList.get(position).getDistrict());
      holder.tvConfirmedNumber
          .setText(cityModelArrayList.get(position).getCityWiseData().getConfirmed());
    }
  }

  @Override
  public int getItemCount() {
    return cityModelArrayList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvState, tvCity, tvConfirmedNumber, tvDeathNumber, tvActiveNumber;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvState = itemView.findViewById(R.id.tv_state_city_wise);
      tvCity = itemView.findViewById(R.id.tv_city_city_wise);
      tvConfirmedNumber = itemView.findViewById(R.id.tv_city_confirmed_wise);
    }
  }
}
