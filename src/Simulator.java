package src;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Simulator {
    public static PriorityQueue<Event> scheduler = new PriorityQueue<>();
    public static Queue queue1;
    public static Queue queue2;
    public static double global_time = 0.0;
    public static int x = 57406;

    public static void main(String[] args) {
        ArrayList<Queue> queues = new ArrayList<>();
        queue1 = new Queue(2, 3, 1, 4, 3, 4, 0, 0, null);
        queue2 = new Queue(1, 5, -1, -1, 2, 3, 0, 0, null);
        queue1.setTimes(new double[queue1.getCapacity() + 1]);
        queue2.setTimes(new double[queue2.getCapacity() + 1]);

        Event first = new Event(1.5, EventType.ARRIVAL);
        scheduler.add(first);

        int rounds = 100000;
        while (rounds > 0) {
            Event event = nextEvent();

            if (event.getType() == EventType.ARRIVAL) {
                arrival(event);
            } else if (event.getType() == EventType.EXIT) {
                exit(event);
            } else {
                pass(event);
            }
            rounds--;
        }

        // Realizar os prints dos conteudos exigidos
        System.out.println("Q1");
        System.out.println("State   Time                  Probability");
        for (int i = 0; i < queue1.getTimes().length; i++) {
            System.out.println(
                    i + "       " + queue1.getTimes()[i] + "    " + queue1.getTimes()[i] / global_time * 100 + "%");
        }
        System.out.println();
        System.out.println("Number of losses: " + queue1.getLosses());
        System.out.println();
        System.out.println("Q2");
        System.out.println("State   Time                  Probability");
        for (int i = 0; i < queue2.getTimes().length; i++) {
            System.out.println(
                    i + "       " + queue2.getTimes()[i] + "    " + queue2.getTimes()[i] / global_time * 100 + "%");
        }
        System.out.println();
        System.out.println("Number of losses: " + queue2.getLosses());
        System.out.println();
        System.out.println("Global time: " + global_time);
    }

    public static double nextRandom() {
        x = ((4212002 * x) + 2224621) % 429496729;
        return ((double) x) / 429496729;
    }

    public static Event nextEvent() {
        return scheduler.poll();
    }

    public static void arrival(Event e) {
        accTime(e);
        if (queue1.getCustomers() < queue1.getCapacity()) {
            queue1.In();
            if (queue1.getCustomers() <= queue1.getServers()) {
                schedulePass();
            }
        } else {
            queue1.Loss();
        }
        scheduleArrival();
    }

    public static void exit(Event e) {
        accTime(e);
        queue2.Out();
        if (queue2.getCustomers() >= queue2.getServers()) {
            scheduleExit();
        }
    }

    public static void pass(Event e) {
        accTime(e);
        queue1.Out();
        if (queue1.getCustomers() >= queue1.getServers()) {
            schedulePass();
        }
        if (queue2.getCustomers() < queue2.getCapacity()) {
            queue2.In();
            if (queue2.getCustomers() <= queue2.getServers()) {
                scheduleExit();
            }
        } else {
            queue2.Loss();
        }
    }

    public static void accTime(Event e) {
        queue1.getTimes()[queue1.getCustomers()] += e.getTime() - global_time;
        queue2.getTimes()[queue2.getCustomers()] += e.getTime() - global_time;
        global_time = e.getTime();
    }

    public static void scheduleArrival() {
        double u = queue1.getMinArrival() + ((queue1.getMaxArrival() - queue1.getMinArrival()) * nextRandom());
        Event event = new Event(u + global_time, EventType.ARRIVAL);
        scheduler.add(event);
    }

    public static void scheduleExit() {
        double u = queue2.getMinService() + ((queue2.getMaxService() - queue2.getMinService()) * nextRandom());
        Event event = new Event(u + global_time, EventType.EXIT);
        scheduler.add(event);
    }

    public static void schedulePass() {
        double u = queue1.getMinService() + ((queue1.getMaxService() - queue1.getMinService()) * nextRandom());
        Event event = new Event(u + global_time, EventType.PASS);
        scheduler.add(event);
    }
}