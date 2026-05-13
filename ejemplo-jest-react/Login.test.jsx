import { render, screen } from '@testing-library/react';
import Login from './Login';

test('muestra campo Usuario y botón Ingresar', () => {
  render(<Login />);

  expect(screen.getByPlaceholderText('Usuario')).toBeInTheDocument();

  expect(screen.getByText('Ingresar')).toBeInTheDocument();
});
