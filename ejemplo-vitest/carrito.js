// ─── carrito.js ───────────────────────────────────────────────────────────────
// Clase con estado: muestra cómo testear objetos más complejos.

export class Carrito {
  #items = [];

  agregar(producto) {
    this.#items.push(producto);
  }

  eliminar(nombre) {
    this.#items = this.#items.filter((p) => p.nombre !== nombre);
  }

  total() {
    return this.#items.reduce((acc, p) => acc + p.precio, 0);
  }

  get cantidad() {
    return this.#items.length;
  }

  vaciar() {
    this.#items = [];
  }
}
