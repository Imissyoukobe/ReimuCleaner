package com.example.reimucleaner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Random;

public class MessageManager {

    private final ConfigManager configManager;
    private final Random random;
    
    public MessageManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.random = new Random();
    }
    
    /**
     * 发送清理预警消息
     * @param warningTime 预警时间（秒）
     */
    public void sendWarningMessage(int warningTime) {
        if (!configManager.isEnableMessages()) {
            return;
        }
        
        List<String> warningMessages = configManager.getWarningMessages();
        if (warningMessages.isEmpty()) {
            return;
        }
        
        // 随机选择一条预警消息
        String message = warningMessages.get(random.nextInt(warningMessages.size()));
        
        // 替换占位符
        message = message.replace("{time}", String.valueOf(warningTime));
        
        // 发送消息给所有玩家
        Bukkit.broadcastMessage(formatMessage(message));
    }
    
    /**
     * 发送清理完成消息
     * @param itemCount 清理的物品数量
     */
    public void sendCompletionMessage(int itemCount) {
        if (!configManager.isEnableMessages()) {
            return;
        }
        
        List<String> completionMessages = configManager.getCompletionMessages();
        if (completionMessages.isEmpty()) {
            return;
        }
        
        // 随机选择一条完成消息
        String message = completionMessages.get(random.nextInt(completionMessages.size()));
        
        // 替换占位符
        message = message.replace("{count}", String.valueOf(itemCount));
        
        // 发送消息给所有玩家
        Bukkit.broadcastMessage(formatMessage(message));
    }
    
    /**
     * 格式化消息，添加前缀和颜色
     * @param message 原始消息
     * @return 格式化后的消息
     */
    private String formatMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', configManager.getPrefix() + message);
    }
}