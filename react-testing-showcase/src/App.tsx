import { UserPanel } from "./components/UserPanel";
import "./App.css";

export function App() {
  return (
    <main className="app">
      <header className="app__header">
        <h1>Panel de cuenta</h1>
        <p className="app__subtitle">
          Demo para clase: pruebas unitarias, integración, mocks y snapshots con criterio.
        </p>
      </header>
      <UserPanel />
    </main>
  );
}
