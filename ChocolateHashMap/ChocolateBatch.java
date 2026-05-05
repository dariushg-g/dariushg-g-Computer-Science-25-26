/**
 * ChocolateBatch is a small domain class you can use as a value type for ChocolateHashMap.
 *
 * Example:
 *   ChocolateHashMap<String, ChocolateBatch> inventory = new ChocolateHashMap<>();
 *   inventory.put("SKU-TRUF-001", new ChocolateBatch("Truffle Box", 72, "Ecuador", 120));
 */
public class ChocolateBatch {
    private final String productName;
    private final int cocoaPercent;     // e.g. 70 means 70%
    private final String beanOrigin;    // e.g. "Ghana", "Ecuador"
    private int unitsInStock;           // e.g. bars / boxes / pallets (your choice)

    public ChocolateBatch(String productName, int cocoaPercent, String beanOrigin, int unitsInStock) {
        this.productName = productName;
        this.cocoaPercent = cocoaPercent;
        this.beanOrigin = beanOrigin;
        this.unitsInStock = unitsInStock;
    }

    public String getProductName() { return productName; }
    public int getCocoaPercent() { return cocoaPercent; }
    public String getBeanOrigin() { return beanOrigin; }
    public int getUnitsInStock() { return unitsInStock; }

    public void addUnits(int delta) {
        this.unitsInStock += delta;
    }

    @Override
    public String toString() {
        return productName + " (" + cocoaPercent + "%, " + beanOrigin + ", stock=" + unitsInStock + ")";
    }
}
