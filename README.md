# XLibrary

Модульная утилитарная библиотека для Minecraft: Paper / Velocity / BungeeCord.

## Модули

| Модуль | Описание |
|---|---|
| `core` | Утилиты: `NumberUtility`, `StringUtility`, `EnumUtility`, `CollectionUtility`, `ReflectionUtility`, `Time` |
| `paper` | Paper API: `ColorUtility`, GUI система, **Schematic API** |
| `velocity` | Velocity API |
| `bungee` | BungeeCord API |

---

## 📦 Schematic API

Двухпровайдерная система схематик: **FastAsyncWorldEdit** или **нативный движок** (без зависимостей).

### Быстрый старт

```java
// Автовыбор провайдера
SchematicProvider provider = Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit")
    ? new FaweSchematicProvider()
    : new NativeSchematicProvider(plugin);

SchematicManager manager = new SchematicManager(provider);

// Вставка
manager.paste(file, location).thenAccept(session -> { /* готово */ });

// Сохранение
manager.save(file, corner1, corner2, true).thenAccept(f -> { /* готово */ });

// Отмена
manager.undo(session);
```

### Провайдеры

| | `FaweSchematicProvider` | `NativeSchematicProvider` |
|---|---|---|
| **Зависимость** | FastAsyncWorldEdit | Нет |
| **Формат** | `.schem` (WorldEdit) | `.esrc` (JSON) |
| **paste** | `Clipboard` → `EditSession` | Чанковая вставка через `BukkitScheduler` |
| **save** | `ForwardExtentCopy` → `ClipboardWriter` | `StringBuilder` → Gson → JSON |
| **undo** | `EditSession.undo()` | Восстановление из `undoMap` |

### Команды

| Команда | Описание | Право |
|---|---|---|
| `/xlibrary schematic wand` | Инструмент выделения (ЛКМ/ПКМ) | `xlibrary.schematic.use` |
| `/xlibrary schematic save <name>` | Сохранить регион в `.esrc` | `xlibrary.schematic.use` |
| `/xlibrary schematic paste <name>` | Вставить схематику | `xlibrary.schematic.use` |
| `/xlibrary schematic undo` | Отменить последнюю вставку | `xlibrary.schematic.use` |

### Интеграция в плагин

```java
@Override
public void onEnable() {
    SchematicProvider provider = Bukkit.getPluginManager().isPluginEnabled("FastAsyncWorldEdit")
        ? new FaweSchematicProvider()
        : new NativeSchematicProvider(this);

    SchematicManager manager = new SchematicManager(provider);

    SelectionListener selection = new SelectionListener();
    getServer().getPluginManager().registerEvents(selection, this);

    File folder = new File(getDataFolder(), "schematics");
    getCommand("xlibrary").setExecutor(new SchematicCommand(manager, selection, folder));
    getCommand("xlibrary").setTabCompleter(new SchematicTabCompleter(folder));
}
```

### Формат `.esrc`

```json
{
  "firstPoint": { "x": 5, "y": 0, "z": 5, "worldName": "world" },
  "secondPoint": { "x": -5, "y": 0, "z": -5, "worldName": "world" },
  "midPoint": { "x": 100.0, "y": 64.0, "z": 200.0, "worldName": "world" },
  "data": "5%0%5%STONE%minecraft:stone@4%1%5%GRASS_BLOCK%minecraft:grass_block[snowy=false]@..."
}
```

### Структура пакетов

```
net.xdevelopment.xlibrary.schematic
├── SchematicProvider          — интерфейс
├── SchematicManager           — роутер
├── SchematicMessages          — MiniMessage строки
├── fawe/
│   └── FaweSchematicProvider  — FAWE реализация
├── nativeapi/
│   ├── NativeSchematicProvider
│   ├── data/                  — JsonSchematicData, JsonLocation, WorldBlock
│   ├── load/                  — AsyncSchematicLoader, TerritoryPasteTransaction
│   └── save/                  — TerritorySaveTransaction
├── command/
│   ├── SchematicCommand       — /xlibrary schematic
│   └── SchematicTabCompleter
└── selection/
    ├── PlayerSelection        — pos1/pos2
    └── SelectionListener      — обработка ЛКМ/ПКМ
```

---

## Core Utilities

| Утилита | Описание |
|---|---|
| `NumberUtility` | Парсинг чисел через `Optional`, форматирование |
| `StringUtility` | `varStyle`, Base64, нормализация строк |
| `EnumUtility` | Безопасный парсинг enum, сдвиг, итерация |
| `CollectionUtility` | Split, modify, fuzzy-match, сортировка |
| `ReflectionUtility` | Reflection через `Optional`, поиск классов/методов/полей |
| `Time` | Сериализация Duration (RU/EN, short/medium/full) |

---

## Сборка

```bash
./gradlew build
```
