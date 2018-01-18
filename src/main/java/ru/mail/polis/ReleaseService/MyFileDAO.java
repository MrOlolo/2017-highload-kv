package ru.mail.polis.ReleaseService;

import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Ololo on 16.10.2017.
 */
public class MyFileDAO implements MyDAO {
    @NotNull
    private final File dir;
    private final Map<String, byte[]> cache;

    public MyFileDAO(@NotNull final File dir) {
        this.dir = dir;
        cache = new HashMap<>(2000);
    }

    @NotNull
    private  File getFile(@NotNull final String key) {
        return new File(dir, key);
    }

    @NotNull
    @Override
    public byte[] get(@NotNull final String key) throws NoSuchElementException, IllegalArgumentException, IOException {
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        final Path path = Paths.get(dir.toString(), key);
        if(!(Files.exists(path))) {
            throw new NoSuchElementException();
        }

        final byte[] value = Files.readAllBytes(path);
        if(cache.size() == 2000) {
            cache.remove(cache.keySet().iterator().next());
        }
        cache.put(key, value);
        return value;
    }

    @Override
    public void upsert(@NotNull final String key, @NotNull byte[] value) throws IllegalArgumentException, IOException {
        try(OutputStream os = new FileOutputStream(getFile(key))) {
            os.write(value);
        }
        cache.remove(key);
    }

    @Override
    public void delete(@NotNull final String key) throws IllegalArgumentException, IOException {
        getFile(key).delete();
        cache.remove(key);
    }

}
