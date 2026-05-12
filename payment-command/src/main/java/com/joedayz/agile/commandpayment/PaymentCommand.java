package com.joedayz.agile.commandpayment;

/**
 * Command: encapsula una solicitud como objeto. Permite parametrizar clientes con distintas acciones,
 * encolar operaciones y desacoplar quien invoca de quien ejecuta.
 */
@FunctionalInterface
public interface PaymentCommand {

    void ejecutar();
}
