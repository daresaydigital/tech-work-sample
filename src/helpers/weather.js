import axios from 'axios';
import Geocode from 'react-geocode';
import serialize from './serialize';
import { WEATHER_KEY, GEOCODE_KEY } from './apikey';

Geocode.setApiKey(GEOCODE_KEY);

const apiConnect = (resolve, reject, args, location, formattedAddress) =>
  axios.get(`https://worksample-api.herokuapp.com/${args.endpoint}?lat=${location.lat}&lon=${location.lng}${serialize(args.params)}&key=${WEATHER_KEY}`)
    .then((result) => {
      const response = result;
      /* If using geocoding, include parsed location in response */
      if (formattedAddress) {
        response.data.location = formattedAddress;
      }
      resolve(response);
    })
    .catch(e => reject(e));

const weather = args =>
  new Promise((resolve, reject) => {
    if (typeof args.address === 'undefined') {
      /* If no address is received, use the navigator geolocation */
      navigator.geolocation.getCurrentPosition((pos) => {
        /* Translate the position object structure to the one used by the geocoding API */
        const location = {
          lat: pos.coords.latitude,
          lng: pos.coords.longitude,
        };
        return apiConnect(resolve, reject, args, location);
      }, e => reject(e));
    } else {
      /* Geocode address to obtain position */
      Geocode.fromAddress(args.address).then(
        (response) => {
          const { location } = response.results[0].geometry;
          /* Make sure that the location is not a business, point of interest or
          something like that. Google will always try to return something */
          /* Should I limit this to only results of "locality" type? Weather for
          entire regions or countries is not really useful, but it's easier to
          test this way */
          if (response.results[0].types.some(x => x === 'political')) {
            const formattedAddress = response.results[0].formatted_address;
            apiConnect(resolve, reject, args, location, formattedAddress);
          } else {
            const e = new Error('ZERO_RESULTS');
            reject(e);
          }
        },
        e =>
          reject(e),
      ).catch(e =>
        reject(e));
    }
  });

module.exports = weather;
