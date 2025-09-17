# BubbleAnnounce

BubbleAnnounce is a highly configurable Minecraft server plugin for automated and manual announcements. Designed for Bubblecraft, it supports advanced formatting, scheduling, and integration with PlaceholderAPI. Easily manage server-wide messages, reminders, and events with flexible configuration options.

## Features
- **Configurable Announcements:** Define unlimited announcements with custom messages, intervals, and display types (chat, actionbar, title).
- **Advanced Formatting:** Supports legacy color codes, MiniMessage, and hex colors.
- **Scheduling:** Automatically schedule announcements at set intervals, or trigger them manually via commands.
- **PlaceholderAPI Support:** Dynamic placeholders for player and server data.
- **Admin Commands:** Reload config, trigger announcements, and manage settings in-game.
- **Permission System:** Restrict commands and announcements to specific user groups.
- **Multi-line & Randomized Messages:** Send multi-line announcements and shuffle message order.

## Example Commands
- `/test <Announce>` — Triggers all announcements (requires `test.announce` permission)
- `/runannouncements` — Runs all announcements (requires `announce.run` permission)
- `/bubbleannounce reload` — Reloads the plugin configuration (requires `bubbleannounce.admin` permission)

## Quick Start
1. Place the plugin JAR in your server's `plugins` folder.
2. Start the server to generate default config files.
3. Edit `config.yml` and `plugin.yml` in `src/main/resources` to customize announcements and settings.
4. Use admin commands to reload or test announcements in-game.

## Configuration
See `config.yml` for detailed options:
- Enable/disable announcements
- Set message mode (`legacy`, `minimessage`, `auto`)
- Choose display type (`chat`, `actionbar`, `title`)
- Schedule intervals, randomize order, and more

## Requirements
- Minecraft 1.21+
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) (optional, for placeholders)

---
For more details, see the example config files and source code.
