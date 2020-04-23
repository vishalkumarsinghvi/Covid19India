package com.vishal.covid19india.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderLayout.Transformer;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.indicators.PagerIndicator;
import com.glide.slider.library.slidertypes.TextSliderView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.covid19.precaution.Precautions;
import com.vishal.covid19india.view.fragment.covid19.child.PrecautionsFragment;
import java.util.ArrayList;

public class PrecautionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static int TYPE_IMAGE = 1;
  private static int TYPE_VIDEO = 2;
  private static int TYPE_TEXT = 3;
  private static int TYPE_SLIDER = 4;
  private PrecautionsFragment precautionsFragment;
  private ArrayList<Precautions> precautionsList;
  private Context context;
  private ArrayList<String> videoId;

  public PrecautionsAdapter(Context context,
      ArrayList<Precautions> precautionsList, PrecautionsFragment precautionsFragment) {
    this.context = context;
    this.precautionsList = precautionsList;
    this.precautionsFragment = precautionsFragment;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view;
    if (viewType == TYPE_IMAGE) {
      view = LayoutInflater.from(context).inflate(R.layout.row_item_image, parent, false);
      return new ImageViewHolder(view);
    } else if (viewType == TYPE_VIDEO) {
      view = LayoutInflater.from(context).inflate(R.layout.row_item_video, parent, false);
      return new VideoViewHolder(view);
    } else if (viewType == TYPE_TEXT) {
      view = LayoutInflater.from(context).inflate(R.layout.row_item_text, parent, false);
      return new TextViewHolder(view);
    } else if (viewType == TYPE_SLIDER) {
      view = LayoutInflater.from(context).inflate(R.layout.row_item_image_slider, parent, false);
      return new SliderViewHolder(view);
    } else {
      return null;
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
    if (getItemViewType(position) == TYPE_IMAGE) {
      ((ImageViewHolder) viewHolder).setDetails(precautionsList.get(position));
    } else if (getItemViewType(position) == TYPE_VIDEO) {
      ((VideoViewHolder) viewHolder).setDetails(precautionsList.get(position));
    } else if (getItemViewType(position) == TYPE_TEXT) {
      ((TextViewHolder) viewHolder).setDetails(precautionsList.get(position));
    } else if (getItemViewType(position) == TYPE_SLIDER) {
      ((SliderViewHolder) viewHolder).setDetails(precautionsList.get(position));
    }
  }

  @Override
  public int getItemCount() {
    return precautionsList.size();
  }

  @Override
  public int getItemViewType(int position) {
    int Type = 0;
    switch (precautionsList.get(position).getType()) {
      case "IMAGE":
        Type = TYPE_IMAGE;
        break;
      case "VIDEO":
        Type = TYPE_VIDEO;
        break;
      case "TEXT":
        Type = TYPE_TEXT;
        break;
      case "SLIDER":
        Type = TYPE_SLIDER;
        break;
      default:
    }
    return Type;
  }


  class ImageViewHolder extends RecyclerView.ViewHolder {

    private ImageView imageView;

    ImageViewHolder(@NonNull View itemView) {
      super(itemView);
      imageView = itemView.findViewById(R.id.iv_image_precautions);
    }

    private void setDetails(Precautions precautions) {
      Glide.with(context).load(precautions.getData().get(0)).into(imageView);
    }

  }

  class TextViewHolder extends RecyclerView.ViewHolder {

    private TextView txtTitle, txtDetails;

    TextViewHolder(@NonNull View itemView) {
      super(itemView);
      txtTitle = itemView.findViewById(R.id.tv_title_precautions);
      txtDetails = itemView.findViewById(R.id.tv_precautions);
    }

    private void setDetails(Precautions precautions) {
      txtTitle.setText(precautions.getTitle());
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < precautions.getData().size(); i++) {
        stringBuilder.append(precautions.getData().get(i));
      }
      txtDetails.setText(stringBuilder.toString());
    }
  }

  class VideoViewHolder extends RecyclerView.ViewHolder {

    private YouTubePlayerView youTubePlayerView;
    private TextView txtTitleVideo;

    VideoViewHolder(@NonNull View itemView) {
      super(itemView);
      txtTitleVideo = itemView.findViewById(R.id.tv_title_precautions_video);
      youTubePlayerView = itemView.findViewById(R.id.youtube_player_view_precautions);
      precautionsFragment.getLifecycle().addObserver(youTubePlayerView);
    }

    private void setDetails(Precautions precautions) {
      txtTitleVideo.setText(precautions.getTitle());
      youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
        @Override
        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
          String videoId = precautions.getData().get(0);
          youTubePlayer.cueVideo(videoId, 0f);
        }
      });
    }
  }

  class SliderViewHolder extends RecyclerView.ViewHolder {

    private SliderLayout mDemoSlider;
    private TextView txtTitleSlider;

    SliderViewHolder(@NonNull View itemView) {
      super(itemView);
      txtTitleSlider = itemView.findViewById(R.id.tv_title_precautions_slider);
      mDemoSlider = itemView.findViewById(R.id.slider_precautions);
    }


    private void setDetails(Precautions precautions) {
      txtTitleSlider.setText(precautions.getTitle());
      mDemoSlider.removeAllSliders();
      for (int i = 0; i < precautions.getData().size(); i++) {
        TextSliderView sliderView = new TextSliderView(context);
        // if you want show image only / without description text use DefaultSliderView instead
        // initialize SliderLayout
        sliderView
            .image(precautions.getData().get(i))
            .description(precautions.getDataTitle().get(i))
            .setRequestOption(new RequestOptions().fitCenter())
            .setProgressBarVisible(true)
            .setOnSliderClickListener(
                slider -> Toast.makeText(context, slider.getBundle().getString("extra") + "",
                    Toast.LENGTH_SHORT).show());
        //add your extra information
        sliderView.bundle(new Bundle());
        sliderView.getBundle().putString("extra", precautions.getDataTitle().get(i));
        mDemoSlider.addSlider(sliderView);
      }

      // set Slider Transition Animation
      // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
      mDemoSlider.setPresetTransformer(Transformer.Stack);
      mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);
//      mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
      mDemoSlider.setCustomAnimation(new DescriptionAnimation());
      mDemoSlider.setDuration(3000);
      mDemoSlider.stopCyclingWhenTouch(true);
    }
  }
}
