package com.example.indevelUpChallenge.utils;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class Converters {


    @TypeConverter
    public static List<String> stringToGenreList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {}.getType();

        return new Gson().fromJson(data, listType);
    }

    @TypeConverter
    public static String genreListToString(List<String> genreList) {
        return new Gson().toJson(genreList);
    }



}
