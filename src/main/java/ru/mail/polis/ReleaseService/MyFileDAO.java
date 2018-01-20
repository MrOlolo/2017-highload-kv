package ru.mail.polis.ReleaseService;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.*;
import java.util.NoSuchElementException;

/**
 * Created by Ololo on 16.10.2017.
 */
public class MyFileDAO implements MyDAO {
    @NotNull
    private final File dir;

    private final Cache<String, byte[]> cache;

    public MyFileDAO(@NotNull final File dir) {
        this.dir = dir;
        cache = CacheBuilder.newBuilder()
                .maximumSize(2000)
                .build();
    }

    @NotNull
    private File getFile(@NotNull final String key) {
        return new File(dir, key);
    }

    @NotNull
    @Override
    public byte[] get(@NotNull final String key) throws IllegalArgumentException, IOException {
        byte[] value = cache.getIfPresent(key);
        if (value != null) {
            return value;
        }

        final Path path = Paths.get(dir.toString(), key);
        if (!(Files.exists(path))) {
            throw new NoSuchElementException();
        }

        value = Files.readAllBytes(path);
        cache.put(key, value);
        return value;
    }

    @Override
    public void upsert(@NotNull final String key, @NotNull byte[] value) throws IllegalArgumentException, IOException {
        try (OutputStream os = new FileOutputStream(getFile(key))) {
            os.write(value);
        }
        cache.invalidate(key);
    }

    @Override
    public void delete(@NotNull final String key) throws IllegalArgumentException, IOException {
        getFile(key).delete();
        cache.invalidate(key);
    }

}
