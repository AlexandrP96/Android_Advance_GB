package ru.alexbox.weatherapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import ru.alexbox.weatherapp.R;

public class GalleryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
}
