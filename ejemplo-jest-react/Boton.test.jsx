import { render, screen } from '@testing-library/react';
import Boton from './Boton';

test('muestra texto Guardar', () => {
  render(<Boton />);

  expect(screen.getByText('Guardar')).toBeInTheDocument();
});
