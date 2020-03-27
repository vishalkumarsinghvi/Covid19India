package com.android.covid19india.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.covid19india.R;
import com.android.covid19india.model.Covid19.Data.Statewise;
import java.util.ArrayList;

public class StateWiseAdapter extends RecyclerView.Adapter<StateWiseAdapter.ViewHolder> {

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
      holder.tvTotal.setText(statewiseArrayList.get(position).getState());
      holder.tvConfirmedNumber
          .setText(statewiseArrayList.get(position).getConfirmed());
      holder.tvRecoveredNumber
          .setText(statewiseArrayList.get(position).getRecovered());
      holder.tvDeathNumber
          .setText(statewiseArrayList.get(position).getDeaths());
      holder.tvActiveNumber
          .setText(statewiseArrayList.get(position).getActive());
    }
  }

  @Override
  public int getItemCount() {
    return statewiseArrayList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvTotal,tvConfirmedNumber, tvRecoveredNumber, tvDeathNumber, tvActiveNumber;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTotal = itemView.findViewById(R.id.tv_total);
      tvConfirmedNumber = itemView.findViewById(R.id.tv_confirmed_number);
      tvRecoveredNumber = itemView.findViewById(R.id.tv_recovered_number);
      tvDeathNumber = itemView.findViewById(R.id.tv_deaths_number);
      tvActiveNumber = itemView.findViewById(R.id.tv_active_number);
    }
  }
}
