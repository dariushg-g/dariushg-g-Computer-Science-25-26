# ChocolateHashMap Assignment: The Cocoa Inventory Ledger

You work as a chocolate manufacturer. The factory’s inventory system needs a fast way to look up
information about chocolate products and production batches using a unique key (SKU, lot code, recipe name, etc.).

Your job is to implement this as a hash map for your client.

## Requirements
- Use hashing to map keys to bucket indices.
- Resolve collisions using **separate chaining**.
- Practice working with **circular doubly-linked lists** using **sentinel** nodes.
- Understand and implement **rehashing** based on load factor.

## Files

### Provided (do not modify unless your instructor allows it)

- `ChocolateEntry.java` — a simple key/value pair class stored inside the map
- `BatchNode.java` — a node for a circular doubly-linked list (each bucket is one list)
- `ChocolateBatch.java` — The batch value type you can store in the map

### You implement

- `ChocolateHashMap.java` — the actual hash map implementation

## Data structure rules

- `buckets` is an array.
- Each `buckets[i]` is a **sentinel** `BatchNode<ChocolateEntry<K,V>>`.
- A bucket is **empty** when `sentinel.getNext() == sentinel` (and `sentinel.getPrevious() == sentinel`).
- Bucket items are stored as a circular list:
  `sentinel -> node1 -> node2 -> ... -> nodeN -> sentinel`

## Methods to implement in `ChocolateHashMap.java`

Implement the methods marked `TODO`:
1. `whichBucket(K key)`
2. `currentLoadFactor()`
3. `containsKey(K key)`
4. `containsValue(V value)`
5. `put(K key, V value)`
6. `get(K key)`
7. `remove(K key)`
8. `rehash(int newBucketCount)`
9. `toString()`

## Required behavior and edge cases

### Keys and values
- **Keys must not be `null`.**
- Values may be `null`.  Sometimes the chocolate SKU is a recipe placeholder for a chocolate batch which hasn't been created yet. (Be careful with `.equals`!)

### `whichBucket`
- Use `key.hashCode()` and map it into `0...buckets.length-1`.
- HINT: `hashCode()` can be negative, and `Math.abs(Integer.MIN_VALUE)` is still negative.

### `put`
- If the key already exists: **do not add** and **do not update**. Return `false`.
- If the key does not exist:
  - add the new SKU and recipe and all dependencies
- “END of the bucket” means inserting immediately **before the sentinel**.

### `rehash`
- Incrase the size of buckets and figure out how to move all items over

### `toString`
Your `toString()` must follow this format:

```
[ n, k | { b#: k1,v1 k2,v2 } { b#: k1,v1 } ]
```

- `n` is the number of objects (`objectCount`)
- `k` is the number of buckets (`buckets.length`)
- Only print buckets that contain at least one item
- Print buckets in increasing index order
- Inside a bucket, print items in list order (first to last)
- Each entry is printed as `key,value` with a space between entries

Example:

```
[ 3, 10 | { b3: SKU-A,Milk SKU-B,Dark } { b7: SKU-C,White } ]
```

## Suggested test data (optional)

You can use the provided `ChocolateBatch` class as your value type:

```java
ChocolateHashMap<String, ChocolateBatch> inventory = new ChocolateHashMap<>();
inventory.put("SKU-TRUF-001", new ChocolateBatch("Truffle Box", 72, "Ecuador", 120));
inventory.put("SKU-BAR-070", new ChocolateBatch("70% Dark Bar", 70, "Ghana", 500));
System.out.println(inventory.get("SKU-TRUF-001"));
```

## What to submit

- `ChocolateHashMap.java` (and any other files your instructor requests)

