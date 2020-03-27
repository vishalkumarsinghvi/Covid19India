package com.vishal.covid19india.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.vishal.covid19india.R;
import com.vishal.covid19india.model.Covid19.RawData.Raw_data;
import com.bumptech.glide.Glide;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import java.util.Objects;

public class RawDataPatientDetailsActivity extends AppCompatActivity implements
    View.OnClickListener {

  private Raw_data res;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_raw_data_patient_details);
    initUI();
  }

  private void initUI() {
    res = (Raw_data) Objects.requireNonNull(getIntent().getExtras()).getSerializable("data");
    ImageView ivPatient = findViewById(R.id.iv_patient_raw_data);
    TextView tvPatientNumber = findViewById(R.id.tv_patient_number_raw_data);
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
    ImageView ivBack = findViewById(R.id.iv_back_raw_data);
    if (!res.getGenderImageUrl().equals("")) {
      Glide.with(RawDataPatientDetailsActivity.this).load(res.getGenderImageUrl())
          .into(ivPatient);
    } else {
      Glide.with(RawDataPatientDetailsActivity.this).load(res.getUnKnownImageUrl())
          .into(ivPatient);
    }
    tvPatientNumber.setText("P ".concat(res.getPatientnumber()));
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
    ivBack.setOnClickListener(this);


  }

  @Override
  public void onClick(View view) {
    if (view.getId() == R.id.iv_back_raw_data) {
      onBackPressed();
    }
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
  }
}
