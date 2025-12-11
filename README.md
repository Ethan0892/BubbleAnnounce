# BubbleAnnounce

Auto announcements for Minecraft servers. Super easy to configure.

## 🚀 Quick Start (3 Steps)

1. Put `BubbleAnnounce.jar` in your **Paper/Spigot server's** `plugins` folder (**NOT Velocity!**)
2. Start server (creates `config.yml`)
3. Edit `config.yml` - change the messages and type `/bubbleannounce reload`

Done! 🎉

> ⚠️ **IMPORTANT:** This is a **Bukkit/Spigot/Paper plugin**, not a Velocity plugin!  
> Put it in your **backend servers** (Paper/Spigot), NOT in your Velocity proxy's plugins folder.

## ✏️ How to Edit Announcements

Open `plugins/BubbleAnnounce/config.yml` and scroll to the announcements:

```yaml
announcements:
  discord:
    enabled: true         # Turn on/off
    interval: 39          # Every 39 minutes
    messages:             # ← EDIT THIS!
      - ""
      - "&9★ &fJoin our &9&lDiscord&f server!"
      - ""
```

**Change the text** in `messages:` then type `/bubbleannounce reload` in-game.

## ➕ Add New Announcement (Copy & Paste)

1. Copy any announcement in `config.yml`
2. Change the name (like `discord:` → `vote:`)
3. Edit the messages
4. `/bubbleannounce reload`

Example:
```yaml
  vote:              # ← New name
    enabled: true
    interval: 30
    messages:
      - "&eVote for us and get rewards!"
```

## 🎨 Color Codes

Add these to your messages:
- `&a` = green  
- `&c` = red  
- `&e` = yellow  
- `&b` = blue  
- `&6` = gold
- `&l` = bold  
- `&f` = white

Example: `"&a&lGREEN BOLD TEXT &cred text"`

## 📋 Commands

| Command | What it does |
|---------|--------------|
| `/bubbleannounce reload` | Reload after editing config |
| `/bubbleannounce list` | See all announcements |
| `/bubbleannounce toggle <name>` | Turn announcement on/off |
| `/runannouncements` | Send all announcements now |

## 💡 Cool Examples

**Every 30 minutes:**
```yaml
  welcome:
    enabled: true
    interval: 30
    messages:
      - "&6★ Welcome to our server!"
```

**Give coins to everyone:**
```yaml
  thanks:
    enabled: true
    interval: 45
    messages:
      - "&aThanks for playing!"
    coinsGiveAll: true    # Everyone gets 1 coin!
```

**Manual only (no timer):**
```yaml
  event:
    enabled: true
    interval: 0           # 0 = never auto-send
    messages:
      - "&c&lEvent starting in 5 minutes!"
```

**VIP only with sound:**
```yaml
  vip_perk:
    enabled: true
    interval: 20
    messages:
      - "&6[VIP] &eSpecial VIP perk unlocked!"
    permission: "group.vip"
    sound: "ENTITY_EXPERIENCE_ORB_PICKUP"
```

## 🎯 Requirements

- **Server Type:** Paper/Purpur/Spigot 1.21+ (Backend servers only)
- **Java:** 21
- **NOT compatible with:** Velocity, BungeeCord, Waterfall (proxy servers)

> 💡 **Using a proxy?** Install this plugin on your **backend Paper/Spigot servers**, not on the proxy itself.

## 📚 More Help

Everything is explained in `config.yml` with comments. Just read the file!

## 🔧 Troubleshooting

**Error: "No velocity-plugin.json present"**
- ❌ You tried to install on a Velocity proxy server
- ✅ Install on your **Paper/Spigot backend servers** instead
- This is a Bukkit plugin, NOT a Velocity plugin!

**Where to install if using a proxy:**
```
Velocity Proxy (proxy server)
├── plugins/
│   └── ❌ DO NOT put BubbleAnnounce here!
│
Backend Server 1 (Paper/Spigot)
├── plugins/
│   └── ✅ BubbleAnnounce.jar goes here!
│
Backend Server 2 (Paper/Spigot)  
├── plugins/
│   └── ✅ BubbleAnnounce.jar goes here too!
```
