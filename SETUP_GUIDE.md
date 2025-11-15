# BubbleAnnounce v1.1.0 - Setup Guide

## ✅ Completed Updates

Your plugin has been successfully updated with the following improvements:

### 1. **Paper/Purpur 1.21.1 Support**
   - Updated from Spigot API to **Paper API**
   - Compatible with Paper, Purpur, and Spigot servers
   - `build.gradle` now references the official Paper Maven repository

### 2. **Gradle Build System**
   - Gradle wrapper configured: **8.10.2**
   - Supports Java 8+ for running the build
   - Requires **JDK 17+ for compilation** (see setup instructions below)

### 3. **User-Friendly Configuration**
   - Completely restructured `config.yml` with:
     - Clear section headers with visual separators
     - Detailed comments explaining every setting
     - Examples for each configuration option
     - Comprehensive text formatting guide
     - Better organization of announcements

### 4. **Plugin Manifest Updates**
   - Version bumped to **1.1.0**
   - Added permission definitions in `plugin.yml`
   - Updated description and platform support info
   - Better command documentation

---

## ⚠️ Build Requirements

To compile this plugin, you need to **install Java Development Kit (JDK) 17 or higher**.

### Windows Installation Steps:

1. **Download JDK 17+ LTS**
   - Visit: https://www.oracle.com/java/technologies/downloads/#java17
   - Or use Eclipse Adoptium: https://adoptium.net/
   - Choose **JDK** (not JRE)

2. **Install JDK**
   - Run the installer and follow the prompts
   - Note the installation directory (typically `C:\Program Files\Java\jdk-21.x.x`)

3. **Set JAVA_HOME Environment Variable**
   - Right-click "This PC" or "My Computer" → Properties
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - Click "New" under "System variables"
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-21.x.x` (adjust version as needed)
   - Click OK and restart your terminal/IDE

4. **Verify Installation**
   ```powershell
   java -version
   javac -version
   ```
   Both should return version 17 or higher.

---

## 🔨 Building the Plugin

Once you have JDK 17+ installed:

```powershell
cd "c:\Users\eirvi\Documents\BubbleCraft\Plugins\BubbleAnnounce"
.\gradlew.bat clean build
```

The compiled JAR will be at: `build/libs/BubbleAnnounce.jar`

---

## 📋 Configuration Files

### `config.yml` - Main Configuration
- **prefix**: Global prefix for all announcements
- **defaultMode**: Text format (legacy, minimessage, or auto)
- **defaultType**: Delivery method (chat, actionbar, title)
- **announcements**: Define your custom announcements

### `plugin.yml` - Plugin Manifest
- API version: 1.21 (Paper 1.21.1+)
- Commands and permissions
- Plugin metadata

---

## 🎮 Commands

| Command | Permission | Description |
|---------|-----------|-------------|
| `/test Announce` | `test.announce` | Test all announcements |
| `/runannouncements` | `announce.run` | Manually trigger announcements |
| `/bubbleannounce reload` | `bubbleannounce.admin` | Reload configuration |

---

## 📝 Text Formatting

### Legacy Format
```
&c - Red        &a - Green      &9 - Blue
&l - Bold       &o - Italic     &n - Underline
&#FF0000 - Hex colors
```

### MiniMessage Format
```
<red>Text</red>
<bold>Bold text</bold>
<#FF0000>Hex colors</#FF0000>
```

---

## 🚀 Next Steps

1. Install JDK 17+ as described above
2. Build the plugin: `.\gradlew.bat clean build`
3. Copy `BubbleAnnounce.jar` to your server's `plugins/` folder
4. Restart server: `/reload` or restart the server
5. Customize `config.yml` in `plugins/BubbleAnnounce/`
6. Use `/bubbleannounce reload` to apply changes

---

## 📦 Dependencies

- **Paper/Purpur 1.21.1+** - Server software
- **PlaceholderAPI** (optional) - For placeholder support
- **Adventure 4.17.0** - Text formatting library

---

## 📞 Support

If you encounter issues:
- Ensure JDK (not JRE) is installed and JAVA_HOME is set
- Check Gradle version: `.\gradlew.bat --version`
- Run: `.\gradlew.bat clean build --stacktrace` for detailed errors
