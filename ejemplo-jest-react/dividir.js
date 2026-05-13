function dividir(a, b) {

  if (b === 0) {
    throw new Error('No se puede dividir');
  }

  return a / b;
}

module.exports = { dividir };
