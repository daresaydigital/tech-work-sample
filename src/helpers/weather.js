import axios from 'axios';
import serialize from './serialize';
import { WEATHER_KEY } from './apikey';

const apiConnect = (resolve, reject, args, location) =>
  axios.get(`https://worksample-api.herokuapp.com/${args.endpoint}?lat=${location.lat}&lon=${location.lng}${serialize(args.params)}&key=${WEATHER_KEY}`)
    .then(result => resolve(result))
    .catch(e => reject(e));

const weather = args =>
  new Promise((resolve, reject) => {
    if (typeof args.location === 'undefined') {
      navigator.geolocation.getCurrentPosition((pos) => {
        /* Unify the position object structure with the one used by the geocoding API */
        const location = {
          lat: pos.coords.latitude,
          lng: pos.coords.longitude,
        };
        return apiConnect(resolve, reject, args, location);
      }, e => reject(e));
    } else {
      apiConnect(resolve, reject, args, args.location);
    }
  });

module.exports = weather;
