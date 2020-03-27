package com.vishal.covid19india.view.fragment.covid19;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsViewModel extends ViewModel {

  private MutableLiveData<String> mText;

  public NewsViewModel() {
    mText = new MutableLiveData<>();
    mText.setValue("This is news fragment");
  }

  public LiveData<String> getText() {
    return mText;
  }

  public void setText(String s){
    mText.setValue(s);
  }
}