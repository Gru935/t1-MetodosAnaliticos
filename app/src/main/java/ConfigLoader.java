import org.yaml.snakeyaml.Yaml;

import models.*;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ConfigLoader {

    public static Parameters loadConfig(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            Map<String, Object> obj = yaml.load(inputStream);

            // O conteúdo real está depois do "!PARAMETERS", então você deve acessar
            // diretamente
            Map<String, Object> parameters = (Map<String, Object>) obj.get("PARAMETERS");

            // Agora converte manualmente ou usa alguma técnica como um mapper.
            // Vou te mostrar de um jeito manual por enquanto:

            Parameters config = new Parameters();
            config.arrivals = (Map<String, Double>) parameters.get("arrivals");
            config.queues = (Map<String, Queue>) parameters.get("queues");
            config.network = (List<NetworkConnection>) parameters.get("network");
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
