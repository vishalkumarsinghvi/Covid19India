package com.android.covid19india.view.fragment.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.android.covid19india.R;

public class ContactFragment extends Fragment {


  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_contacts, container, false);
    initUI(root);
    return root;
  }

  private void initUI(View view) {

  }



}
