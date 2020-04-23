package com.vishal.covid19india.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.vipulasri.timelineview.TimelineView;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.covid19.updateTimeline.UpdateTimeline;
import java.util.ArrayList;

public class UpdateTimelineAdapter extends RecyclerView.Adapter<UpdateTimelineAdapter.ViewHolder> {

  private ArrayList<UpdateTimeline> updateTimelineList;
  private Context context;

  public UpdateTimelineAdapter(Context context,
      ArrayList<UpdateTimeline> updateTimelineList) {
    this.context = context;
    this.updateTimelineList = updateTimelineList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_item_update_timeline_data, parent, false), viewType);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    holder.tvMessage.setText(updateTimelineList.get(position).getUpdate());
    holder.tvDate.setText(updateTimelineList.get(position).getTimeShow());
    if (position==0){
      holder.mTimelineView.setMarker(context.getDrawable(R.drawable.ic_marker_inactive));
    } else if (position == 1) {
      holder.mTimelineView.setMarker(context.getDrawable(R.drawable.ic_marker_active));
    } else {
      holder.mTimelineView.setMarker(context.getDrawable(R.drawable.ic_marker));
    }

  }

  @Override
  public int getItemCount() {
    return updateTimelineList.size();
  }

  @Override
  public int getItemViewType(int position) {
    return TimelineView.getTimeLineViewType(position, getItemCount());
  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    TimelineView mTimelineView;
    private TextView tvDate, tvMessage;

    ViewHolder(@NonNull View itemView, int viewType) {
      super(itemView);
      tvDate = itemView.findViewById(R.id.text_timeline_date);
      tvMessage = itemView.findViewById(R.id.text_timeline_title);
      mTimelineView = itemView.findViewById(R.id.timeline);
      mTimelineView.initLine(viewType);
    }
  }
}
