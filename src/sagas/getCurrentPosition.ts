import { call, put } from 'redux-saga/effects'
import Api from '../api';
import weatherRequest from '../actions/weatherRequest';
import changePage from '../actions/changePage';

export default function* (action) {
  try {
    var coords = yield call(Api.getCurrentPosition);
    yield put(weatherRequest({lat: coords.latitude, lon: coords.longitude}));
  } catch (e) {
    yield put(changePage('search'));
  }
};
