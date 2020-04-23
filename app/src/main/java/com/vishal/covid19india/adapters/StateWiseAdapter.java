package com.vishal.covid19india.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.covid19.city.CityDataComparator.CitySorter;
import com.vishal.covid19india.model.covid19.city.CityDataComparator.ConfirmedSorter;
import com.vishal.covid19india.model.covid19.data.Statewise;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class StateWiseAdapter extends RecyclerView.Adapter<StateWiseAdapter.ViewHolder> {

  private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
  private ArrayList<Statewise> statewiseArrayList;
  private Context context;

  public StateWiseAdapter(Context context,
      ArrayList<Statewise> statewiseArrayList) {
    this.context = context;
    this.statewiseArrayList = statewiseArrayList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_item_state_wise_data, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    if (statewiseArrayList != null) {
      Statewise item = statewiseArrayList.get(position);
      holder.tvTotal.setText(item.getState());
      if (item.getDeltaconfirmed().equals("0")) {
        holder.tvConfirmedNumber
            .setText(item.getConfirmed());
      } else {
        holder.tvConfirmedNumber
            .setText((item.getConfirmed()).concat("\n" + "▲ ").concat(item.getDeltaconfirmed()));
      }
      if (item.getDeltarecovered().equals("0")) {
        holder.tvRecoveredNumber
            .setText(item.getRecovered());
      } else {
        holder.tvRecoveredNumber
            .setText((item.getRecovered()).concat("\n" + "▲ ").concat(item.getDeltarecovered()));
      }
      if (item.getDeltadeaths().equals("0")) {
        holder.tvDeathNumber
            .setText(item.getDeaths());
      } else {
        holder.tvDeathNumber
            .setText((item.getDeaths()).concat("\n" + "▲ ").concat(item.getDeltadeaths()));
      }
      holder.tvActiveNumber
          .setText(item.getActive());
      if (item.getDistrictDataList() != null) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(
            holder.rvCityWiseData.getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setInitialPrefetchItemCount(
            item.getDistrictDataList().size());

        CityWiseAdapter cityWiseAdapter = new CityWiseAdapter(context,
            item.getDistrictDataList());
        holder.rvCityWiseData
            .setLayoutManager(layoutManager);
        holder.rvCityWiseData.setHasFixedSize(true);
        holder.rvCityWiseData.addItemDecoration(
            new DividerItemDecoration(Objects.requireNonNull(context),
                DividerItemDecoration.VERTICAL));
        holder.rvCityWiseData.setAdapter(cityWiseAdapter);
        holder.rvCityWiseData.setRecycledViewPool(viewPool);
        holder.rvCityWiseData.setVisibility(!item.isVisible ? View.VISIBLE : View.GONE);
        holder.llRowData.setVisibility(!item.isVisible ? View.VISIBLE : View.GONE);
        holder.tvTotal.setCompoundDrawablesWithIntrinsicBounds(
            !item.isVisible ? R.drawable.ic_down : R.drawable.ic_right, 0, 0, 0);
      } else {
        holder.tvTotal.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        holder.rvCityWiseData.setVisibility(View.GONE);
        holder.llRowData.setVisibility(View.GONE);
      }
      holder.itemView.setOnClickListener(view -> {
        if (position != RecyclerView.NO_POSITION) {
          item.isVisible = !item.isVisible;
          notifyItemChanged(position);
        }
      });
      holder.tvDistrict.setOnClickListener(view -> {
        item.isCityClicked = !item.isCityClicked;
        Collections.sort(statewiseArrayList.get(position).getDistrictDataList(),
            new CitySorter(item.isCityClicked));
        notifyItemChanged(position);
      });
      holder.tvDistrictConfirmed.setOnClickListener(view -> {
        item.isConfirmedClicked = !item.isConfirmedClicked;
        Collections.sort(statewiseArrayList.get(position).getDistrictDataList(),
            new ConfirmedSorter(item.isConfirmedClicked));
        notifyItemChanged(position);
      });
    }
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return statewiseArrayList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTotal, tvConfirmedNumber, tvRecoveredNumber, tvDeathNumber, tvActiveNumber, tvDistrict, tvDistrictConfirmed;
    private RecyclerView rvCityWiseData;
    private LinearLayout llRowData;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTotal = itemView.findViewById(R.id.tv_total);
      tvConfirmedNumber = itemView.findViewById(R.id.tv_confirmed_number);
      tvRecoveredNumber = itemView.findViewById(R.id.tv_recovered_number);
      tvDeathNumber = itemView.findViewById(R.id.tv_deaths_number);
      tvActiveNumber = itemView.findViewById(R.id.tv_active_number);
      rvCityWiseData = itemView.findViewById(R.id.rv_city_wise_data_in_city);
      llRowData = itemView.findViewById(R.id.ll_city_wise_data_new);
      tvDistrict = itemView.findViewById(R.id.tv_District);
      tvDistrictConfirmed = itemView.findViewById(R.id.tv_District_confirmed);
      tvDistrict.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
      tvDistrictConfirmed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    }
  }
}
