package com.vishal.covid19india.view.fragment.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;
import com.vishal.covid19india.R;
import com.vishal.covid19india.adapters.NewsAdapter;
import com.vishal.covid19india.model.Covid19.RawData.RawData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment implements OnRefreshListener {

  private NewsAdapter newsAdapter;
  private ArrayList<String> newsList = new ArrayList<>();
  private SwipeRefreshLayout mSwipeRefreshLayout;

  public View onCreateView(@NonNull LayoutInflater inflater,
      ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_news, container, false);
    initUI(root);
    setData();
    return root;
  }

  private void initUI(View view) {
    mSwipeRefreshLayout = view.findViewById(R.id.swipe_container_news_data);
    mSwipeRefreshLayout.setOnRefreshListener(this);
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
        android.R.color.holo_green_dark,
        android.R.color.holo_orange_dark,
        android.R.color.holo_blue_dark);
    setUpNewsDataRecyclerView(view);
  }

  private void setUpNewsDataRecyclerView(View view) {
    RecyclerView rvNewsData = view.findViewById(R.id.rv_news_data);
    newsAdapter = new NewsAdapter(getActivity(), newsList);
    rvNewsData.setLayoutManager(new LinearLayoutManager(getActivity(),
        RecyclerView.VERTICAL, false));
    rvNewsData.setHasFixedSize(true);
    rvNewsData.addItemDecoration(
        new DividerItemDecoration(Objects.requireNonNull(getActivity()),
            DividerItemDecoration.VERTICAL));
    rvNewsData.setAdapter(newsAdapter);
  }


  private void setData() {
    getRawData();
  }

  @Override
  public void onRefresh() {
    getRawData();
  }

  private void getRawData() {
    newsList.clear();
    mSwipeRefreshLayout.setRefreshing(true);
    RawData.getRawData(new Callback<RawData>() {
      @Override
      public void onResponse(@NotNull Call<RawData> call, @NotNull Response<RawData> response) {
        if (response.body() != null && response.body().getRaw_data() != null) {
          for (int i = 0; i < response.body().getRaw_data().size(); i++) {
            if (!response.body().getRaw_data().get(i).getSource().equals("") && response.body()
                .getRaw_data().get(i).getSource().contains("twitter")) {
              String string = response.body().getRaw_data().get(i).getSource();
              newsList.add(string);
            }
          }
        }
        Collections.reverse(newsList);
        LinkedHashSet<String> hashSet = new LinkedHashSet<>(newsList);
        newsList.clear();
        newsList.addAll(hashSet);
        newsAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override
      public void onFailure(@NotNull Call<RawData> call, @NotNull Throwable t) {
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }
}
