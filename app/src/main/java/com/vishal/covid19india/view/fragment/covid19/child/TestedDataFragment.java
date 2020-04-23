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
import com.vishal.covid19india.adapters.TestedDataAdapter;
import com.vishal.covid19india.model.covid19.testedData.States_tested_data;
import com.vishal.covid19india.model.covid19.testedData.TestedData;
import com.vishal.covid19india.model.covid19.testedData.TestedDataComparator.DateSorter;
import com.vishal.covid19india.model.covid19.testedData.TestedDataComparator.NegativeSorter;
import com.vishal.covid19india.model.covid19.testedData.TestedDataComparator.PositiveSorter;
import com.vishal.covid19india.model.covid19.testedData.TestedDataComparator.StateSorter;
import com.vishal.covid19india.model.covid19.testedData.TestedDataComparator.TotalTestedSorter;
import java.util.ArrayList;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestedDataFragment extends Fragment implements OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

  private TestedDataAdapter testedDataAdapter;
  private ArrayList<States_tested_data> testedDataArray = new ArrayList<>();
  private boolean isStateClickedTestData;
  private boolean isTotalTestedClickedTestData;
  private boolean isPositiveClickedTestData;
  private boolean isNegativeClickedTestData;
  private boolean isDateClickedTestData;
  private SwipeRefreshLayout mSwipeRefreshLayoutTestData;

  public TestedDataFragment() {
  }

  public static TestedDataFragment newInstance() {
    return new TestedDataFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_tested_data, container, false);
    initUI(root);
    setData();
    return root;
  }

  private void initUI(View view) {
    TextView tvStateTestData = view.findViewById(R.id.tv_state_test_data);
    TextView tvTotalTestedData = view.findViewById(R.id.tv_total_test_data);
    TextView tvPositiveTestData = view.findViewById(R.id.tv_positive_test_data);
    TextView tvNegativeTestData = view.findViewById(R.id.tv_negative_test_data);
    TextView tvDateTestData = view.findViewById(R.id.tv_date_test_data);
    tvStateTestData.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvTotalTestedData.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvPositiveTestData.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvNegativeTestData.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvDateTestData.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvStateTestData.setOnClickListener(this);
    tvTotalTestedData.setOnClickListener(this);
    tvPositiveTestData.setOnClickListener(this);
    tvNegativeTestData.setOnClickListener(this);
    tvDateTestData.setOnClickListener(this);
    mSwipeRefreshLayoutTestData = view.findViewById(R.id.swipe_container_state_wise_test_data);
    mSwipeRefreshLayoutTestData.setOnRefreshListener(this);
    mSwipeRefreshLayoutTestData.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_blue_dark);
    setUpRawDataRecyclerView(view);
  }

  private void setUpRawDataRecyclerView(View view) {
    RecyclerView rvStateWiseData = view.findViewById(R.id.rv_state_wise_test_data);
    testedDataAdapter = new TestedDataAdapter(getActivity(), testedDataArray);
    rvStateWiseData
        .setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    rvStateWiseData.setHasFixedSize(true);
    rvStateWiseData.addItemDecoration(
        new DividerItemDecoration(requireActivity(),
            DividerItemDecoration.VERTICAL));
    rvStateWiseData.setAdapter(testedDataAdapter);
  }

  private void setData() {
    getStateWiseData();
  }

  private void getStateWiseData() {
    testedDataArray.clear();
    mSwipeRefreshLayoutTestData.setRefreshing(true);
    TestedData.getTestedData(new Callback<TestedData>() {
      @Override
      public void onResponse(@NotNull Call<TestedData> call,
          @NotNull Response<TestedData> response) {
        if (response.body() != null) {
//          SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//          Date now = new Date();
//          tvLatestUpdateTestData.setText(format.format(now));
          ArrayList<States_tested_data> states_tested_data = response.body()
              .getStates_tested_data();
          for (int i = 0; i < states_tested_data.size(); i++) {
            if (states_tested_data.get(i).getTotaltested().equals("")) {
              states_tested_data.remove(states_tested_data.get(i));
            }
          }
          states_tested_data.add(new States_tested_data(""));
          for (int i = 0; i < states_tested_data.size() - 1; i++) {
            if (!states_tested_data.get(i).getTotaltested().equals("")
                && !states_tested_data.get(i).getState()
                .equals(states_tested_data.get(i + 1).getState())) {
              testedDataArray.add(states_tested_data.get(i));
            }
          }
          Collections.sort(testedDataArray,
              new TotalTestedSorter(isTotalTestedClickedTestData));
          testedDataAdapter.notifyDataSetChanged();
          mSwipeRefreshLayoutTestData.setRefreshing(false);
        }
      }

      @Override
      public void onFailure(@NotNull Call<TestedData> call, @NotNull Throwable t) {
        mSwipeRefreshLayoutTestData.setRefreshing(false);
      }
    });
  }


  @Override
  public void onClick(View view) {
    if (testedDataArray.size() > 0) {
      switch (view.getId()) {
        case R.id.tv_state_test_data:
          isStateClickedTestData = !isStateClickedTestData;
          Collections
              .sort(testedDataArray, new StateSorter(isStateClickedTestData));
          break;
        case R.id.tv_total_test_data:
          isTotalTestedClickedTestData = !isTotalTestedClickedTestData;
          Collections.sort(testedDataArray,
              new TotalTestedSorter(isTotalTestedClickedTestData));
          break;
        case R.id.tv_positive_test_data:
          isPositiveClickedTestData = !isPositiveClickedTestData;
          Collections.sort(testedDataArray, new PositiveSorter(isPositiveClickedTestData));
          break;
        case R.id.tv_negative_test_data:
          isNegativeClickedTestData = !isNegativeClickedTestData;
          Collections.sort(testedDataArray,
              new NegativeSorter(isNegativeClickedTestData));
          break;
        case R.id.tv_date_test_data:
          isDateClickedTestData = !isDateClickedTestData;
          Collections
              .sort(testedDataArray, new DateSorter(isDateClickedTestData));
          break;
      }
      testedDataAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void onRefresh() {
    getStateWiseData();
  }
}
