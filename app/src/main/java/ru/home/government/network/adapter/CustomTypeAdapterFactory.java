package ru.home.government.network.adapter;

import android.util.Log;

import com.dropbox.android.external.store4.FetcherResult;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;

import ru.home.government.App;

public class CustomTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!FetcherResult.class.isAssignableFrom(type.getRawType())) return null;
        return (TypeAdapter<T>) new FetcherResultAdapter<T>(gson, type);
    }

    public static class FetcherResultAdapter<T> extends TypeAdapter<FetcherResult<T>> {

        private final Gson gson;
        private final TypeToken<T> type;

        public FetcherResultAdapter(Gson gson, TypeToken<T> type) {
            super();
            this.gson = gson;
            this.type = type;
        }

        @Override
        public void write(JsonWriter out, FetcherResult<T> value) throws IOException {
            Log.d(App.TAG, "[start] FetcherResultAdapter.write");
        }

        @Override
        public FetcherResult<T> read(JsonReader in) throws IOException {
            Log.d(App.TAG, "[start] FetcherResultAdapter.read");
            FetcherResult<T> response = null;
            try {
                Type responseType = getParameterUpperBound(0, (ParameterizedType) type.getType());
                response = new FetcherResult.Data<>(gson.fromJson(in, responseType));
            } catch (Exception ex) {
                Log.e(App.TAG, "Failed to parse FetchResult response", ex);
                response = new FetcherResult.Error.Exception<>(ex);
            }
            return response;
        }
    }

    static Type getParameterUpperBound(int index, ParameterizedType type) {
        Type[] types = type.getActualTypeArguments();
        if (index < 0 || index >= types.length) {
            throw new IllegalArgumentException(
                    "Index " + index + " not in range [0," + types.length + ") for " + type);
        }
        Type paramType = types[index];
        if (paramType instanceof WildcardType) {
            return ((WildcardType) paramType).getUpperBounds()[0];
        }
        return paramType;
    }

}
