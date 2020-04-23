package com.vishal.covid19india.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.covid19.city.DistrictData;
import java.util.ArrayList;

public class CityWiseAdapter extends RecyclerView.Adapter<CityWiseAdapter.ViewHolder> {

  private ArrayList<DistrictData> cityModelArrayList;

  CityWiseAdapter(Context context,
      ArrayList<DistrictData> cityModelArrayList) {
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
      holder.tvCity
          .setText(cityModelArrayList.get(position).getDistrict());
      if (cityModelArrayList.get(position).getDelta().getConfirmed().equals("0")) {
        holder.tvConfirmedNumber
            .setText(cityModelArrayList.get(position).getConfirmed());
      } else {
        holder.tvConfirmedNumber
            .setText((cityModelArrayList.get(position).getConfirmed()).concat(
                "\n" + "▲ ".concat(cityModelArrayList.get(position).getDelta().getConfirmed())));
      }
    }
  }

  @Override
  public int getItemCount() {
    return cityModelArrayList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvCity, tvConfirmedNumber;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvCity = itemView.findViewById(R.id.tv_city_city_wise);
      tvConfirmedNumber = itemView.findViewById(R.id.tv_city_confirmed_wise);
    }
  }
}
