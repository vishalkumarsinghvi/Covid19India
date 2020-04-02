package com.vishal.covid19india.view.fragment.covid19.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.vishal.covid19india.R;
import com.vishal.covid19india.adapters.RawAdapter;
import com.vishal.covid19india.model.Covid19.RawData.RawData;
import com.vishal.covid19india.model.Covid19.RawData.Raw_data;
import com.vishal.covid19india.model.Covid19.RawData.RowDataComparator.AgeSorter;
import com.vishal.covid19india.model.Covid19.RawData.RowDataComparator.CitySorter;
import com.vishal.covid19india.model.Covid19.RawData.RowDataComparator.CurrentSorter;
import com.vishal.covid19india.model.Covid19.RawData.RowDataComparator.PIDSorter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RawDataFragment extends Fragment implements OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

  private TextView tvPid, tvCurrent, tvAge, tvCity;
  private RawAdapter rawAdapter;
  private ArrayList<Raw_data> raw_dataArrayList = new ArrayList<>();
  private boolean isCityClicked;
  private boolean isCurrentClicked;
  private boolean isAgeClicked;
  private boolean isPIDClicked;
  private SwipeRefreshLayout mSwipeRefreshLayout;

  public RawDataFragment() {
  }

  public static RawDataFragment newInstance() {
    return new RawDataFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_raw_data, container, false);
    initUI(root);
    setData();
    return root;
  }

  private void initUI(View view) {
    tvPid = view.findViewById(R.id.tv_patient_id);
    tvAge = view.findViewById(R.id.tv_patient_age);
    tvCurrent = view.findViewById(R.id.tv_patient_stage);
    tvCity = view.findViewById(R.id.tv_patient_city);
    tvPid.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvAge.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvCurrent.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvPid.setOnClickListener(this);
    tvAge.setOnClickListener(this);
    tvCity.setOnClickListener(this);
    tvCurrent.setOnClickListener(this);
    mSwipeRefreshLayout = view.findViewById(R.id.swipe_container_raw_data);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_blue_dark);
    setUpRawDataRecyclerView(view);
  }

  private void setUpRawDataRecyclerView(View view) {
    RecyclerView rvRawData = view.findViewById(R.id.rv_raw_data);
    rawAdapter = new RawAdapter(getActivity(), raw_dataArrayList);
    rvRawData
        .setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    rvRawData.setHasFixedSize(true);
    rvRawData.addItemDecoration(
        new DividerItemDecoration(Objects.requireNonNull(getActivity()),
            DividerItemDecoration.VERTICAL));
    rvRawData.setAdapter(rawAdapter);
  }

  private void setData() {
    getRawData();
  }

  private void getRawData() {
    raw_dataArrayList.clear();
    mSwipeRefreshLayout.setRefreshing(true);
    RawData.getRawData(new Callback<RawData>() {
      @Override
      public void onResponse(@NotNull Call<RawData> call, @NotNull Response<RawData> response) {
        if (response.body() != null && response.body().getRaw_data() != null) {
          for (int i = 0; i < response.body().getRaw_data().size(); i++) {
            if (!response.body().getRaw_data().get(i).getCurrentstatus().equals("-")) {
              raw_dataArrayList.add(response.body().getRaw_data().get(i));
            }
          }
        }
        Collections.reverse(raw_dataArrayList);
        rawAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override
      public void onFailure(@NotNull Call<RawData> call, @NotNull Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }


  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_patient_id:
        isPIDClicked = !isPIDClicked;
        Collections.sort(raw_dataArrayList, new PIDSorter(isPIDClicked));
        rawAdapter.notifyDataSetChanged();
        break;
      case R.id.tv_patient_age:
        isAgeClicked = !isAgeClicked;
        Collections.sort(raw_dataArrayList, new AgeSorter(isAgeClicked));
        rawAdapter.notifyDataSetChanged();
        break;
      case R.id.tv_patient_stage:
        isCurrentClicked = !isCurrentClicked;
        Collections.sort(raw_dataArrayList, new CurrentSorter(isCurrentClicked));
        rawAdapter.notifyDataSetChanged();
        break;
      case R.id.tv_patient_city:
        isCityClicked = !isCityClicked;
        Collections.sort(raw_dataArrayList, new CitySorter(isCityClicked));
        rawAdapter.notifyDataSetChanged();
        break;
    }
  }

  @Override
  public void onRefresh() {
    getRawData();
  }
}
