package me.n2g7mutf8.soup.utils.elo;

public class KFactor {

    private final int startIndex;
    private final int endIndex;
    private final double value;

    public KFactor(int startIndex, int endIndex, double value) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.value = value;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public double getValue() {
        return value;
    }
}
