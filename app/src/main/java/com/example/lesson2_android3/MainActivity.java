package com.example.lesson2_android3;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson2_android3.data.GhibliService;
import com.example.lesson2_android3.interfac.OnItemFilmClickListener;
import com.example.lesson2_android3.model.Film;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GhibliService.GhibliCallback {

    private RecyclerView recyclerView;
    private FilmAdapter adapter;
    private List<Film> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.ghibliService.getListFilms(this);
    }

    private void createRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FilmAdapter(list);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemFilmClickListener() {
            @Override
            public void onItemClick(int position) {
                //Открыл фрагмент фильмИнфо
                Log.d("tag","onItemClick");
                Bundle bundle = new Bundle();
                bundle.putString("id", list.get(position).getId());
                FilmInfoFragment fragment = new FilmInfoFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().add(R.id.fr_container,fragment).addToBackStack(null).commit();

            }
        });
    }
    @Override
    public void onSuccess(List<Film> films) {
        Log.d("tag","id");
        list = films;
        createRecyclerView();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFilm(Film film) {

    }

    @Override
    public void onFailure(Exception exception) {
     Log.d("check", "fail") ;
    }
}
