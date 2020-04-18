package com.vishal.covid19india.view.fragment.covid19;

import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.chip.Chip;
import com.vishal.covid19india.R;
import com.vishal.covid19india.adapters.Covid19ViewPagerAdapter;
import com.vishal.covid19india.model.Covid19.Factoids.FactoidsModel;
import com.vishal.covid19india.view.fragment.covid19.child.DataFragment;
import com.vishal.covid19india.view.fragment.covid19.child.RawDataFragment;
import com.vishal.covid19india.view.fragment.covid19.child.StateWiseFragment;
import com.vishal.covid19india.view.fragment.covid19.child.TestedDataFragment;
import com.vishal.covid19india.view.fragment.covid19.child.UpdateTimelineFragment;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Covid19Fragment extends Fragment implements OnClickListener {

  private ViewPager2 viewPager;
  private TextView tvHeader;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_covid_19, container, false);
    initUI(root);
    return root;
  }

  private void initUI(View root) {
    tvHeader = root.findViewById(R.id.tv_header);
    tvHeader.setEllipsize(TruncateAt.MARQUEE);
    tvHeader.setSelected(true);
    tvHeader.setSingleLine(true);
    Chip chipStateWiseData = root.findViewById(R.id.chip_state_wise_data);
    Chip chipUpdateTimeline = root.findViewById(R.id.chip_update_timeline_data);
    Chip chipTestedData = root.findViewById(R.id.chip_test_data);
    Chip chipRawData = root.findViewById(R.id.chip_raw_data);
    Chip chipData = root.findViewById(R.id.chip_data);
    viewPager = root.findViewById(R.id.view_pager);
    Covid19ViewPagerAdapter covid19Adapter = new Covid19ViewPagerAdapter(
        Objects.requireNonNull(getActivity()).getSupportFragmentManager(), getLifecycle());
    covid19Adapter.addFragment(new StateWiseFragment());
    covid19Adapter.addFragment(new UpdateTimelineFragment());
    covid19Adapter.addFragment(new TestedDataFragment());
    covid19Adapter.addFragment(new RawDataFragment());
    covid19Adapter.addFragment(new DataFragment());
    viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
    viewPager.setUserInputEnabled(false);
    viewPager.setAdapter(covid19Adapter);
    viewPager.setOffscreenPageLimit(5);
    chipStateWiseData.setOnClickListener(this);
    chipUpdateTimeline.setOnClickListener(this);
    chipTestedData.setOnClickListener(this);
    chipRawData.setOnClickListener(this);
    chipData.setOnClickListener(this);
    getFactoidsData();
  }

  private void getFactoidsData() {
    FactoidsModel.getFactoidsList(new Callback<FactoidsModel>() {
      @Override
      public void onResponse(@NotNull Call<FactoidsModel> call,
          @NotNull Response<FactoidsModel> response) {
        if (response.body() != null && response.body().getFactoids() != null) {
          StringBuilder s = new StringBuilder();
          for (int i = 0; i < response.body().getFactoids().size(); i++) {
            s.append(response.body().getFactoids().get(i).getId()).append(". ")
                .append(response.body().getFactoids().get(i).getBanner()).append("              ");
          }
          tvHeader.setText(s.toString());
        }
      }

      @Override
      public void onFailure(@NotNull Call<FactoidsModel> call, @NotNull Throwable t) {
        Toast.makeText(getActivity(), "Please Check internet connection", Toast.LENGTH_SHORT)
            .show();
      }
    });
  }

  @Override
  public void onClick(View view) {
    int position = 0;
    switch (view.getId()) {
      case R.id.chip_state_wise_data:
        position = 0;
        break;
      case R.id.chip_update_timeline_data:
        position = 1;
        break;
      case R.id.chip_test_data:
        position = 2;
        break;
      case R.id.chip_raw_data:
        position = 3;
        break;
      case R.id.chip_data:
        position = 4;
        break;
      default:
    }
    viewPager.setCurrentItem(position);
  }
}
