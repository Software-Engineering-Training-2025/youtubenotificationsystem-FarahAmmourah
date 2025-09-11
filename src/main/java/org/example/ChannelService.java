package org.example;

import org.example.subject.Channel;
//import org.example.subject.Subject;
import org.example.observer.Observer;
import java.util.*;
import java.util.stream.Collectors;

public class ChannelService {
    private final Map<String, Channel> channels = new HashMap<>();

    public boolean createChannel(String name) {
        return Optional.ofNullable(name)
                .filter(n -> !n.isBlank())
                .filter(n -> !channels.containsKey(n))
                .map(n -> channels.put(n, new Channel(n)) == null)
                .orElse(false);
    }

    public boolean deleteChannel(String name) {
        return Optional.ofNullable(channels.remove(name)).isPresent();
    }

    public Optional<Channel> getChannel(String name) {
        return Optional.ofNullable(channels.get(name));
    }

    public boolean subscribe(String channelName, Observer observer) {
        return getChannel(channelName)
                .map(ch -> { ch.subscribe(observer); return true; })
                .orElse(false);
    }

    public boolean unsubscribe(String channelName, Observer observer) {
        return getChannel(channelName)
                .map(ch -> { ch.unsubscribe(observer); return true; })
                .orElse(false);
    }

    public boolean upload(String channelName, String videoTitle) {
        return getChannel(channelName)
                .map(ch -> { ch.uploadVideo(videoTitle); return true; })
                .orElse(false);
    }

    public Set<String> listChannels() {
        return channels.keySet()
                .stream()
                .collect(Collectors.toUnmodifiableSet());
    }
}
