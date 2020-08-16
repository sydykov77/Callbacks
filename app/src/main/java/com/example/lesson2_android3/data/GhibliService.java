package com.example.lesson2_android3.data;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.example.lesson2_android3.model.Film;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class GhibliService {

    public static List<Film> list = new ArrayList<>();

    public static Film getFilmNamePosition(int position) {
        return list.get(position);
    }

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://ghibliapi.herokuapp.com/")
            .build();

    GhibliApi service = retrofit.create(GhibliApi.class);

    public void getFilmById(String id, final GhibliCallback callback) {
        Call<Film> call = service.getFilmById(id);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                if (response.isSuccessful() && response.body() != null){
                    callback.onResponseFilm(response.body());
                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                callback.onFailure(new NetworkErrorException());
            }
        });
    }

    public void getListFilms(final GhibliCallback callback) {
        Call<List<Film>> call = service.getFilms();
        call.enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(Call<List<Film>> call, Response<List<Film>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body());
                        Log.d("tag", response.body().toString());
                    } else {
                        Log.d("tag", "response body is null");
                        callback.onFailure(new NetworkErrorException());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Film>> call, Throwable t) {
                Log.d("tag", "Error");
                callback.onFailure(new Exception());
            }
        });

    }

    public interface GhibliCallback {
        void onSuccess(List<Film> films);

        void onResponseFilm(Film film);

        void onFailure(Exception exception);
    }

    public interface GhibliApi {
        @GET("films/")
        Call<List<Film>> getFilms();

        @GET("films/{id}")
        Call<Film> getFilmById(@Path("id") String filmId);
    }
}
