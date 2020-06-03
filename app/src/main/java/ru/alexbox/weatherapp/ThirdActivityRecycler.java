package ru.alexbox.weatherapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.alexbox.weatherapp.room.City;
import ru.alexbox.weatherapp.room.CitySource;

public class ThirdActivityRecycler extends RecyclerView.Adapter<ThirdActivityRecycler.ViewHolder>{

    private Activity activity;
    private CitySource citySource;
    private long menuPosition;

    public ThirdActivityRecycler(Activity activity, CitySource citySource) {
        this.activity = activity;
        this.citySource = citySource;
    }

    @NonNull
    @Override
    public ThirdActivityRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ThirdActivityRecycler.ViewHolder holder, int position) {
        List<City> city = citySource.getCities();
        holder.temperature.setText("+10");
        holder.city.setText("Moscow");
    }

    @Override
    public int getItemCount() {
        return (int) citySource.getCountCities();
    }

    public long getMenuPosition() {
        return menuPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView city;
        TextView temperature;
        View cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView;
            city.findViewById(R.id.CityNameHistory);
            temperature.findViewById(R.id.CityTempHistory);
        }
    }
}
