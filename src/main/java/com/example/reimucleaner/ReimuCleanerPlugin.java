package com.example.reimucleaner;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class ReimuCleanerPlugin extends JavaPlugin {

    private static ReimuCleanerPlugin instance;
    private ConfigManager configManager;
    private MessageManager messageManager;
    private CleanerTask cleanerTask;
    private final Logger logger = getLogger();

    @Override
    public void onEnable() {
        instance = this;
        
        // 初始化配置管理器
        configManager = new ConfigManager(this);
        configManager.loadConfig();
        
        // 初始化消息管理器
        messageManager = new MessageManager(configManager);
        
        // 初始化清理任务
        cleanerTask = new CleanerTask(this, configManager, messageManager);
        cleanerTask.startTask();
        
        logger.info("博丽灵梦掉落物清理插件已启用！");
        logger.info("由博丽神社的巫女负责保持服务器整洁～");
    }

    @Override
    public void onDisable() {
        if (cleanerTask != null) {
            cleanerTask.cancelTask();
        }
        
        logger.info("博丽灵梦掉落物清理插件已禁用。");
        logger.info("灵梦回去打扫神社了，下次再见～");
    }

    public static ReimuCleanerPlugin getInstance() {
        return instance;
    }
}