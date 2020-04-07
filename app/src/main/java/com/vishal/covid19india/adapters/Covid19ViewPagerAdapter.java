package com.vishal.covid19india.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.vishal.covid19india.view.fragment.covid19.child.CityWiseFragment;
import com.vishal.covid19india.view.fragment.covid19.child.DataFragment;
import com.vishal.covid19india.view.fragment.covid19.child.RawDataFragment;
import com.vishal.covid19india.view.fragment.covid19.child.StateWiseFragment;
import com.vishal.covid19india.view.fragment.covid19.child.UpdateTimelineFragment;
import java.util.ArrayList;

public class Covid19ViewPagerAdapter extends FragmentStateAdapter {

  private ArrayList<Fragment> arrayList = new ArrayList<>();


  public Covid19ViewPagerAdapter(@NonNull FragmentManager fragmentManager,
      @NonNull Lifecycle lifecycle) {
    super(fragmentManager, lifecycle);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    Fragment fragment = StateWiseFragment.newInstance();
    switch (position) {
      case 0:
        fragment = StateWiseFragment.newInstance();
        break;
      case 1:
        fragment = UpdateTimelineFragment.newInstance();
        break;
      case 2:
        fragment = RawDataFragment.newInstance();
        break;
      case 3:
        fragment = CityWiseFragment.newInstance();
        break;
      case 4:
        fragment = DataFragment.newInstance();
        break;
    }
    return fragment;
  }


  public void addFragment(Fragment fragment) {
    arrayList.add(fragment);
  }

  @Override
  public int getItemCount() {
    return arrayList.size();
  }
}
