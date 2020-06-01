package ru.alexbox.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import ru.alexbox.weatherapp.room.App;
import ru.alexbox.weatherapp.room.CityDao;
import ru.alexbox.weatherapp.room.CitySource;

public class HistoryActivity extends AppCompatActivity {

    private ThirdActivityRecycler adapter;
    private CitySource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initRecycler();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.HistoryListRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        CityDao cityDao = App
                .getInstance()
                .getCityDao();
        source = new CitySource(cityDao);

        adapter = new ThirdActivityRecycler(this, source);
        recyclerView.setAdapter(adapter);

    }
}