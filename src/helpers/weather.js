import axios from 'axios';
import serialize from './serialize';
import API_KEY from './apikey';

const weather = args =>
  new Promise((resolve, reject) =>
    navigator.geolocation.getCurrentPosition(pos =>
      axios.get(`https://worksample-api.herokuapp.com/${args.endpoint}?lat=${pos.coords.latitude}&lon=${pos.coords.longitude}${serialize(args.params)}&key=${API_KEY}`)
        .then(result => resolve(result))
        .catch(e => reject(e)), e => reject(e)));

module.exports = weather;
