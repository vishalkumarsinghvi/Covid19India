package com.vishal.covid19india.view.fragment.covid19.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.vishal.covid19india.R;
import com.vishal.covid19india.adapters.UpdateTimelineAdapter;
import com.vishal.covid19india.model.Covid19.UpdateTimeline.UpdateTimeline;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTimelineFragment extends Fragment implements OnRefreshListener {

  private SwipeRefreshLayout mSwipeRefreshLayout;
  private TextView tvLatestUpdateTimeline;
  private ArrayList<UpdateTimeline> updateTimelineList = new ArrayList<>();
  private UpdateTimelineAdapter updateTimelineAdapter;

  public UpdateTimelineFragment() {
  }


  public static UpdateTimelineFragment newInstance() {
    return new UpdateTimelineFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_update_timeline, container, false);
    initUI(root);
    setData();
    return root;
  }

  private void initUI(View view) {
    mSwipeRefreshLayout = view.findViewById(R.id.swipe_container_update_timeline_data);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_blue_dark);
    setRecyclerView(view);
  }

  private void setRecyclerView(View view) {
    tvLatestUpdateTimeline = view.findViewById(R.id.tv_latest_updated_timeline);
    RecyclerView rvUpdateTimeline = view.findViewById(R.id.rv_update_timeline_data);
    updateTimelineAdapter = new UpdateTimelineAdapter(getActivity(), updateTimelineList);
    rvUpdateTimeline
        .setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    rvUpdateTimeline.setHasFixedSize(true);
    rvUpdateTimeline.setAdapter(updateTimelineAdapter);
  }

  private void setData() {
    updateTimelineList.clear();
    mSwipeRefreshLayout.setRefreshing(true);
    UpdateTimeline.getUpdateTimelineList(new Callback<List<UpdateTimeline>>() {
      @Override
      public void onResponse(@NotNull Call<List<UpdateTimeline>> call,
          @NotNull Response<List<UpdateTimeline>> response) {
        if (response.body() != null) {
          setHeaderDate();
          updateTimelineList.addAll(response.body());
          setTimeLineDate(response);
          Collections.reverse(updateTimelineList);
        }
        updateTimelineAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override
      public void onFailure(@NotNull Call<List<UpdateTimeline>> call, @NotNull Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  private void setTimeLineDate(
      @NotNull Response<List<UpdateTimeline>> response) {
    if (response.body() != null && response.body().size() > 0) {
      for (int i = 0; i < response.body().size(); i++) {
        Timestamp stamp = new Timestamp(
            Long.parseLong(updateTimelineList.get(i).getTimestamp()) * 1000);
        Date past = new Date(stamp.getTime());
        Date now = new Date();

        String result;
        if (TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) <= 59) {
          result = "ABOUT ".concat(
              TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " MINUTES AGO");
        } else if (TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) <= 24) {
          result =
              "ABOUT "
                  .concat(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())
                      + " HOURS AGO");
        } else {
          result =
              "ABOUT "
                  .concat(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime())
                      + " Days AGO");
        }
        updateTimelineList.get(i).setTimeShow(result);
      }
    }
  }

  private void setHeaderDate() {
    Date c = new Date();
    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
    String formattedDate = df.format(c);
    tvLatestUpdateTimeline.setText(formattedDate);
  }

  @Override
  public void onRefresh() {
    setData();
  }
}
