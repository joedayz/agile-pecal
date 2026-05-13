export type FormFields = {
  nombre: string;
  email: string;
  telefono: string;
  mensaje: string;
  aceptoPolitica: boolean;
};

export type FormErrors = Partial<Record<keyof FormFields, string>>;

const emailRe = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

/** Solo dígitos, 9 caracteres (ej. móvil ES sin prefijo internacional). */
const telefonoEsRe = /^\d{9}$/;

export function validateInscripcion(fields: FormFields): {
  valid: boolean;
  errors: FormErrors;
} {
  const errors: FormErrors = {};

  const nombre = fields.nombre.trim();
  if (!nombre) {
    errors.nombre = "El nombre es obligatorio.";
  } else if (nombre.length < 2) {
    errors.nombre = "Escribe al menos 2 caracteres.";
  }

  const email = fields.email.trim();
  if (!email) {
    errors.email = "El correo es obligatorio.";
  } else if (!emailRe.test(email)) {
    errors.email = "Introduce un correo válido.";
  }

  const tel = fields.telefono.trim();
  if (tel && !telefonoEsRe.test(tel)) {
    errors.telefono = "Si lo indicas, usa 9 dígitos (sin espacios).";
  }

  const mensaje = fields.mensaje.trim();
  if (!mensaje) {
    errors.mensaje = "El mensaje es obligatorio.";
  } else if (mensaje.length < 10) {
    errors.mensaje = "El mensaje debe tener al menos 10 caracteres.";
  } else if (mensaje.length > 500) {
    errors.mensaje = "Máximo 500 caracteres.";
  }

  if (!fields.aceptoPolitica) {
    errors.aceptoPolitica = "Debes aceptar la política para continuar.";
  }

  return {
    valid: Object.keys(errors).length === 0,
    errors,
  };
}
