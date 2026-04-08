# Core Utilities

The `net.xdevelopment.xlibrary.core.utility` package provides powerful helpers.

## CollectionUtility
Assorted utilities for manipulating lists:
- `split(List<T> source, int partitionSize)` — splits huge lists of blocks into chunks for asynchronous processing.
- `getSequentialMatches(List<String> list, String match)` — smart matching utility. Perfect for command Tab Completion.

## NumberUtility
Safe conversion of strings to numbers.
- `getInteger(String context, int def)` — parses strings into integers with a default mathematical fallback, preventing `NumberFormatException` crashes.

## ExpiringSet
A special thread-safe collection where elements automatically clear themselves after a set period. Ideal for Anti-Spam / Cooldown filters!

```java
// Create a set with a 100ms timing
ExpiringSet<UUID> throttled = new ExpiringSet<>(100L);

if (!throttled.add(player.getUniqueId())) {
    player.sendMessage("Do not click so quickly!");
    return;
}
```

## Randomizer (Weighted Randoms)
Allows you to pull random elements based on a given percentage weight. Perfect for Lootboxes or randomized rewards!

```java
Randomizer<String> lootBox = new Randomizer<>();

// The second parameter is the weight (can be any double)
lootBox.add("Common Sword", 70.0);
lootBox.add("Rare Shield", 25.0);
lootBox.add("Legendary Helmet", 5.0);

// Get a random item respecting the probabilities
String drop = lootBox.get(); 
```

## Difference (State Tracking)
Very handy utility for diffing collections. If you want to compare old cache vs new cache and find exactly what was added or removed:

```java
Difference<Player> diff = new Difference<>();
diff.recordChange(ChangeType.ADD, player);
diff.recordChange(ChangeType.REMOVE, anotherPlayer);

for (Player p : diff.getAdded()) {
    // Process newly added elements
}
```
