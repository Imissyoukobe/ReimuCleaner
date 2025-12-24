package com.example.reimucleaner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitTask;

public class CleanerTask {

    private final ReimuCleanerPlugin plugin;
    private final ConfigManager configManager;
    private final MessageManager messageManager;
    private BukkitTask mainTask;
    private BukkitTask warningTask;
    
    public CleanerTask(ReimuCleanerPlugin plugin, ConfigManager configManager, MessageManager messageManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.messageManager = messageManager;
    }
    
    /**
     * 启动定时清理任务
     */
    public void startTask() {
        int interval = configManager.getCleanInterval() * 20; // 转换为tick
        int warningTime = configManager.getWarningTime();
        
        // 创建主清理任务
        mainTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            // 启动预警任务
            warningTask = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                messageManager.sendWarningMessage(warningTime);
                
                // 等待预警时间后执行清理
                Bukkit.getScheduler().runTaskLater(plugin, this::cleanItems, warningTime * 20L);
            }, (interval - (warningTime * 20)));
        }, interval, interval);
        
        plugin.getLogger().info("定时清理任务已启动，清理间隔：" + configManager.getCleanInterval() + "秒");
    }
    
    /**
     * 清理所有掉落物
     */
    private void cleanItems() {
        int totalItems = 0;
        
        // 遍历所有世界
        for (org.bukkit.World world : Bukkit.getWorlds()) {
            // 获取所有掉落物实体
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                    totalItems++;
                }
            }
        }
        
        // 发送清理完成消息
        messageManager.sendCompletionMessage(totalItems);
        
        plugin.getLogger().info("已清理 " + totalItems + " 个掉落物");
    }
    
    /**
     * 取消所有任务
     */
    public void cancelTask() {
        if (mainTask != null && !mainTask.isCancelled()) {
            mainTask.cancel();
        }
        
        if (warningTask != null && !warningTask.isCancelled()) {
            warningTask.cancel();
        }
        
        plugin.getLogger().info("定时清理任务已取消");
    }
}