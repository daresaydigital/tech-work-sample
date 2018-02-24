const roundTo = (number, places) => {
  return typeof number === 'undefined' ? 0 : Math.round(number * (10 ** places)) / (10 ** places);
}
module.exports = roundTo;
