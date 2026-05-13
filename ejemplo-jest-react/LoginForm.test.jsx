import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import LoginForm from './LoginForm';

test('muestra formulario y Login correcto al enviar', async () => {
  render(<LoginForm />);

  // Buscar inputs
  const usuarioInput =
      screen.getByPlaceholderText('Usuario');

  const passwordInput =
      screen.getByPlaceholderText('Password');

  // Buscar botón
  const boton =
      screen.getByText('Ingresar');

  // Simular escritura
  await userEvent.type(usuarioInput, 'jose');

  await userEvent.type(passwordInput, '123456');

  // Simular click
  await userEvent.click(boton);

  // Validar resultado
  expect(
      screen.getByText('Login correcto')
  ).toBeInTheDocument();

  // fireEvent.change(screen.getByPlaceholderText('Usuario'), {
  //   target: { value: 'ana' },
  // });
  // fireEvent.change(screen.getByPlaceholderText('Password'), {
  //   target: { value: 'secreto' },
  // });
  // fireEvent.click(screen.getByRole('button', { name: 'Ingresar' }));
  //
  // expect(screen.getByText('Login correcto')).toBeInTheDocument();
});
