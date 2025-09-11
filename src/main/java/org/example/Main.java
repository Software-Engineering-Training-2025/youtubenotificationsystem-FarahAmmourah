package org.example;

import org.example.observer.Observer;
import org.example.observer.User;

public class Main {
    public static void main(String[] args) {
        ChannelService svc = new ChannelService();

        svc.createChannel("TechWorld");
        svc.createChannel("FoodiesUnite");

        User alice = new User("Alice");
        User bob   = new User("Bob");
        User charlie = new User("Charlie");

        Observer lambdaObs = msg -> System.out.println("LambdaUser >> " + msg);

        svc.subscribe("TechWorld", alice);
        svc.subscribe("TechWorld", bob);
        svc.subscribe("TechWorld", lambdaObs);

        svc.subscribe("FoodiesUnite", alice);
        svc.subscribe("FoodiesUnite", charlie);

        svc.upload("TechWorld", "Observer Pattern Explained");
        svc.upload("FoodiesUnite", "Best Pasta Recipe");

        svc.unsubscribe("TechWorld", bob);
        svc.upload("TechWorld", "Second Video after Bob unsubscribed");

        svc.listChannels().forEach(ch -> System.out.println("Channel: " + ch));
    }
}
