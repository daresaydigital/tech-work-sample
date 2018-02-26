import { call, put } from 'redux-saga/effects'
import Api from '../api';
import forecastCompleted from '../actions/forecastCompleted';
import changePage from '../actions/changePage';

export default function* (action) {
  try {
    var { query } = action;
    var json = yield call(Api.invokeForecast, query);
    yield put(forecastCompleted(query, json));
    yield put(changePage('weather'));
  } catch (e) {
    yield put(changePage('oops'));
  }
}
