package com.joedayz.agile.bankobserver;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountObserverTest {

    @Test
    void all_subscribers_receive_each_balance_change() {
        Account account = new Account("X-1", "Test", 100.0);
        List<Double> seen = new ArrayList<>();
        account.subscribe((a, e) -> seen.add(e.newBalance()));
        account.subscribe((a, e) -> seen.add(e.newBalance()));

        account.applyMovement(50, "dep");
        account.applyMovement(-20, "wd");

        assertEquals(List.of(150.0, 150.0, 130.0, 130.0), seen);
    }

    @Test
    void unsubscribe_stops_notifications() {
        Account account = new Account("X-2", "Test", 0);
        List<String> log = new ArrayList<>();
        BalanceObserver o = (a, e) -> log.add("x");
        account.subscribe(o);
        account.applyMovement(1, "a");
        account.unsubscribe(o);
        account.applyMovement(1, "b");
        assertEquals(List.of("x"), log);
    }
}
