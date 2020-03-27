package com.vishal.covid19india.view.fragment.covid19.child;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.Covid19.Data.Data;
import com.vishal.covid19india.utils.NetworkUtils;
import com.google.android.material.chip.Chip;
import im.dacer.androidcharts.LineView;
import java.util.ArrayList;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataFragment extends Fragment implements OnClickListener {

  private ArrayList<Integer> confirmedTotalCase = new ArrayList<>();
  private ArrayList<Integer> recoveredTotalCase = new ArrayList<>();
  private ArrayList<Integer> deceasedTotalCase = new ArrayList<>();
  private ArrayList<Integer> confirmedDailyCase = new ArrayList<>();
  private ArrayList<Integer> recoveredDailyCase = new ArrayList<>();
  private ArrayList<Integer> deceasedDailyCase = new ArrayList<>();
  private ArrayList<String> dateData = new ArrayList<>();
  private LineView lineView;

  public DataFragment() {
  }

  public static DataFragment newInstance() {
    return new DataFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_data, container, false);
    initUI(root);
    return root;
  }


  private void initUI(View view) {
    Chip chipCumulativeData = view.findViewById(R.id.chip_Cumulative_wise_data);
    Chip chipDailyData = view.findViewById(R.id.chip_daily_data);
    lineView = view.findViewById(R.id.line_view);
    lineView.setDrawDotLine(true);
    lineView.setShowPopup(1);
    chipCumulativeData.setOnClickListener(this);
    chipDailyData.setOnClickListener(this);
    setData();
  }


  private void setData() {
    Data.getData(new Callback<Data>() {
      @Override
      public void onResponse(@NotNull Call<Data> call, @NotNull Response<Data> response) {
        if (response.body() != null && response.body().getCases_time_series()!=null) {
          for (int i = 0; i < response.body().getCases_time_series().size(); i++) {
            if (!response.body().getCases_time_series().get(i).getTotalconfirmed().equals("")) {
              confirmedTotalCase.add(Integer
                  .parseInt(response.body().getCases_time_series().get(i).getTotalconfirmed()));
              recoveredTotalCase.add(Integer
                  .parseInt(response.body().getCases_time_series().get(i).getTotalrecovered()));
              deceasedTotalCase.add(Integer
                  .parseInt(response.body().getCases_time_series().get(i).getTotaldeceased()));
              confirmedDailyCase.add(Integer
                  .parseInt(response.body().getCases_time_series().get(i).getDailyconfirmed()));
              recoveredDailyCase.add(Integer
                  .parseInt(response.body().getCases_time_series().get(i).getDailyrecovered()));
              deceasedDailyCase.add(Integer
                  .parseInt(response.body().getCases_time_series().get(i).getDailydeceased()));
              dateData.add(response.body().getCases_time_series().get(i).getDate());
            }
          }
          setChartTotalData();
        }
      }

      @Override
      public void onFailure(@NotNull Call<Data> call, @NotNull Throwable t) {
      }
    });
  }

  private void setChartTotalData() {
    ArrayList<ArrayList<Integer>> finalData = new ArrayList<>();
    finalData.add(confirmedTotalCase);
    finalData.add(recoveredTotalCase);
    finalData.add(deceasedTotalCase);
    lineView.setBottomTextList(dateData);
    lineView.setColorArray(new int[]{Color.RED, Color.GREEN, Color.GRAY});
    lineView.setDataList(finalData);
  }

  private void setChartDailyData() {
    ArrayList<ArrayList<Integer>> finalData = new ArrayList<>();
    finalData.add(confirmedDailyCase);
    finalData.add(recoveredDailyCase);
    finalData.add(deceasedDailyCase);
    lineView.setBottomTextList(dateData);
    lineView.setColorArray(new int[]{Color.RED, Color.GREEN, Color.GRAY});
    lineView.setDataList(finalData);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.chip_Cumulative_wise_data:
        if (NetworkUtils.isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
          setChartTotalData();
        }

        break;
      case R.id.chip_daily_data:
        if (NetworkUtils.isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
          setChartDailyData();
        }
        break;
      default:
    }
  }
}
