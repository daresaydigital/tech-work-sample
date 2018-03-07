const serialize = (obj) => {
  const str = [];
  const arr = Object.entries(obj);
  arr.forEach((key) => {
    str.push(`${encodeURIComponent(key[0])}=${encodeURIComponent(key[1])}`);
  });
  return `&${str.join('&')}`;
};

module.exports = serialize;
