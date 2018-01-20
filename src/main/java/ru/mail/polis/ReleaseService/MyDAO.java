package ru.mail.polis.ReleaseService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Ololo on 16.10.2017.
 */
public interface MyDAO {
    @NotNull
    byte[] get(@NotNull String key) throws ExecutionException;

    void upsert(@NotNull String key, @NotNull byte[] value) throws IllegalArgumentException, IOException;

    void delete(@NotNull String key) throws IllegalArgumentException, IOException;

}
