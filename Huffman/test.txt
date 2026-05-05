

public class HuffmanNode implements Comparable<HuffmanNode> {
    private Character c;
    private Integer frequency;

    HuffmanNode parent;

    HuffmanNode left;
    HuffmanNode right;

    public HuffmanNode(Character c, int freq) {
        this.c = c;
        this.frequency = freq;
    }

    public boolean is_leaf() {
        return left == null && right == null;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return Integer.compare(this.frequency, o.frequency);
    }

    /**
     * @return the c
     */
    public Character getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(Character c) {
        this.c = c;
    }

    /**
     * @return the frequency
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

}
