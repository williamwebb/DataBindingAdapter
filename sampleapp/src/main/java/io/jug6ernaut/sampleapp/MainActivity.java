package io.jug6ernaut.sampleapp;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.jug6ernaut.sampleapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
    binding.setModel(makeModel());
  }

  private static Model makeModel() {
    List<RowModel> rows = new ArrayList<>();
    for(int x=0;x<3;x++) rows.add(new RowModel("Model: " + x));

    return new Model(rows);
  }

  public static class RowModel {
    public final String text;
    public RowModel(String text) {
      this.text = text;
    }
  }

  public static class Model {
    public final List<RowModel> rows;
    public Model(List<RowModel> rows) {
      this.rows = rows;
    }
  }
}
