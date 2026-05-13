import express from "express";

const app = express();
app.use(express.json());

/** @type {Array<{ id: string, titulo: string, autor: string, anioPublicacion: number }>} */
let libros = [
  { id: "1", titulo: "Clean Code", autor: "Robert C. Martin", anioPublicacion: 2008 },
  { id: "2", titulo: "The Pragmatic Programmer", autor: "Hunt / Thomas", anioPublicacion: 1999 },
];

function nextId() {
  const max = libros.reduce((m, l) => Math.max(m, Number.parseInt(l.id, 10) || 0), 0);
  return String(max + 1);
}

function error(res, status, code, message) {
  return res.status(status).json({ error: { code, message } });
}

app.get("/health", (_req, res) => {
  res.json({ status: "ok", version: "1.0.0" });
});

app.get("/libros", (_req, res) => {
  res.json({ data: libros });
});

app.get("/libros/:id", (req, res) => {
  const libro = libros.find((l) => l.id === req.params.id);
  if (!libro) return error(res, 404, "NOT_FOUND", "Libro no encontrado");
  res.json({ data: libro });
});

app.post("/libros", (req, res) => {
  const { titulo, autor, anioPublicacion } = req.body ?? {};
  if (typeof titulo !== "string" || titulo.trim().length === 0) {
    return error(res, 400, "VALIDATION", "titulo es obligatorio");
  }
  if (typeof autor !== "string" || autor.trim().length === 0) {
    return error(res, 400, "VALIDATION", "autor es obligatorio");
  }
  const anio = Number(anioPublicacion);
  if (!Number.isInteger(anio) || anio < 1000 || anio > 2100) {
    return error(res, 400, "VALIDATION", "anioPublicacion debe ser un entero entre 1000 y 2100");
  }
  const nuevo = {
    id: nextId(),
    titulo: titulo.trim(),
    autor: autor.trim(),
    anioPublicacion: anio,
  };
  libros = [...libros, nuevo];
  res.status(201).json({ data: nuevo });
});

const port = Number(process.env.PORT) || 3030;
app.listen(port, () => {
  console.log(`API demo en http://localhost:${port}`);
});
