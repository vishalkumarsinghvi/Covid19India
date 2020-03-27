package com.android.covid19india.view.fragment.covid19.child;

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
import com.android.covid19india.R;
import com.android.covid19india.adapters.CityWiseAdapter;
import com.android.covid19india.model.Covid19.City.CityDataComparator;
import com.android.covid19india.model.Covid19.City.CityModel;
import com.android.covid19india.model.Covid19.Covid19Service;
import com.android.covid19india.model.Covid19.Data.Statewise;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityWiseFragment extends Fragment implements OnClickListener {

  private CityWiseAdapter cityWiseAdapter;
  private ArrayList<CityModel> cityWiseArrayList = new ArrayList<>();
  private TextView tvLatestUpdate;
  private TextView tvState, tvCity, tvConfirmed;

  private boolean isStateClicked;
  private boolean isCityClicked;
  private boolean isConfirmedClicked;


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
    tvLatestUpdate = view.findViewById(R.id.tv_latest_updated);
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
    Covid19Service.getInstanceCovid19().getStateDistrictWise().enqueue(new Callback<JsonObject>() {
      @Override
      public void onResponse(@NotNull Call<JsonObject> call,
          @NotNull Response<JsonObject> response) {
        assert response.body() != null;
        getListDataFromJson(response.body());
        cityWiseAdapter.notifyDataSetChanged();
      }

      @Override
      public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
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
        Collections.sort(cityWiseArrayList, new CityDataComparator.StateSorter(isStateClicked));
        cityWiseAdapter.notifyDataSetChanged();
        break;
      case R.id.tv_city:
        isCityClicked = !isCityClicked;
        Collections.sort(cityWiseArrayList, new CityDataComparator.CitySorter(isCityClicked));
        cityWiseAdapter.notifyDataSetChanged();
        break;
      case R.id.tv_confirmed:
        isConfirmedClicked = !isConfirmedClicked;
        Collections
            .sort(cityWiseArrayList, new CityDataComparator.ConfirmedSorter(isConfirmedClicked));
        cityWiseAdapter.notifyDataSetChanged();
        break;
    }
  }
}
