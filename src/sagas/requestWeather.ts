import { call, put } from 'redux-saga/effects'
import Api from '../api';
import weatherRequest from '../actions/weatherRequest';
import weatherCompleted from '../actions/weatherCompleted';
import forecastRequest from '../actions/forecastRequest';
import changePage from '../actions/changePage';

export default function* (action) {
  try {
    var { query, showProgress } = action;
    if (showProgress) {
      yield put(changePage('progress'));
    }
    var json = yield call(Api.invokeWeather, query);
    if (json.cod == '404') {
      if (!query.q) {
        throw new Error(json.message);
      }
      var latLng = yield call(Api.getLatLngByAddress, query.q);
      query = {
        ...query,
        q: undefined,
        lat: latLng.lat,
        lon: latLng.lng
      };
      return yield put(weatherRequest(query));
    }
    yield put(weatherCompleted(query, json));
    yield put(forecastRequest(query));
  } catch (e) {
    yield put(changePage('oops'));
  }
}
