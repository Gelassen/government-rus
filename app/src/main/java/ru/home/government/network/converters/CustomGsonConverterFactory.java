package ru.home.government.network.converters;

import androidx.annotation.NonNull;

import com.dropbox.android.external.store4.FetcherResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public final class CustomGsonConverterFactory extends Converter.Factory {
    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static CustomGsonConverterFactory create() {
        return create(new Gson());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static CustomGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        GsonBuilder builder = gson.newBuilder();
        builder.registerTypeAdapter(FetcherResult.class, null);
        return new CustomGsonConverterFactory(builder.create());
    }

    private final Gson gson;

    private CustomGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NonNull Type type,
                                                            @NonNull Annotation[] annotations,
                                                            @NonNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(@NonNull Type type,
                                                          @NonNull Annotation[] parameterAnnotations,
                                                          @NonNull Annotation[] methodAnnotations,
                                                          @NonNull Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(gson, adapter);
    }
}
