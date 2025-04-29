package models;

import java.util.ArrayList;
import java.util.Arrays;

public class Queue {
    private String name;
    private int servers;
    private int capacity;
    private double minArrival;
    private double maxArrival;
    private double minService;
    private double maxService;
    private int customers;
    private int losses;
    private double[] times;
    private ArrayList<NetworkConnection> connections;

    public Queue(String name, int servers, int capacity, double minArrival, double maxArrival, double minService,
            double maxService) {
        this.name = name;
        this.servers = servers;
        this.capacity = capacity;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minService = minService;
        this.maxService = maxService;
        customers = 0;
        losses = 0;
        times = new double[capacity + 1];
        connections = new ArrayList<>();
    }

    public void In() {
        customers++;
    }

    public void Loss() {
        losses++;
    }

    public void Out() {
        customers--;
    }

    public int getServers() {
        return servers;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getMinArrival() {
        return minArrival;
    }

    public double getMaxArrival() {
        return maxArrival;
    }

    public double getMinService() {
        return minService;
    }

    public double getMaxService() {
        return maxService;
    }

    public int getCustomers() {
        return customers;
    }

    public int getLosses() {
        return losses;
    }

    public double[] getTimes() {
        return times;
    }

    public void setServers(int servers) {
        this.servers = servers;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setMinArrival(double minArrival) {
        this.minArrival = minArrival;
    }

    public void setMaxArrival(double maxArrival) {
        this.maxArrival = maxArrival;
    }

    public void setMinService(double minService) {
        this.minService = minService;
    }

    public void setMaxService(double maxService) {
        this.maxService = maxService;
    }

    public void setCustomers(int customers) {
        this.customers = customers;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setTimes(double[] times) {
        this.times = times;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<NetworkConnection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<NetworkConnection> connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "Queue [name=" + name + ", servers=" + servers + ", capacity=" + capacity + ", minArrival=" + minArrival
                + ", maxArrival=" + maxArrival + ", minService=" + minService + ", maxService=" + maxService
                + ", customers=" + customers + ", losses=" + losses + ", times=" + Arrays.toString(times)
                + ", connections=" + connections.toString() + "]";
    }

}
