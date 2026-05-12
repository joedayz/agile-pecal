package com.joedayz.agile.commandpayment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Invocador: no construye la lógica de aprobar/rechazar; solo guarda y ejecuta comandos.
 * Así puedes encolar operaciones (batch nocturno, jobs, deshacer en otras variantes).
 */
public class PaymentInvoker {

    private final Deque<PaymentCommand> cola = new ArrayDeque<>();

    public void encolar(PaymentCommand comando) {
        cola.addLast(comando);
    }

    public List<PaymentCommand> ejecutarTodos() {
        List<PaymentCommand> ejecutados = new ArrayList<>();
        while (!cola.isEmpty()) {
            PaymentCommand cmd = cola.removeFirst();
            cmd.ejecutar();
            ejecutados.add(cmd);
        }
        return ejecutados;
    }
}
