package ru.alexbox.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ru.alexbox.weatherapp.parcel.Constants;
import ru.alexbox.weatherapp.parcel.Parcel;

public class CityActivity extends AppCompatActivity implements Constants {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        String[] data = getResources().getStringArray(R.array.cities);
        initList(data);
    }

    private void initList(String[] data) {
        final RecyclerView recyclerView = findViewById(R.id.CityListRecycler);
        recyclerView.setHasFixedSize(true);

        LayoutManager(recyclerView);

        final SecondActivityRecycler adapter = new SecondActivityRecycler(data);
        recyclerView.setAdapter(adapter);
        onRecyclerClick(adapter);
    }

    private void LayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void onRecyclerClick(SecondActivityRecycler adapter) {
        adapter.SetOnItemClickListener((view, position) -> {
            TextView textView = findViewById(R.id.ChosenCity);
            if (position == 0) {
                textView.setText(R.string.city_tokyo);
                CreateIntent();
            }
            if (position == 1) {
                textView.setText(R.string.city_new_york);
                CreateIntent();
            }
            if (position == 2) {
                textView.setText(R.string.city_odessa);
                CreateIntent();
            }
            if (position == 3) {
                textView.setText(R.string.city_kiev);
                CreateIntent();
            }
        });
    }

    private void CreateIntent() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(PARCEL, createParcel());
        setResult(RESULT_OK, intent);

        finish();
    }

    private Parcel createParcel() {
        TextView textView = findViewById(R.id.ChosenCity);
        Parcel parcel = new Parcel();

        parcel.currentCity = textView.getText().toString();

        if (parcel.currentCity.equals("")) {
            textView.setText(R.string.DefaultValue);
            parcel.currentCity = textView.getText().toString();
        }
        return parcel;
    }
}