package com.vishal.covid19india.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.covid19.testedData.States_tested_data;
import java.util.ArrayList;

public class TestedDataAdapter extends RecyclerView.Adapter<TestedDataAdapter.ViewHolder> {

  private ArrayList<States_tested_data> states_tested_dataArrayList;

  public TestedDataAdapter(Context context,
      ArrayList<States_tested_data> states_tested_dataArrayList) {
    this.states_tested_dataArrayList = states_tested_dataArrayList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_item_state_wise_test_data, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    if (states_tested_dataArrayList != null) {
      States_tested_data item = states_tested_dataArrayList.get(position);
      holder.tvState.setText(item.getState());
      holder.tvTotalTested
          .setText(!item.getTotaltested().equals("") ? item.getTotaltested() : "?");
      holder.tvPositive
          .setText(!item.getPositive().equals("") ? item.getPositive() : "?");
      holder.tvNegative
          .setText(!item.getNegative().equals("") ? item.getNegative() : "?");
      holder.tvDate
          .setText(!item.getUpdatedon().equals("") ? item.getUpdatedon() : "?");
      holder.itemView.setOnClickListener(view -> {
        if (!item.getSource().equals("")) {
          String url = item.getSource();
          Intent i = new Intent(Intent.ACTION_VIEW);
          i.setData(Uri.parse(url));
          view.getContext().startActivity(i);
        }
      });
    }
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    return states_tested_dataArrayList.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView tvState, tvTotalTested, tvPositive, tvNegative, tvDate;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvState = itemView.findViewById(R.id.tv_total);
      tvTotalTested = itemView.findViewById(R.id.tv_confirmed_number);
      tvPositive = itemView.findViewById(R.id.tv_recovered_number);
      tvNegative = itemView.findViewById(R.id.tv_deaths_number);
      tvDate = itemView.findViewById(R.id.tv_active_number);
    }
  }
}
