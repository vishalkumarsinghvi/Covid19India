package com.vishal.covid19india.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.Covid19.RawData.Raw_data;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import java.util.Objects;

public class RawDataPatientDetailsActivity extends AppCompatActivity {

  private Raw_data res;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_raw_data_patient_details);
    initUI();
  }

  private void initUI() {
    if (getIntent().getExtras() != null) {
      res = (Raw_data) getIntent().getExtras().getSerializable("data");
    }
    final Toolbar toolbar = findViewById(R.id.anim_toolbar);
    setSupportActionBar(toolbar);
    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    toolbar.setNavigationIcon(R.drawable.ic_back_white);
    AppBarLayout appBarLayout = findViewById(R.id.appbar);
    CollapsingToolbarLayout mCollapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
    TextView tvStatePatientNumber = findViewById(R.id.tv_state_patient_number_raw_data);
    TextView tvPatientGender = findViewById(R.id.tv_gender_raw_data);
    TextView tvPatientAge = findViewById(R.id.tv_age_raw_data);
    TextView tvPatientNationality = findViewById(R.id.tv_nationality_raw_data);
    TextView tvPatientState = findViewById(R.id.tv_state_raw_data);
    TextView tvPatientCity = findViewById(R.id.tv_city_raw_data);
    TextView tvPatientCurrentStatus = findViewById(R.id.tv_status_raw_data);
    TextView tvPatientReportedOn = findViewById(R.id.tv_reported_on_raw_data);
    TextView tvPatientNotes = findViewById(R.id.tv_notes_raw_data);
    TextView tvPatientSources = findViewById(R.id.tv_source_one_raw_data);
    ImageView header = findViewById(R.id.header);
    if (!res.getGenderImageUrl().equals("")) {
      Glide.with(RawDataPatientDetailsActivity.this).load(res.getGenderImageUrl())
          .into(header);
    } else {
      Glide.with(RawDataPatientDetailsActivity.this).load(res.getUnKnownImageUrl())
          .into(header);
    }
    if (res != null) {
      mCollapsingToolbarLayout.setTitle("P ".concat(res.getPatientnumber()));
      tvStatePatientNumber
          .setText(
              tvStatePatientNumber.getText().toString().concat(": ")
                  .concat(res.getStatepatientnumber()));
      tvPatientGender
          .setText(tvPatientGender.getText().toString().concat(": ").concat(res.getGender()));
      tvPatientAge
          .setText(tvPatientAge.getText().toString().concat(": ").concat(res.getAgebracket()));
      tvPatientNationality
          .setText(
              tvPatientNationality.getText().toString().concat(": ").concat(res.getNationality()));
      tvPatientState
          .setText(tvPatientState.getText().toString().concat(": ").concat(res.getDetectedstate()));
      tvPatientCity
          .setText(tvPatientCity.getText().toString().concat(": ").concat(res.getDetectedcity()));
      tvPatientCurrentStatus.setText(
          tvPatientCurrentStatus.getText().toString().concat(": ").concat(res.getCurrentstatus()));
      tvPatientReportedOn
          .setText(
              tvPatientReportedOn.getText().toString().concat(": ").concat(res.getDateannounced()));
      tvPatientNotes
          .setText(tvPatientNotes.getText().toString().concat(": ").concat(res.getNotes()));
      tvPatientSources
          .setText(
              tvPatientSources.getText().toString()
                  .concat(res.getSource1())
                  .concat(res.getSource2())
                  .concat(res.getSource3()));

      Linkify.addLinks(tvPatientSources, Linkify.WEB_URLS);
    }


  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }
}
