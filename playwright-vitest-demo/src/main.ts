import { validateInscripcion, type FormFields } from "./validation";

const form = document.getElementById("form-inscripcion") as HTMLFormElement;
const success = document.getElementById("exito") as HTMLParagraphElement;
const errorNombre = document.getElementById("error-nombre")!;
const errorEmail = document.getElementById("error-email")!;
const errorTelefono = document.getElementById("error-telefono")!;
const errorMensaje = document.getElementById("error-mensaje")!;
const errorPolitica = document.getElementById("error-politica")!;

const fieldIds: Record<keyof FormFields, string> = {
  nombre: "nombre",
  email: "email",
  telefono: "telefono",
  mensaje: "mensaje",
  aceptoPolitica: "acepto",
};

function readFields(): FormFields {
  return {
    nombre: (document.getElementById(fieldIds.nombre) as HTMLInputElement).value,
    email: (document.getElementById(fieldIds.email) as HTMLInputElement).value,
    telefono: (document.getElementById(fieldIds.telefono) as HTMLInputElement).value,
    mensaje: (document.getElementById(fieldIds.mensaje) as HTMLTextAreaElement).value,
    aceptoPolitica: (document.getElementById(fieldIds.aceptoPolitica) as HTMLInputElement).checked,
  };
}

function clearErrors(): void {
  [errorNombre, errorEmail, errorTelefono, errorMensaje, errorPolitica].forEach((el) => {
    el.textContent = "";
    el.hidden = true;
  });
  success.hidden = true;
}

function showErrors(errors: Partial<Record<keyof FormFields, string>>): void {
  const map: [keyof FormFields, HTMLElement][] = [
    ["nombre", errorNombre],
    ["email", errorEmail],
    ["telefono", errorTelefono],
    ["mensaje", errorMensaje],
    ["aceptoPolitica", errorPolitica],
  ];
  for (const [key, el] of map) {
    const msg = errors[key];
    if (msg) {
      el.textContent = msg;
      el.hidden = false;
    } else {
      el.textContent = "";
      el.hidden = true;
    }
  }
}

form.addEventListener("submit", (e) => {
  e.preventDefault();
  clearErrors();
  const { valid, errors } = validateInscripcion(readFields());
  if (!valid) {
    showErrors(errors);
    return;
  }
  success.hidden = false;
  form.reset();
});

["nombre", "email", "telefono", "mensaje"].forEach((id) => {
  document.getElementById(id)?.addEventListener("input", () => {
    if (!(document.getElementById(`error-${id}`) as HTMLElement).hidden) {
      const { errors } = validateInscripcion(readFields());
      const key = id as keyof FormFields;
      const el = document.getElementById(`error-${id}`) as HTMLElement;
      const msg = errors[key];
      if (msg) {
        el.textContent = msg;
        el.hidden = false;
      } else {
        el.textContent = "";
        el.hidden = true;
      }
    }
  });
});

document.getElementById("acepto")?.addEventListener("change", () => {
  if (!errorPolitica.hidden) {
    const { errors } = validateInscripcion(readFields());
    const msg = errors.aceptoPolitica;
    if (msg) {
      errorPolitica.textContent = msg;
      errorPolitica.hidden = false;
    } else {
      errorPolitica.textContent = "";
      errorPolitica.hidden = true;
    }
  }
});
