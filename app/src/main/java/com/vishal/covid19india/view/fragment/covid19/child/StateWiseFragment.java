package com.vishal.covid19india.view.fragment.covid19.child;

import static android.content.Context.MODE_PRIVATE;
import static com.vishal.covid19india.constants.AppConstants.subscribeToTopic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.vishal.covid19india.R;
import com.vishal.covid19india.adapters.StateWiseAdapter;
import com.vishal.covid19india.model.covid19.Covid19Service;
import com.vishal.covid19india.model.covid19.city.CityDataComparator;
import com.vishal.covid19india.model.covid19.city.NewCityModel;
import com.vishal.covid19india.model.covid19.data.Data;
import com.vishal.covid19india.model.covid19.data.StateWiseDataComparator.ActiveSorter;
import com.vishal.covid19india.model.covid19.data.StateWiseDataComparator.ConfirmedSorter;
import com.vishal.covid19india.model.covid19.data.StateWiseDataComparator.DeathsSorter;
import com.vishal.covid19india.model.covid19.data.StateWiseDataComparator.RecoveredSorter;
import com.vishal.covid19india.model.covid19.data.StateWiseDataComparator.StateSorter;
import com.vishal.covid19india.model.covid19.data.Statewise;
import com.vishal.covid19india.utils.AppController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StateWiseFragment extends Fragment implements OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

  private TextView tvConfirmedToday, tvActiveToday, tvRecoveredToday, tvDeathToday;
  private StateWiseAdapter stateWiseAdapter;
  private ArrayList<Statewise> statewiseArrayList = new ArrayList<>();
  private TextView tvLatestUpdate;
  private boolean isStateClicked;
  private boolean isConfirmedClicked;
  private boolean isDeathClicked;
  private boolean isRecoveredClicked;
  private boolean isActiveClicked;
  private SwipeRefreshLayout mSwipeRefreshLayout;
  private ImageView ivRing;
  private boolean ivRingClicked;


  public StateWiseFragment() {
  }

  public static StateWiseFragment newInstance() {
    return new StateWiseFragment();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_state_wise, container, false);
    initUI(root);
    setData();
    return root;
  }

  private void initUI(View view) {
    SharedPreferences sharedPreferences = requireActivity().getApplicationContext()
        .getSharedPreferences(requireActivity().getPackageName(), MODE_PRIVATE);
    ivRing = view.findViewById(R.id.iv_ring);
    TextView tvState = view.findViewById(R.id.tv_state);
    TextView tvConfirmed = view.findViewById(R.id.tv_confirmed);
    TextView tvActive = view.findViewById(R.id.tv_active);
    TextView tvRecovered = view.findViewById(R.id.tv_recovered);
    TextView tvDeath = view.findViewById(R.id.tv_deaths);
    tvConfirmedToday = view.findViewById(R.id.tv_confirmed_today);
    tvActiveToday = view.findViewById(R.id.tv_active_today);
    tvRecoveredToday = view.findViewById(R.id.tv_recovered_today);
    tvDeathToday = view.findViewById(R.id.tv_deaths_today);
    tvState.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvConfirmed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvActive.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvRecovered.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvDeath.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.up_down, 0);
    tvState.setOnClickListener(this);
    tvConfirmed.setOnClickListener(this);
    tvActive.setOnClickListener(this);
    tvRecovered.setOnClickListener(this);
    tvDeath.setOnClickListener(this);
    ivRing.setOnClickListener(this);
    mSwipeRefreshLayout = view.findViewById(R.id.swipe_container_state_wise_data);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_blue_dark);
    setUpRawDataRecyclerView(view);
    ivRingClicked = sharedPreferences.getBoolean(subscribeToTopic, false);
    if (ivRingClicked) {
      ivRing.setBackgroundResource(R.drawable.ic_notificaion_on);
    } else {
      ivRing.setBackgroundResource(R.drawable.ic_notificaion_off);
    }
  }

  private void setUpRawDataRecyclerView(View view) {
    tvLatestUpdate = view.findViewById(R.id.tv_latest_updated);
    RecyclerView rvStateWiseData = view.findViewById(R.id.rv_state_wise_data);
    stateWiseAdapter = new StateWiseAdapter(getActivity(), statewiseArrayList);
    rvStateWiseData
        .setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    rvStateWiseData.setHasFixedSize(true);
    rvStateWiseData.addItemDecoration(
        new DividerItemDecoration(requireActivity(),
            DividerItemDecoration.VERTICAL));
    rvStateWiseData.setAdapter(stateWiseAdapter);
  }

  private void setData() {
    getStateWiseData();
  }

  private void getStateWiseData() {
    statewiseArrayList.clear();
    mSwipeRefreshLayout.setRefreshing(true);
    Data.getData(new Callback<Data>() {
      @Override
      public void onResponse(@NotNull Call<Data> call, @NotNull Response<Data> response) {
        if (response.body() != null && response.body().getStatewise() != null) {
          SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
          Date past = null;
          Date now = new Date();
          if (response.body().getStatewise().get(0).getLastupdatedtime() != null) {
            try {
              past = format.parse(response.body().getStatewise().get(0).getLastupdatedtime());
            } catch (ParseException e) {
              e.printStackTrace();
            }
          }
          if (past != null) {
            String result;
            if (TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) <= 59) {
              result = "LAST UPDATED ".concat(
                  TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " MINUTES AGO");
            } else {
              result =
                  "LAST UPDATED "
                      .concat(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime())
                          + " HOURS AGO");
            }
            tvLatestUpdate.setText(result);
          } else {
            if (response.body().getStatewise().get(0).getLastupdatedtime() != null) {
              tvLatestUpdate.setText(response.body().getStatewise().get(0).getLastupdatedtime());
            }
          }
          tvConfirmedToday.setText(response.body().getStatewise().get(0).getTotalDeltaconfirmed());
          tvRecoveredToday.setText(response.body().getStatewise().get(0).getTotalDeltarecovered());
          tvDeathToday.setText(response.body().getStatewise().get(0).getTotalDeltadeaths());
          tvActiveToday.setText(response.body().getStatewise().get(0).getTotalDeltaActive());
          for (int i = 0; i < response.body().getStatewise().size(); i++) {
            if (!response.body().getStatewise().get(i).getConfirmed().equals("0")) {
              statewiseArrayList.add(response.body().getStatewise().get(i));
            }
          }
          getCityData();
        }
      }

      @Override
      public void onFailure(@NotNull Call<Data> call, @NotNull Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }

  private void getCityData() {
    Covid19Service.getInstanceCovid19().getNewCityDistrictWise()
        .enqueue(new Callback<List<NewCityModel>>() {
          @Override
          public void onResponse(@NotNull Call<List<NewCityModel>> call,
              @NotNull Response<List<NewCityModel>> response) {
            if (response.body() != null) {
              for (int i = 1; i < statewiseArrayList.size(); i++) {
                for (int j = 0; j < response.body().size(); j++) {
                  if (statewiseArrayList.get(i).getState()
                      .equals(response.body().get(j).getState())) {
                    Collections.sort(response.body().get(j).getDistrictData(),
                        new CityDataComparator.ConfirmedSorter(false));
                    statewiseArrayList.get(i)
                        .setDistrictDataList(response.body().get(j).getDistrictData());
                    break;
                  }
                }
              }
            }
            stateWiseAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
          }

          @Override
          public void onFailure(@NotNull Call<List<NewCityModel>> call, @NotNull Throwable t) {
          }
        });
  }

  @Override
  public void onClick(View view) {
    if (statewiseArrayList.size() > 0) {
      Statewise statewises = statewiseArrayList.get(0);
      statewiseArrayList.remove(0);
      switch (view.getId()) {
        case R.id.tv_state:
          isStateClicked = !isStateClicked;
          Collections
              .sort(statewiseArrayList, new StateSorter(isStateClicked));
          break;
        case R.id.tv_confirmed:
          isConfirmedClicked = !isConfirmedClicked;
          Collections.sort(statewiseArrayList,
              new ConfirmedSorter(isConfirmedClicked));
          break;
        case R.id.tv_active:
          isActiveClicked = !isActiveClicked;
          Collections
              .sort(statewiseArrayList, new ActiveSorter(isActiveClicked));
          break;
        case R.id.tv_recovered:
          isRecoveredClicked = !isRecoveredClicked;
          Collections.sort(statewiseArrayList,
              new RecoveredSorter(isRecoveredClicked));
          break;
        case R.id.tv_deaths:
          isDeathClicked = !isDeathClicked;
          Collections
              .sort(statewiseArrayList, new DeathsSorter(isDeathClicked));
          break;
        case R.id.iv_ring:
          AppController appController = new AppController(getActivity());
          if (ivRingClicked) {
            ivRingClicked = false;
            ivRing.setBackgroundResource(R.drawable.ic_notificaion_off);
            appController.unSubScribeToChannel();
            Toast.makeText(getActivity(), "Turn off notification for breaking news",
                Toast.LENGTH_SHORT)
                .show();
          } else {
            ivRingClicked = true;
            ivRing.setBackgroundResource(R.drawable.ic_notificaion_on);
            appController.subScribeToChannel();
            Toast
                .makeText(getActivity(), "Turn on notification for breaking news",
                    Toast.LENGTH_SHORT)
                .show();
          }
          break;
      }
      statewiseArrayList.add(0, statewises);
      stateWiseAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void onRefresh() {
    getStateWiseData();
  }
}
