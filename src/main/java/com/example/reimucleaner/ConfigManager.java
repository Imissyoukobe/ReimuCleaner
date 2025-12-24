package com.example.reimucleaner;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final ReimuCleanerPlugin plugin;
    private FileConfiguration config;
    
    // 默认配置值
    private static final int DEFAULT_CLEAN_INTERVAL = 300; // 5分钟
    private static final int DEFAULT_WARNING_TIME = 10;    // 10秒
    private static final boolean DEFAULT_ENABLE_MESSAGES = true;
    private static final String DEFAULT_PREFIX = "§c[博丽灵梦] §f";
    
    // 配置项
    private int cleanInterval;
    private int warningTime;
    private boolean enableMessages;
    private String prefix;
    private List<String> warningMessages;
    private List<String> completionMessages;
    
    public ConfigManager(ReimuCleanerPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void loadConfig() {
        // 保存默认配置文件
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
        
        // 读取配置项
        cleanInterval = config.getInt("clean-interval", DEFAULT_CLEAN_INTERVAL);
        warningTime = config.getInt("warning-time", DEFAULT_WARNING_TIME);
        enableMessages = config.getBoolean("enable-messages", DEFAULT_ENABLE_MESSAGES);
        prefix = config.getString("prefix", DEFAULT_PREFIX);
        
        // 初始化默认消息列表
        initializeDefaultMessages();
        
        // 读取消息列表
        warningMessages = config.getStringList("messages.warning");
        completionMessages = config.getStringList("messages.completion");
        
        // 如果消息列表为空，使用默认消息
        if (warningMessages.isEmpty()) {
            warningMessages = getDefaultWarningMessages();
        }
        if (completionMessages.isEmpty()) {
            completionMessages = getDefaultCompletionMessages();
        }
        
        // 保存配置（确保默认值被写入）
        saveConfig();
    }
    
    private void initializeDefaultMessages() {
        // 如果配置中没有消息部分，添加默认消息
        if (!config.contains("messages.warning")) {
            config.set("messages.warning", getDefaultWarningMessages());
        }
        if (!config.contains("messages.completion")) {
            config.set("messages.completion", getDefaultCompletionMessages());
        }
    }
    
    private List<String> getDefaultWarningMessages() {
        return List.of(
            "哼，地上的垃圾越来越多了，再等一下就全部清理掉哦？",
            "真是的，大家就不能好好收拾自己的东西吗？马上就要清理了哦。",
            "哎呀呀，这里变得这么脏，作为巫女可不能不管呢。{time}秒后清理掉落物～",
            "喂喂，地上的东西太多了，会影响神社的整洁度哦。{time}秒后清理～",
            "啊～真是的，又要我来收拾残局吗？{time}秒后清理所有掉落物！",
            "作为博丽神社的巫女，保持整洁是我的职责。{time}秒后清理地面～",
            "哼，这些掉落的物品真是碍眼。{time}秒后全部清理掉！",
            "真是的，每次都要我来打扫... {time}秒后清理掉落物哦。"
        );
    }
    
    private List<String> getDefaultCompletionMessages() {
        return List.of(
            "呼，终于清理干净了。真是的，下次要自己收拾好啊。",
            "清理完毕！一共清除了{count}个物品。保持神社的整洁可是很重要的哦？",
            "啊～清爽多了。作为感谢，下次可以来博丽神社喝杯茶哦？",
            "哼，总算打扫完了。{count}个物品被清理掉了。下次要注意哦！",
            "清理完成！{count}个物品已经被处理掉了。保持整洁才是好孩子～",
            "呼，打扫完了。{count}个物品，真是的，大家就不能小心一点吗？",
            "完成！{count}个物品已清理。博丽神社的巫女可是很忙碌的，下次请自己收拾哦～",
            "哎呀，终于干净了。{count}个物品被清理掉了。保持整洁才能迎来好客人呢。"
        );
    }
    
    public void saveConfig() {
        plugin.saveConfig();
    }
    
    // Getter方法
    public int getCleanInterval() {
        return cleanInterval;
    }
    
    public int getWarningTime() {
        return warningTime;
    }
    
    public boolean isEnableMessages() {
        return enableMessages;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public List<String> getWarningMessages() {
        return warningMessages;
    }
    
    public List<String> getCompletionMessages() {
        return completionMessages;
    }
}