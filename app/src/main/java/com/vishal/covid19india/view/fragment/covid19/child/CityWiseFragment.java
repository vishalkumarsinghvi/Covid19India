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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.vishal.covid19india.R;
import com.vishal.covid19india.adapters.CityWiseAdapter;
import com.vishal.covid19india.model.Covid19.City.CityDataComparator.CitySorter;
import com.vishal.covid19india.model.Covid19.City.CityDataComparator.ConfirmedSorter;
import com.vishal.covid19india.model.Covid19.City.CityDataComparator.StateSorter;
import com.vishal.covid19india.model.Covid19.City.CityModel;
import com.vishal.covid19india.model.Covid19.Covid19Service;
import com.vishal.covid19india.model.Covid19.Data.Statewise;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityWiseFragment extends Fragment implements OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

  private CityWiseAdapter cityWiseAdapter;
  private ArrayList<CityModel> cityWiseArrayList = new ArrayList<>();
  private TextView tvState, tvCity, tvConfirmed;

  private boolean isStateClicked;
  private boolean isCityClicked;
  private boolean isConfirmedClicked;
  private SwipeRefreshLayout mSwipeRefreshLayout;


  public CityWiseFragment() {
  }

  public static CityWiseFragment newInstance() {
    return new CityWiseFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_city_wise, container, false);
    initUI(root);
    setData();
    return root;
  }

  private void initUI(View view) {
    setUpRawDataRecyclerView(view);
  }

  private void setUpRawDataRecyclerView(View view) {
    tvState = view.findViewById(R.id.tv_state);
    tvCity = view.findViewById(R.id.tv_city);
    tvConfirmed = view.findViewById(R.id.tv_confirmed);
    tvState.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvConfirmed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvCity.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvState.setOnClickListener(this);
    tvCity.setOnClickListener(this);
    tvConfirmed.setOnClickListener(this);
    mSwipeRefreshLayout = view.findViewById(R.id.swipe_container_city_wise_data);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_blue_dark);
    setUpCityWiseRecyclerView(view);
  }

  private void setUpCityWiseRecyclerView(View view) {
    RecyclerView rvCityWiseData = view.findViewById(R.id.rv_city_wise_data);
    cityWiseAdapter = new CityWiseAdapter(getActivity(), cityWiseArrayList);
    rvCityWiseData
        .setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    rvCityWiseData.setHasFixedSize(true);
    rvCityWiseData.addItemDecoration(
        new DividerItemDecoration(Objects.requireNonNull(getActivity()),
            DividerItemDecoration.VERTICAL));
    rvCityWiseData.setAdapter(cityWiseAdapter);
  }

  private void setData() {
    getCityData();
  }

  private void getCityData() {
    cityWiseArrayList.clear();
    mSwipeRefreshLayout.setRefreshing(true);
    Covid19Service.getInstanceCovid19().getStateDistrictWise().enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NotNull Call<JsonObject> call,
          @NotNull Response<JsonObject> response) {
        if (response.body() != null) {
          getListDataFromJson(response.body());
          cityWiseAdapter.notifyDataSetChanged();
        }
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override
      public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  private void getListDataFromJson(JsonObject jsonObject) {
    for (String key : jsonObject.keySet()) {
      JsonObject jsonObjectState = new Gson().fromJson(jsonObject.get(key), JsonObject.class);
      JsonObject jsonObjectDistrictData = new Gson()
          .fromJson(jsonObjectState.get("districtData"), JsonObject.class);
      for (String string : jsonObjectDistrictData.keySet()) {
        CityModel cityModel = new CityModel();
        cityModel.setState(key);
        cityModel.setDistrict(string);
        Statewise statewise = new Gson()
            .fromJson(jsonObjectDistrictData.get(string), Statewise.class);
        cityModel.setCityWiseData(statewise);
        cityWiseArrayList.add(cityModel);
      }
    }
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.tv_state:
        isStateClicked = !isStateClicked;
        Collections.sort(cityWiseArrayList, new StateSorter(isStateClicked));
        cityWiseAdapter.notifyDataSetChanged();
        break;
      case R.id.tv_city:
        isCityClicked = !isCityClicked;
        Collections.sort(cityWiseArrayList, new CitySorter(isCityClicked));
        cityWiseAdapter.notifyDataSetChanged();
        break;
      case R.id.tv_confirmed:
        isConfirmedClicked = !isConfirmedClicked;
        Collections
            .sort(cityWiseArrayList, new ConfirmedSorter(isConfirmedClicked));
        cityWiseAdapter.notifyDataSetChanged();
        break;
    }
  }

  @Override
  public void onRefresh() {
    getCityData();
  }
}
