package com.vishal.covid19india.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.vishal.covid19india.R;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

  public Context context;
  private ArrayList<String> newsList;

  public NewsAdapter(Context context,
      ArrayList<String> newsList) {
    this.context = context;
    this.newsList = newsList;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.row_item_news_data, parent, false));
  }


  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    holder.webview.setTag(position);
    if (newsList != null) {
      if (newsList.get(position).startsWith("http://") || newsList.get(position)
          .startsWith("https://")) {
        holder.webview.loadData(getHtmlCode(newsList.get(position)), "text/html", "UTF-8");
        holder.webview.setWebViewClient(new WebViewClient() {
          @Override
          public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            holder.progressBar.setVisibility(View.GONE);
          }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            String url = newsList.get(position);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            arg0.getContext().startActivity(i);
          }
        });
      }
    }
  }

  public String getHtmlCode(String url) {
    return "<!DOCTYPE html>\n"
        + "<html>\n"
        + "<body>\n"
        + "\n"
        + "<blockquote class=\"twitter-tweet\"><a href=\""
        + url
        + "\"></a></blockquote>\n"
        + "<script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>\n"
        + "\n"
        + "</body>\n"
        + "</html>\n";
  }

  @Override
  public int getItemCount() {
    return newsList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    WebView webview;
    ProgressBar progressBar;
    RelativeLayout mainLayout;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      webview = itemView.findViewById(R.id.webview);
      progressBar = itemView.findViewById(R.id.progress_bar);
      mainLayout = itemView.findViewById(R.id.main_layout);
      webview.getSettings().setJavaScriptEnabled(true);
    }
  }

}
