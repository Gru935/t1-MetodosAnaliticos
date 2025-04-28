package src;

public class Queue {
    private int servers;
    private int capacity;
    private double minArrival;
    private double maxArrival;
    private double minService;
    private double maxService;
    private int customers;
    private int losses;
    private double[] times;

    public Queue(int servers, int capacity, double minArrival, double maxArrival, double minService, double maxService,
            int customers, int losses, double[] times) {
        this.servers = servers;
        this.capacity = capacity;
        this.minArrival = minArrival;
        this.maxArrival = maxArrival;
        this.minService = minService;
        this.maxService = maxService;
        this.customers = customers;
        this.losses = losses;
        this.times = times;
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

}
