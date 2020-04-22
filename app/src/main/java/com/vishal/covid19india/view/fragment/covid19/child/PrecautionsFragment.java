package com.vishal.covid19india.view.fragment.covid19.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.vishal.covid19india.R;
import com.vishal.covid19india.adapters.PrecautionsAdapter;
import com.vishal.covid19india.model.Covid19.precaution.Precautions;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrecautionsFragment extends Fragment implements OnRefreshListener {

  private SwipeRefreshLayout mSwipeRefreshLayout;
  private ArrayList<Precautions> precautionsList = new ArrayList<>();
  private PrecautionsAdapter precautionsAdapter;

  public PrecautionsFragment() {
  }


  public static PrecautionsFragment newInstance() {
    return new PrecautionsFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_precautions, container, false);
    initUI(root);
    setData();
    return root;
  }

  private void initUI(View view) {
    mSwipeRefreshLayout = view.findViewById(R.id.swipe_container_precautions_data);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_blue_dark);
    setRecyclerView(view);
  }

  @NonNull
  @Override
  public Lifecycle getLifecycle() {
    return super.getLifecycle();
  }

  private void setRecyclerView(View view) {
    RecyclerView rvUpdateTimeline = view.findViewById(R.id.rv_precautions_data);
    precautionsAdapter = new PrecautionsAdapter(getActivity(), precautionsList, this);
    rvUpdateTimeline
        .setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    rvUpdateTimeline.setHasFixedSize(true);
    rvUpdateTimeline.setAdapter(precautionsAdapter);
  }

  private void setData() {
    precautionsList.clear();
    mSwipeRefreshLayout.setRefreshing(true);
    Precautions.getPrecautionsList(new Callback<List<Precautions>>() {
      @Override
      public void onResponse(@NotNull Call<List<Precautions>> call,
          @NotNull Response<List<Precautions>> response) {
        if (response.body() != null) {
          precautionsList.addAll(response.body());
        }
        precautionsAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override
      public void onFailure(@NotNull Call<List<Precautions>> call, @NotNull Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }


  @Override
  public void onRefresh() {
    setData();
  }
}
