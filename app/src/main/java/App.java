import java.util.ArrayList;

import models.*;

public class App {
    public static void main(String[] args) {
        Parameters config = ConfigLoader.loadConfig("model.yml");

        // ATRIBUINDO NETWORKS PARA AS FILAS
        for (Queue q : config.queues.values()) {
            for (NetworkConnection connection : config.network) {
                if (q.getName().equals(connection.getSource())) {
                    q.getConnections().add(connection);
                }
            }
            q.getConnections().sort((c1, c2) -> Double.compare(c1.getProbability(), c2.getProbability()));
        }

        ArrayList<Queue> queues = new ArrayList<>(config.queues.values());
        ArrayList<Event> arrivals = new ArrayList<>(config.events);
        int rounds = config.rndnumbersPerSeed;

        Simulator.start(queues, arrivals, rounds);
    }
}
