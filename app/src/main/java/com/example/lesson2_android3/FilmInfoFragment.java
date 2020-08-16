package com.example.lesson2_android3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lesson2_android3.data.GhibliService;
import com.example.lesson2_android3.model.Film;

import java.util.List;

public class FilmInfoFragment extends Fragment implements GhibliService.GhibliCallback {

    private TextView filmName, filmDeck,filmDirector,filmProducer,filmReleaseDate,filmScore;
    private Film models;
    //private ArrayList<Film> list;
    public FilmInfoFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("tag","onCreateView");
        Bundle bundle = this.getArguments();
        if (bundle != null){
            String id = bundle.getString("id");
            App.ghibliService.getFilmById(id, this);
        }

        return inflater.inflate(R.layout.fragment_film_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("tag","onViewCreated");
        filmName = view.findViewById(R.id.info_filmName);
        filmDeck = view.findViewById(R.id.info_desc);
        filmDirector = view.findViewById(R.id.info_director);
        filmProducer = view.findViewById(R.id.info_producer);
        filmReleaseDate = view.findViewById(R.id.info_releaseDate);
        filmScore = view.findViewById(R.id.info_rtScore);
    }

    @Override
    public void onSuccess(List<Film> films) {
    }

    @Override
    public void onResponseFilm(Film film) {
        filmName.setText(film.getTitle());
        filmDeck.setText(film.getDescription());
        filmDirector.setText(film.getDirector());
        filmProducer.setText(film.getProducer());
        filmReleaseDate.setText(film.getReleaseDate());
        filmScore.setText(film.getRtScore());


    }

    @Override
    public void onFailure(Exception exception) {

    }
}
