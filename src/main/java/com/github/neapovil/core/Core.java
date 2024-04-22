package com.github.neapovil.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin
{
    private static Core instance;

    @Override
    public void onEnable()
    {
        instance = this;
    }

    public static Core instance()
    {
        return instance;
    }

    public CompletableFuture<String> loadResource(JavaPlugin plugin, Path path)
    {
        return CompletableFuture.supplyAsync(() -> {
            plugin.saveResource(path.getFileName().toString(), false);
            try
            {
                final String string = Files.readString(path);
                return string;
            }
            catch (IOException e)
            {
                throw new CompletionException(e);
            }
        });
    }

    public CompletableFuture<Void> saveResource(Path path, String string)
    {
        return CompletableFuture.runAsync(() -> {
            try
            {
                Files.write(path, string.getBytes());
            }
            catch (IOException e)
            {
                throw new CompletionException(e);
            }
        });
    }
}
