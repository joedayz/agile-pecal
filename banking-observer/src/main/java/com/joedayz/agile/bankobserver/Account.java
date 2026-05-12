package com.joedayz.agile.bankobserver;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Subject (observable): el saldo cambia y todos los suscriptores reciben el evento.
 */
public class Account {
    private final String id;
    private final String holderName;
    private final List<BalanceObserver> observers = new ArrayList<>();
    private double balance;

    public Account(String id, String holderName, double initialBalance) {
        this.id = Objects.requireNonNull(id);
        this.holderName = Objects.requireNonNull(holderName);
        this.balance = initialBalance;
    }

    public String getId() {
        return id;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void subscribe(BalanceObserver observer) {
        Objects.requireNonNull(observer);
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(BalanceObserver observer) {
        observers.remove(observer);
    }

    public List<BalanceObserver> observersSnapshot() {
        return Collections.unmodifiableList(new ArrayList<>(observers));
    }

    public void applyMovement(double delta, String reason) {
        double previous = balance;
        balance += delta;
        notifyAllObservers(previous, balance, reason);
    }

    private void notifyAllObservers(double previous, double current, String reason) {
        BalanceEvent event = new BalanceEvent(previous, current, reason, Instant.now());
        for (BalanceObserver observer : new ArrayList<>(observers)) {
            observer.onBalanceChanged(this, event);
        }
    }
}
