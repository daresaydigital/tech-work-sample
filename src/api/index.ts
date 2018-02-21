import * as queryString from 'query-string';
import * as R from 'ramda';
import { geocodeByAddress, getLatLng } from 'react-places-autocomplete'
import config from '../config';

const Api = {
  geocodeByAddress,
  getLatLng,

  getCurrentPosition: () => {
    const promise: Promise<any> = new Promise((resolve, reject) => {
      const coords = localStorage.getItem('current_position');
      if (coords) {
        return resolve(JSON.parse(coords));
      }
      navigator.geolocation.getCurrentPosition(function (position) {
        var coords = R.pipe(
          R.path(['coords']),
          R.pick(['latitude', 'longitude'])
        )(position);
        localStorage.setItem('current_position', JSON.stringify(coords));
        resolve(coords);
      }, function (error) {
        reject(error);
      });
    });
    return promise;
  },

  invokeWeather: (query) => {
    const promise: Promise<any> = new Promise((resolve, reject) => {
      var qs = queryString.stringify({
        ...query,
        key: config.WETHER_API_KEY
      });
      var apiUrl = `http://worksample-api.herokuapp.com/weather?${qs}`;
      return fetch(apiUrl)
        .then(response => resolve(response.json()))
        .catch(err => reject(err));
    });

    return promise;
  },

  invokeForecast: (query) => {
    const promise: Promise<any> = new Promise((resolve, reject) => {
      var qs = queryString.stringify({
        ...query,
        cnt: 7,
        key: config.WETHER_API_KEY
      });
      var apiUrl = `http://worksample-api.herokuapp.com/forecast/daily?${qs}`;
      return fetch(apiUrl)
        .then((response) => resolve(response.json()))
        .catch(err => reject(err));
    });

    return promise;
  },

  getLatLngByAddress: (address) => {
    return Api.geocodeByAddress(address)
      .then(results => Api.getLatLng(results[0]));
  }
};

export default Api;
