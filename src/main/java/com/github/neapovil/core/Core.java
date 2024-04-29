package com.github.neapovil.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;

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
                this.getServer().broadcast(Component.text("File (%s) loaded".formatted(path)));
                return string;
            }
            catch (IOException e)
            {
                final String message = "Unable to load file: " + path;
                final String message1 = "Exception: " + e.getMessage();
                this.getLogger().severe(message);
                this.getLogger().severe(message1);
                this.getServer().broadcast(Component.text(message, Style.empty().color(NamedTextColor.RED)), "core.broadcast");
                this.getServer().broadcast(Component.text(message1, Style.empty().color(NamedTextColor.RED)));
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
                this.getServer().broadcast(Component.text("File (%s) changes saved".formatted(path)));
            }
            catch (IOException e)
            {
                final String message = "Unable to save changes for file: " + path;
                final String message1 = "Exception: " + e.getMessage();
                this.getLogger().severe(message);
                this.getLogger().severe(message1);
                this.getServer().broadcast(Component.text(message, Style.empty().color(NamedTextColor.RED)), "core.broadcast");
                this.getServer().broadcast(Component.text(message1, Style.empty().color(NamedTextColor.RED)));
                throw new CompletionException(e);
            }
        });
    }
}
