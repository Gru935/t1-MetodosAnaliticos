import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import models.*;

public class Simulator {
    public static PriorityQueue<Event> scheduler;
    public static double global_time;
    public static int x;

    public static void start(ArrayList<Queue> queues, ArrayList<Event> arrivals, int rounds) {
        scheduler = new PriorityQueue<>(arrivals);
        global_time = 0.0;
        x = 57406;

        while (rounds > 0) {
            // PEGA O PROXIMO EVENTO
            Event event = nextEvent();

            if (event.getType() == EventType.ARRIVAL) {
                // PEGA A FILA ASSOCIADA AQUELE EVENTO
                Queue event_queue = findQueueByName(queues, event.getQueue_name());
                arrival(event, queues, event_queue);
            } else if (event.getType() == EventType.EXIT) {
                // PEGA A FILA ASSOCIADA AQUELE EVENTO
                Queue event_queue = findQueueByName(queues, event.getQueue_name());
                exit(event, queues, event_queue);
            } else {
                // PEGA A FILA ASSOCIADA AQUELE EVENTO E A FILA DE ORIGEM
                Queue event_queue = findQueueByName(queues, event.getQueue_name());
                Queue origin_queue = findQueueByName(queues, event.getOrigin_queue_name());
                pass(event, queues, event_queue, origin_queue);
            }
            rounds--;
        }

        // COLOCAR OS PRINTS
        for (Queue q : queues) {
            System.out.println(q.getName());
            System.out.println("State   Time                  Probability");
            for (int i = 0; i < q.getTimes().length; i++) {
                if (q.getTimes()[i] != 0.0) {
                    System.out.println(
                            i + "       " + q.getTimes()[i] + "    " + q.getTimes()[i] / global_time * 100 + "%");
                }
            }
            System.out.println();
            System.out.println("Number of losses: " + q.getLosses());
            System.out.println();
        }
        System.out.println("Global time: " + global_time);
    }

    public static double nextRandom() {
        x = ((4212002 * x) + 2224621) % 429496729;
        return ((double) x) / 429496729;
    }

    public static Event nextEvent() {
        return scheduler.poll();
    }

    public static Queue findQueueByName(ArrayList<Queue> queues, String name) {
        for (Queue q : queues) {
            if (q.getName().equals(name)) {
                return q;
            }
        }
        return null;
    }

    private static String drawProbability(Queue queue) {
        double sum = 0.0;
        Random r = new Random();
        double prob = r.nextDouble(0, 1);
        for (NetworkConnection n : queue.getConnections()) {
            sum += n.getProbability();
            if (prob < sum) {
                return n.getTarget();
            }
        }
        return null;
    }

    public static void arrival(Event e, ArrayList<Queue> queues, Queue event_queue) {
        accTime(e, queues);
        if (event_queue.getCustomers() < event_queue.getCapacity()) {
            event_queue.In();
            if (event_queue.getCustomers() <= event_queue.getServers()) {

                // SORTEIA O DESTINO DO PROXIMO EVENTO (SAIDA OU PASSAGEM DE FILA)
                String name_target = drawProbability(event_queue);

                if (name_target.equals("exit")) {
                    scheduleExit(event_queue);
                } else {
                    Queue target_queue = findQueueByName(queues, name_target);
                    schedulePass(event_queue, target_queue);
                }
            }
        } else {
            event_queue.Loss();
        }
        scheduleArrival(event_queue);
    }

    public static void exit(Event e, ArrayList<Queue> queues, Queue event_queue) {
        accTime(e, queues);
        event_queue.Out();
        if (event_queue.getCustomers() >= event_queue.getServers()) {

            // SORTEIA O DESTINO DO PROXIMO EVENTO (SAIDA OU PASSAGEM DE FILA)
            String name_target = drawProbability(event_queue);

            if (name_target.equals("exit")) {
                scheduleExit(event_queue);
            } else {
                Queue target_queue = findQueueByName(queues, name_target);
                schedulePass(event_queue, target_queue);
            }
        }
    }

    public static void pass(Event e, ArrayList<Queue> queues, Queue event_queue, Queue origin_queue) {
        accTime(e, queues);
        origin_queue.Out();
        if (event_queue.getCustomers() >= event_queue.getServers()) {

            // SORTEIA O DESTINO DO PROXIMO EVENTO (SAIDA OU PASSAGEM DE FILA)
            String name_target = drawProbability(event_queue);

            if (name_target.equals("exit")) {
                scheduleExit(event_queue);
            } else {
                Queue target_queue = findQueueByName(queues, name_target);
                schedulePass(event_queue, target_queue);
            }
        }
        if (event_queue.getCustomers() < event_queue.getCapacity()) {
            event_queue.In();
            if (event_queue.getCustomers() <= event_queue.getServers()) {

                // SORTEIA O DESTINO DO PROXIMO EVENTO (SAIDA OU PASSAGEM DE FILA)
                String name_target = drawProbability(event_queue);

                if (name_target.equals("exit")) {
                    scheduleExit(event_queue);
                } else {
                    Queue target_queue = findQueueByName(queues, name_target);
                    schedulePass(event_queue, target_queue);
                }
            }
        } else {
            event_queue.Loss();
        }
    }

    public static void accTime(Event e, ArrayList<Queue> queues) {
        for (Queue q : queues) {
            q.getTimes()[q.getCustomers()] += e.getTime() - global_time;
        }
        global_time = e.getTime();
    }

    public static void scheduleArrival(Queue event_queue) {
        double u = event_queue.getMinArrival() + ((event_queue.getMaxArrival() -
                event_queue.getMinArrival()) * nextRandom());
        Event event = new Event(u + global_time, EventType.ARRIVAL, event_queue.getName());
        scheduler.add(event);
    }

    public static void scheduleExit(Queue event_queue) {
        double u = event_queue.getMinService() + ((event_queue.getMaxService() -
                event_queue.getMinService()) * nextRandom());
        Event event = new Event(u + global_time, EventType.EXIT, event_queue.getName());
        scheduler.add(event);
    }

    public static void schedulePass(Queue event_queue, Queue target_queue) {
        double u = event_queue.getMinService() + ((event_queue.getMaxService() -
                event_queue.getMinService()) * nextRandom());
        Event event = new Event(u + global_time, EventType.PASS, target_queue.getName(), event_queue.getName());
        scheduler.add(event);
    }
}