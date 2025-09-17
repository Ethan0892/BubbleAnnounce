package com.bubblecraft.bubbleannounce;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BubbleAnnounce extends JavaPlugin {
    private FileConfiguration config;
    private final List<Announcement> announcements = new ArrayList<>();
    private final List<BukkitTask> scheduledTasks = new ArrayList<>();
    private final MiniMessage mini = MiniMessage.miniMessage();

    // Global settings
    private String globalPrefix = "";
    private String defaultMode = "auto"; // legacy|minimessage|auto
    private String defaultType = "chat"; // chat|actionbar|title
    private int defaultFadeIn = 10, defaultStay = 60, defaultFadeOut = 10;
    private boolean hasPapi = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.config = getConfig();
    this.hasPapi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
        loadAnnouncements();
        scheduleAnnouncements();
    }

    @Override
    public void onDisable() {
        for (BukkitTask t : scheduledTasks) t.cancel();
        scheduledTasks.clear();
    }

    private void loadAnnouncements() {
        announcements.clear();
        // read globals
        ConfigurationSection settings = config.getConfigurationSection("settings");
        if (settings != null) {
            globalPrefix = settings.getString("prefix", "");
            defaultMode = settings.getString("defaultMode", "auto");
            defaultType = settings.getString("defaultType", "chat");
            ConfigurationSection tt = settings.getConfigurationSection("defaultTitleTimings");
            if (tt != null) {
                defaultFadeIn = tt.getInt("fadeIn", 10);
                defaultStay = tt.getInt("stay", 60);
                defaultFadeOut = tt.getInt("fadeOut", 10);
            }
        }

        ConfigurationSection root = config.getConfigurationSection("announcements");
        if (root == null) return;
        for (String key : root.getKeys(false)) {
            ConfigurationSection sec = root.getConfigurationSection(key);
            if (sec == null) continue;

            boolean enabled = sec.getBoolean("enabled", true);
            String mode = sec.getString("mode", defaultMode).toLowerCase(Locale.ROOT);
            String type = sec.getString("type", defaultType).toLowerCase(Locale.ROOT);
            int intervalMin = sec.getInt("interval", 0);
            List<String> messages = sec.getStringList("messages");
            List<String> commands = sec.getStringList("commands");
            boolean coins = sec.getBoolean("coinsGiveAll", false);
            boolean randomOrder = sec.getBoolean("randomOrder", false);
            int delayTicksBetweenLines = sec.getInt("delayTicksBetweenLines", 0);
            ConfigurationSection times = sec.getConfigurationSection("titleTimings");
            int fadeIn = times != null ? times.getInt("fadeIn", defaultFadeIn) : defaultFadeIn;
            int stay = times != null ? times.getInt("stay", defaultStay) : defaultStay;
            int fadeOut = times != null ? times.getInt("fadeOut", defaultFadeOut) : defaultFadeOut;

            announcements.add(new Announcement(key, enabled, mode, type, intervalMin, messages, commands, coins, randomOrder, delayTicksBetweenLines, fadeIn, stay, fadeOut));
        }
    }

    private void scheduleAnnouncements() {
        for (Announcement a : announcements) {
            if (!a.enabled || a.intervalMin <= 0) continue;
            long ticks = 20L * 60 * a.intervalMin;
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    runAnnouncement(a);
                }
            }.runTaskTimer(this, ticks, ticks);
            scheduledTasks.add(task);
        }
    }

    private void runAnnouncement(Announcement a) {
        // Messages with optional delay and random order
        if (a.messages != null && !a.messages.isEmpty()) {
            List<String> lines = new ArrayList<>(a.messages);
            if (a.randomOrder) Collections.shuffle(lines);
            int delay = Math.max(0, a.delayTicksBetweenLines);
            if (delay <= 0) {
                for (String msg : lines) {
                    deliverLine(a, msg);
                }
            } else {
                final int[] index = {0};
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (index[0] >= lines.size()) {
                            cancel();
                            return;
                        }
                        String msg = lines.get(index[0]++);
                        deliverLine(a, msg);
                        if (index[0] >= lines.size()) {
                            cancel();
                        }
                    }
                }.runTaskTimer(this, 0L, delay);
            }
        }

    // Commands
        if (a.commands != null) {
            for (String cmd : a.commands) {
                if (cmd != null && !cmd.isEmpty()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                }
            }
        }

        // Optional coins action
        if (a.coinsGiveAll) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "coins giveall 1");
        }
    }

    private void deliverLine(Announcement a, String msg) {
        String mode = a.mode;
        if ("auto".equals(mode)) {
            mode = looksLikeMiniMessage(msg) ? "minimessage" : "legacy";
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            String line = msg;
            if (hasPapi) {
                try { line = PlaceholderAPI.setPlaceholders(p, line); } catch (Throwable ignored) {}
            }
            String textToSend;
            if ("minimessage".equals(mode)) {
                String mm = convertBareHexToMiniMessage(line);
                Component comp = mini.deserialize(mm);
                textToSend = LegacyComponentSerializer.legacySection().serialize(comp);
            } else {
                String withHex = applyHexToLegacy(line);
                textToSend = ChatColor.translateAlternateColorCodes('&', withHex);
            }
            if (globalPrefix != null && !globalPrefix.isEmpty()) {
                textToSend = ChatColor.translateAlternateColorCodes('&', globalPrefix) + textToSend;
            }
            sendByTypeToPlayer(a, p, textToSend);
        }
    }

    private void sendByTypeToPlayer(Announcement a, Player p, String legacy) {
        switch (a.type) {
            case "actionbar":
                p.spigot().sendMessage(
                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                        net.md_5.bungee.api.chat.TextComponent.fromLegacyText(legacy)
                );
                break;
            case "title":
                String[] parts = legacy.split("\n", 2);
                String title = parts.length > 0 ? parts[0] : "";
                String sub = parts.length > 1 ? parts[1] : "";
                p.sendTitle(title, sub, a.fadeIn, a.stay, a.fadeOut);
                break;
            default:
                p.sendMessage(legacy);
        }
    }

    private void runAllAnnouncementsOnce() {
        for (Announcement a : announcements) {
            if (a.enabled) runAnnouncement(a);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("test")) {
            if (!sender.hasPermission("test.announce")) {
                sender.sendMessage(color(config.getString("messages.no_permission", "&cYou do not have permission to use this command.")));
                return true;
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("Announce")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "runannouncements");
                sender.sendMessage(color(config.getString("messages.announce_triggered", "&aAll announcements have been triggered!")));
            } else {
                sender.sendMessage(color(config.getString("messages.invalid_argument", "&cInvalid argument! Use &e/test Announce &cto trigger all announcements.")));
            }
            return true;
        }
        if (command.getName().equalsIgnoreCase("runannouncements")) {
            if (!sender.hasPermission("announce.run")) {
                sender.sendMessage(color(config.getString("messages.no_permission", "&cYou do not have permission to use this command.")));
                return true;
            }
            runAllAnnouncementsOnce();
            return true;
        }
        if (command.getName().equalsIgnoreCase("bubbleannounce")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("bubbleannounce.admin")) {
                    sender.sendMessage(color(config.getString("messages.no_permission", "&cYou do not have permission to use this command.")));
                    return true;
                }
                for (BukkitTask t : scheduledTasks) t.cancel();
                scheduledTasks.clear();
                reloadConfig();
                this.config = getConfig();
                this.hasPapi = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
                loadAnnouncements();
                scheduleAnnouncements();
                sender.sendMessage(color("&aBubbleAnnounce configuration reloaded."));
                return true;
            }
            sender.sendMessage(color("&eUsage: /bubbleannounce reload"));
            return true;
        }
        return false;
    }

    private String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input == null ? "" : input);
    }

    // Detect MiniMessage by looking for tag-like patterns such as <white> or </...>
    private boolean looksLikeMiniMessage(String s) {
        if (s == null) return false;
        return Pattern.compile("<[/a-zA-Z#].*?>").matcher(s).find();
    }

    // Convert occurrences of #RRGGBB or &#RRGGBB into the legacy §x§R§R§G§G§B§B sequence
    private String applyHexToLegacy(String input) {
        if (input == null) return "";
        Pattern hex = Pattern.compile("(?i)&?#([0-9a-f]{6})");
        Matcher m = hex.matcher(input);
        StringBuffer out = new StringBuffer();
        while (m.find()) {
            String hexStr = m.group(1).toUpperCase(Locale.ROOT);
            StringBuilder rep = new StringBuilder("§x");
            for (char c : hexStr.toCharArray()) {
                rep.append('§').append(c);
            }
            m.appendReplacement(out, Matcher.quoteReplacement(rep.toString()));
        }
        m.appendTail(out);
        return out.toString();
    }

    // Convert bare hex tokens like #RRGGBB into MiniMessage color tags <#RRGGBB>
    private String convertBareHexToMiniMessage(String input) {
        if (input == null) return "";
        return input.replaceAll("(?i)#([0-9a-f]{6})", "<$1>");
    }

    private static class Announcement {
        final String id;
        final boolean enabled;
        final String mode; // legacy|minimessage|auto
        final String type; // chat|actionbar|title
        final int intervalMin;
        final List<String> messages;
        final List<String> commands;
        final boolean coinsGiveAll;
        final boolean randomOrder;
        final int delayTicksBetweenLines;
        final int fadeIn, stay, fadeOut;

        Announcement(String id, boolean enabled, String mode, String type, int intervalMin,
                     List<String> messages, List<String> commands, boolean coinsGiveAll,
                     boolean randomOrder, int delayTicksBetweenLines, int fadeIn, int stay, int fadeOut) {
            this.id = id;
            this.enabled = enabled;
            this.mode = mode;
            this.type = type;
            this.intervalMin = intervalMin;
            this.messages = messages == null ? Collections.emptyList() : messages;
            this.commands = commands == null ? Collections.emptyList() : commands;
            this.coinsGiveAll = coinsGiveAll;
            this.randomOrder = randomOrder;
            this.delayTicksBetweenLines = delayTicksBetweenLines;
            this.fadeIn = fadeIn;
            this.stay = stay;
            this.fadeOut = fadeOut;
        }
    }
}
