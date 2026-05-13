import { useState } from 'react';

function LoginForm() {
  const [mensaje, setMensaje] = useState('');

  function handleSubmit(e) {
    e.preventDefault();
    setMensaje('Login correcto');
  }

  return (
    <form onSubmit={handleSubmit}>
      <input placeholder="Usuario" />
      <input type="password" placeholder="Password" />

      <button type="submit">Ingresar</button>

      {mensaje && <p>{mensaje}</p>}
    </form>
  );
}

export default LoginForm;
