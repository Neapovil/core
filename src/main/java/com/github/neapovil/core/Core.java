package com.github.neapovil.core;

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
}
