# 📝 LAZY PERSON'S GUIDE TO BUBBLEANNOUNCE

## 3-Second Summary
Edit `config.yml`, change messages, type `/bubbleannounce reload`. Done.

## Edit Existing Announcement
1. Open `config.yml`
2. Find the announcement name (like `discord:`)
3. Change the text in `messages:`
4. Save file
5. Type in game: `/bubbleannounce reload`

## Add New Announcement
1. Copy an entire announcement from `config.yml`
2. Paste it somewhere else in the file
3. Change the name (first line)
4. Change the messages
5. `/bubbleannounce reload`

## Example Copy-Paste
```yaml
  vote:              # ← Your new announcement name
    enabled: true
    interval: 30     # ← Every 30 minutes
    messages:        # ← Your text
      - ""
      - "&eVote for rewards!"
      - ""
```

## Turn Off Announcement
Change `enabled: true` to `enabled: false`

OR type: `/bubbleannounce toggle <name>`

## Colors
- `&a` green
- `&c` red
- `&e` yellow
- `&b` blue
- `&l` bold

Example: `"&a&lGREEN BOLD &cred"`

## Settings You Actually Care About

| Thing | What | Example |
|-------|------|---------|
| `enabled:` | On/off | `true` or `false` |
| `interval:` | Minutes between | `30` = every 30 min |
| `messages:` | The text | List of text lines |
| `coinsGiveAll:` | Give coins? | `true` or `false` |

## Commands
- `/bubbleannounce reload` - After editing
- `/bubbleannounce list` - See all
- `/runannouncements` - Send all now

## That's It!
Everything else is optional. Just edit messages and reload. 🎉
