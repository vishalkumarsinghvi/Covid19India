package com.android.covid19india.view.fragment.precaution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.covid19india.R;

public class PrecautionsFragment extends Fragment {

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_precautions, container, false);
    initUI(root);
    return root;
  }

  private void initUI(View view) {
  }

  private void setData() {

  }
}
