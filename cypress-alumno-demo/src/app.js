let contador = 0;

const elContador = document.querySelector("[data-cy=contador]");
const elBtnSumar = document.querySelector("[data-cy=btn-sumar]");
const elNombre = document.querySelector("[data-cy=nombre]");
const elBtnSaludar = document.querySelector("[data-cy=btn-saludar]");
const elMensaje = document.querySelector("[data-cy=mensaje]");

elBtnSumar.addEventListener("click", () => {
  contador += 1;
  elContador.textContent = String(contador);
});

elBtnSaludar.addEventListener("click", () => {
  const nombre = elNombre.value.trim();
  if (!nombre) {
    elMensaje.textContent = "Escribe un nombre primero.";
    elMensaje.hidden = false;
    return;
  }
  elMensaje.textContent = `Hola, ${nombre}. ¡Bienvenida/o!`;
  elMensaje.hidden = false;
});
