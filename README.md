[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/B2xHAILZ)

# YouTube-Style Notifications (Observer Pattern + ChannelService)

Implement a lightweight notification system using the **Observer pattern** and a `ChannelService` backed by a `HashMap` for channel storage.

## Goal

* Complete the Observer pattern (`Subject` ↔ `Observer`) with `Channel` and `User`.
* Implement `ChannelService` to **create/manage channels, subscriptions, and uploads** using an in-memory `HashMap<String, Channel>`.

---

## Project Structure (given)

```
src/main/java/...
  Observer.java          // interface: observers implement update(...)
  Subject.java           // interface: subjects manage subscribe/unsubscribe/notify
  Channel.java           // concrete Subject (incomplete)
  User.java              // concrete Observer (incomplete)
  ChannelService.java    // service that stores channels in a HashMap (incomplete)
```

> Do **not** add external libraries. Use core Java collections.

---

## What You Must Implement

### 1) Observer Interface (`Observer.java`)

* Method:

  ```java
  void update(String message);
  ```

  Called by a `Subject` to notify the observer.

### 2) Subject Interface (`Subject.java`)

* Methods:

  ```java
  void subscribe(Observer observer);
  void unsubscribe(Observer observer);
  void notifyObservers(String message);
  ```

  Any `Subject` (e.g., `Channel`) must manage its observers.

### 3) Channel (Concrete Subject) — `Channel.java`

* Fields (suggested):

  * `private final String name;`
  * `private final List<Observer> subscribers = new ArrayList<>();`
* Behavior:

  * `subscribe`, `unsubscribe`, `notifyObservers` per `Subject`.
  * `uploadVideo(String title)` → builds message:

    ```
    <ChannelName> uploaded a new video: <title>
    ```

    then calls `notifyObservers(message)`.
* Edge cases:

  * Ignore duplicate subscriptions (optional but recommended).
  * Ignore `null` observers.
  * Unsubscribe is a no-op if the observer isn’t present.

### 4) User (Concrete Observer) — `User.java`

* Fields (suggested):

  * `private final String username;`
* Implement:

  ```java
  @Override
  public void update(String message) {
      System.out.println("Notification for " + username + ": " + message);
  }
  ```
* Provide `getUsername()` to help tests/examples (optional but handy).

### 5) ChannelService — `ChannelService.java`

Use an in-memory map for channels:

```java
private final Map<String, Channel> channels = new HashMap<>();
```

**Required methods (suggested set):**

* `public boolean createChannel(String name)`

  * Returns `false` if a channel with that name already exists or `name` is null/blank.
  * Otherwise creates and stores a new `Channel`.
* `public boolean deleteChannel(String name)`

  * Removes channel if present; returns `true` if removed.
  * If not present, return `false`.
* `public Optional<Channel> getChannel(String name)`

  * Returns `Optional` of the channel.
* `public boolean subscribe(String channelName, Observer user)`

  * Finds channel by name and calls `channel.subscribe(user)`.
  * Return `false` if channel/user invalid; `true` if subscribed (or already subscribed if you allow idempotency).
* `public boolean unsubscribe(String channelName, Observer user)`

  * Finds channel and unsubscribes; return `true` if success, else `false`.
* `public boolean upload(String channelName, String videoTitle)`

  * Delegates to `channel.uploadVideo(videoTitle)`; return `true` on success.
* `public Set<String> listChannels()`

  * Returns current channel names (e.g., `new HashSet<>(channels.keySet())`).

> You may add helper methods if needed (e.g., `exists`, validations).

---

## Example (for your own local test)

```java
public class Main {
    public static void main(String[] args) {
        ChannelService svc = new ChannelService();
        svc.createChannel("TechWorld");
        svc.createChannel("FoodiesUnite");

        User alice = new User("Alice");
        User bob   = new User("Bob");
        User charlie = new User("Charlie");

        svc.subscribe("TechWorld", alice);
        svc.subscribe("TechWorld", bob);

        svc.subscribe("FoodiesUnite", alice);
        svc.subscribe("FoodiesUnite", charlie);

        svc.upload("TechWorld", "Observer Pattern Explained");
        svc.upload("FoodiesUnite", "Best Pasta Recipe");
    }
}
```

**Expected console output:**

```
Notification for Alice: TechWorld uploaded a new video: Observer Pattern Explained
Notification for Bob: TechWorld uploaded a new video: Observer Pattern Explained
Notification for Alice: FoodiesUnite uploaded a new video: Best Pasta Recipe
Notification for Charlie: FoodiesUnite uploaded a new video: Best Pasta Recipe
```

---

## Requirements & Constraints

* Use **Observer pattern** correctly:

  * `Channel` does **not** know concrete `User` type details—only `Observer`.
* Use a **HashMap** inside `ChannelService` to store channels by name.
* Handle **null/blank inputs** gracefully (return `false` or no-op; do not throw unless specified).
* A `User` can subscribe to **multiple channels**; a `Channel` can have **many subscribers**.
* Keep methods **small, clear, and testable**.

---


