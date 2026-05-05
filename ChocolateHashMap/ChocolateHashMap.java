/**
 * ChocolateHashMap<K,V>
 *
 * A custom hash map (separate chaining) built for a fictional chocolate factory inventory system.
 * Each bucket is a circular DOUBLY-linked list with a sentinel BatchNode.
 *
 * You are responsible for implementing the methods marked TODO.
 */

public class ChocolateHashMap {
    private BatchNode[] buckets;
    private int objectCount;
    private double loadFactorLimit;

    // Constructor: creates a hash map with the given initial bucket size and load
    // factor limit
    public ChocolateHashMap(int bucketCount, double loadFactorLimit) {
        this.buckets = (BatchNode[]) new BatchNode[bucketCount];
        fillArrayWithSentinels(buckets);
        this.objectCount = 0;
        this.loadFactorLimit = loadFactorLimit;
    }

    // Constructor: creates an empty hash map with default parameters
    public ChocolateHashMap() {
        this(10, 0.75);
    }

    private static void fillArrayWithSentinels(BatchNode[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new BatchNode();
        }
    }

    // Return a pointer to the bucket array
    public BatchNode[] getBuckets() {
        return this.buckets;
    }

    // Returns true if this map is empty; otherwise returns false.
    public boolean isEmpty() {
        return (objectCount == 0);
    }

    // Returns the number of entries in this map.
    public int size() {
        return objectCount;
    }

    // Return the bucket index for the key
    // Use .hashCode(), but be aware that hashCode can return negative numbers!
    // NOTE: Math.abs(Integer.MIN_VALUE) is still negative. Consider masking the
    // sign bit.
    private int whichBucket(String key) {
        return (0x7FFFFFFF & this.hashCode()) % this.buckets.length;
    }

    // Returns the current load factor (objCount / buckets)
    public double currentLoadFactor() {
        return (double) objectCount / (double) this.buckets.length;
    }

    // Return true if the key exists as a key in the map, otherwise false.
    // Use the .equals method to check equality.
    public boolean containsKey(String key) {
        var bucket = whichBucket(key);
        var node = this.buckets[bucket].getNext();
        while (!node.isSentinel()) {
            if (node.getKey().equals(key))
                return true;
            node = node.getNext();
        }
        return false;
    }

    // Return true if the value exists as a value in the map, otherwise false.
    // Use the .equals method to check equality.
    public boolean containsValue(ChocolateBatch value) {
        for (var bucket : this.buckets) {
            var node = bucket.getNext();
            while (!node.isSentinel())
                if (node.getValue().equals(value))
                    return true;

        }
        return false;
    }

    // Puts a key-value pair into the map.
    // If the key already exists in the map you should *not* add the key-value pair.
    // Return true if the pair was added; false if the key already exists.
    // If a pair should be added, add it to the END of the bucket.
    // After adding the pair, check if the load factor is greater than the limit.
    // - If so, you must call rehash with double the current bucket size.
    public boolean put(String key, ChocolateBatch value) {
        var bucket = whichBucket(key);
        var sent = this.buckets[bucket];

        var new_node = new BatchNode(key, value, sent.getPrevious(), sent);
        sent.insertBefore(new_node);

        if (currentLoadFactor() >= this.loadFactorLimit) {
            this.rehash(buckets.length * 2);
        }

        return true;
    }

    // Returns the value associated with the key in the map.
    // If the key is not in the map, then return null.
    public ChocolateBatch get(String key) {
        var bucket = whichBucket(key);
        var node = this.buckets[bucket].getNext();
        while (!node.isSentinel()) {
            if (node.getKey().equals(key))
                return node.getValue();
            node = node.getNext();
        }
        return null;
    }

    // Remove the pair associated with the key.
    // Return true if successful, false if the key did not exist.
    public boolean remove(String key) {
        var bucket = whichBucket(key);
        var node = this.buckets[bucket].getNext();

        while (!node.isSentinel()) {
            if (node.getKey().equals(key)) {
                node.unlink();
                return true;
            }
            node = node.getNext();
        }
        return false;
    }

    // Rehash the map so that it contains the given number of buckets
    // Loop through all existing buckets, from 0 to length
    // Rehash each object into the new bucket array in the order they appear on the
    // original chain.
    // I.e. if a bucket originally has (sentinel)->J->Z->K, then J will be rehashed
    // first,
    // followed by Z, then K.
    public void rehash(int newBucketCount) {
        var new_map = new ChocolateHashMap(newBucketCount, this.loadFactorLimit);

        for (var bucket : this.buckets) {
            var node = bucket.getNext();
            while (!node.isSentinel()) {
                new_map.put(node.getKey(), node.getValue());
            }
        }
    }

    // The output should be in the following format:
    // [ n, k | { b#: k1,v1 k2,v2 k3,v3 } { b#: k1,v1 k2,v2 } ]
    // n is the objCount
    // k is the number of buckets
    // For each bucket that contains objects, create a substring that indicates the
    // bucket index
    // And list all of the items in the bucket (in the order they appear)
    // Example (using chocolate-themed data):
    // [ 3, 10 | { b3: LOT-70,DARK LOT-12,MILK } { b7: LOT-99,WHITE } ]
    @Override
    public String toString() {
        var ret = new StringBuilder();
        ret.append("[ " + this.objectCount + ", " + this.buckets.length + " | ");
        for (int i = 0; i < this.buckets.length; i++) {
            var node = this.buckets[i].getNext();
            if (!node.isSentinel()) {
                ret.append(" { b" + i + ": ");
                while (!node.isSentinel()) {
                    ret.append(node.getKey().toString() + ", " + node.getValue().toString() + " ");
                    node = node.getNext();
                }
                ret.append("}");
            }
        }
        ret.append(" ]");
        return ret.toString();
    }
}
