import { all, fork, takeLatest } from 'redux-saga/effects'

import { CURRENT_POSITION_REQUEST } from '../actions/currentPositionRequest';
import getCurrentPosition from './getCurrentPosition';

import { WEATHER_REQUEST } from '../actions/weatherRequest';
import requestWeather from './requestWeather';

import { FORECAST_REQUEST } from '../actions/forecastRequest';
import requestForecast from './requestForecast';

export function* watchForCurrentPositionRequest() {
  yield takeLatest(CURRENT_POSITION_REQUEST, getCurrentPosition);
}

export function* watchForRequestWeather() {
  yield takeLatest(WEATHER_REQUEST, requestWeather)
}

export function* watchForRequestForecast() {
  yield takeLatest(FORECAST_REQUEST, requestForecast)
}

// Register all your watchers
export default function* root() {
  yield all([
    fork(watchForCurrentPositionRequest),
    fork(watchForRequestWeather),
    fork(watchForRequestForecast),
  ]);
}
