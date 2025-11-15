# ✅ BubbleAnnounce v1.1.0 - Build Complete!

## 📦 Build Results

**Status:** ✅ **BUILD SUCCESSFUL**
- **JAR File:** `build/libs/BubbleAnnounce.jar` (12.8 KB)
- **Java Version:** JDK 21.0.3 LTS
- **Gradle:** 8.10.2
- **Build Time:** ~33 seconds

---

## 🚀 Deployment Instructions

### Step 1: Copy JAR to Server
```powershell
Copy-Item "build/libs/BubbleAnnounce.jar" "C:\path\to\server\plugins\"
```

### Step 2: Restart Server
```
/reload
```
Or restart the server completely.

### Step 3: Configure
The plugin will auto-generate configuration files:
- `plugins/BubbleAnnounce/config.yml` - Main configuration
- `plugins/BubbleAnnounce/plugin.yml` - Already in JAR

### Step 4: Customize & Reload
Edit `config.yml` in the plugins folder, then run:
```
/bubbleannounce reload
```

---

## 📋 What's Included in v1.1.0

✅ **Paper/Purpur 1.21.1 Support**
- Uses official Paper API instead of Spigot
- Compatible with Paper, Purpur, and Spigot servers

✅ **User-Friendly Configuration**
- Comprehensive inline documentation
- Examples for all settings
- Clear section headers
- Text formatting guide included

✅ **Modern Build System**
- Gradle 8.10.2
- Java 17+ support
- Clean dependency management

---

## 🎮 Available Commands

| Command | Permission | Purpose |
|---------|-----------|---------|
| `/test Announce` | `test.announce` | Test announcements |
| `/runannouncements` | `announce.run` | Trigger manually |
| `/bubbleannounce reload` | `bubbleannounce.admin` | Reload config |

---

## 🔧 System Environment

**JAVA_HOME** has been set to: `C:\jdk-21.0.3+9`

To persist this across sessions, add to system environment variables:
- Variable: `JAVA_HOME`
- Value: `C:\jdk-21.0.3+9`

---

## 📝 Configuration Quick Start

In `plugins/BubbleAnnounce/config.yml`:

```yaml
settings:
  prefix: ""              # Prefix for all messages
  defaultMode: auto       # Text format: legacy, minimessage, auto
  defaultType: chat       # Display: chat, actionbar, title
  defaultTitleTimings:
    fadeIn: 10
    stay: 60
    fadeOut: 10

announcements:
  myannouncement:
    enabled: true
    mode: auto
    type: chat
    interval: 30          # Minutes between announcements
    messages:
      - "Line 1"
      - "Line 2"
    commands: []
    coinsGiveAll: false
    randomOrder: false
    delayTicksBetweenLines: 0
```

---

## ✨ Features

- **Multiple Text Formats:** Legacy (&c codes), MiniMessage, or auto-detect
- **Multiple Display Types:** Chat, Action Bar, or Title screens
- **Scheduled Announcements:** Set custom intervals in minutes
- **Console Commands:** Execute commands when announcement fires
- **Placeholder Support:** Integrates with PlaceholderAPI
- **Coin Rewards:** Give players coins with announcements
- **Line Delays:** Control timing between announcement lines
- **Random Order:** Shuffle message lines for variety

---

## 📞 Building Again

If you make code changes, rebuild with:
```powershell
$env:JAVA_HOME = "C:\jdk-21.0.3+9"
.\gradlew.bat clean build
```

---

**Enjoy your updated BubbleAnnounce plugin! 🎉**
