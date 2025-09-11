package org.example.subject;

import org.example.observer.Observer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Channel implements Subject {
    private final String name;
    private final Set<Observer> subscribers = new HashSet<>();

    public Channel(String name) {this.name = name;}

    public String getName() {return name;}

    public void uploadVideo(String title){
        Optional.ofNullable(title)
                .filter(t -> !t.isBlank())
                .map(t -> name + " uploaded a new video: " + t)
                .ifPresent(this::notifyObservers);
    }

    @Override
    public void subscribe(Observer observer) {
        Optional.ofNullable(observer).ifPresent(subscribers::add);
    }

    @Override
    public void unsubscribe(Observer observer) {
        Optional.ofNullable(observer).ifPresent(subscribers::remove);
    }

    @Override
    public void notifyObservers(String message) {
        subscribers.forEach(o -> o.update(message));
    }

    public Set<Observer> snapshotSubscribers() {
        return Collections.unmodifiableSet(subscribers);
    }
}
