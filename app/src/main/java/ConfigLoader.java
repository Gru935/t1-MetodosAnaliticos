import org.yaml.snakeyaml.Yaml;

import models.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ConfigLoader {

    public static Parameters loadConfig(String filePath) {
        Yaml yaml = new Yaml();
        InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(filePath);

        if (inputStream == null) {
            System.err.println("Arquivo YAML não encontrado: " + filePath);
            return null;
        }

        try {
            Map<String, Object> obj = yaml.load(inputStream);

            Parameters config = new Parameters();

            Map<String, Object> parameters = obj; // usa o mapa raiz

            // CONVERTE CHEGADAS
            List<Event> events = new java.util.ArrayList<>();
            Map<String, Double> arrivalsMap = (Map<String, Double>) parameters.get("arrivals");

            for (Map.Entry<String, Double> entry : arrivalsMap.entrySet()) {
                String queueName = entry.getKey();
                double arrivalTime = entry.getValue();

                Event e = new Event(arrivalTime, EventType.ARRIVAL, queueName);
                events.add(e);
            }
            config.events = events;

            // CONVERTE FILAS
            Map<String, Map<String, Object>> queuesMap = (Map<String, Map<String, Object>>) parameters.get("queues");
            Map<String, Queue> parsedQueues = new java.util.HashMap<>();

            for (String name : queuesMap.keySet()) {
                Map<String, Object> q = queuesMap.get(name);

                int servers = (int) q.get("servers");

                // Caso a capacidade não seja dita, estamos considerandos um valor alto, mas não
                // o maior possível (MAX_INTEGER), pois senão os limites da JVM são
                // ultrapassados (Exception: Requested array size exceeds VM limit)
                int capacity = q.containsKey("capacity") ? (int) q.get("capacity") : 1000000;

                double minArrival = q.containsKey("minArrival") ? ((Number) q.get("minArrival")).doubleValue() : -1;
                double maxArrival = q.containsKey("maxArrival") ? ((Number) q.get("maxArrival")).doubleValue() : -1;
                double minService = ((Number) q.get("minService")).doubleValue();
                double maxService = ((Number) q.get("maxService")).doubleValue();

                Queue queue = new Queue(name, servers, capacity, minArrival, maxArrival, minService, maxService);
                // queue.setTimes(new double[capacity + 1]);
                parsedQueues.put(name, queue);
            }
            config.queues = parsedQueues;

            // CONVERTE NETWORK
            List<Map<String, Object>> networkList = (List<Map<String, Object>>) parameters.get("network");
            List<NetworkConnection> parsedConnections = new java.util.ArrayList<>();

            for (Map<String, Object> conn : networkList) {
                String source = (String) conn.get("source");
                String target = (String) conn.get("target");
                double probability = ((Number) conn.get("probability")).doubleValue();

                NetworkConnection c = new NetworkConnection(source, target, probability);
                parsedConnections.add(c);
            }
            config.network = parsedConnections;

            // RESTO
            config.rndnumbersPerSeed = (Integer) parameters.get("rndnumbersPerSeed");
            config.seeds = (List<Integer>) parameters.get("seeds");
            config.rndnumbers = (List<Double>) parameters.get("rndnumbers");

            return config;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
