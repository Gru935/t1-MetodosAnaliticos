package models;

public class NetworkConnection {
    private String source;
    private String target;
    private double probability;

    public NetworkConnection(String source, String target, double probability) {
        this.source = source;
        this.target = target;
        this.probability = probability;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "NetworkConnection [source=" + source + ", target=" + target + ", probability=" + probability + "]";
    }

}
