package ru.alexbox.weatherapp.ui.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.alexbox.weatherapp.ListAdapter;
import ru.alexbox.weatherapp.R;

public class GalleryFragment extends Fragment {

    private ListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        String[] data = getResources().getStringArray(R.array.cities);
        initList(view, data);
        return view;
    }

    private void initList(View view, String[] data) {

        RecyclerView recyclerView = view.findViewById(R.id.RecyclerCity);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        adapter = new ListAdapter(initData(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<String> initData() {
        List<String> list = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            list.add(String.format("Element %d", i));
        }
        return list;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_context) {
            adapter.addItem(String.format("New element %d", adapter.getItemCount()));
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
