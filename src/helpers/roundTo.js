const roundTo = (number, places) =>
  Math.round(number * (10 ** places)) / (10 ** places);

module.exports = roundTo;
