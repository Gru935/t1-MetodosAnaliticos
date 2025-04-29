package models;

import java.util.List;
import java.util.Map;

public class Parameters {
    public Map<String, Double> arrivals;
    public Map<String, Queue> queues;
    public List<NetworkConnection> network;
    public int rndnumbersPerSeed;
    public List<Integer> seeds;
    public List<Double> rndnumbers;
}
