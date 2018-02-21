import sagaHelper from 'redux-saga-testing';
import sinon from 'sinon';
import { call, put, takeLatest, fork, all } from 'redux-saga/effects';
import Api from '../src/api'

import changePage, { CHANGE_PAGE } from '../src/actions/changePage'
import currentPositionRequest, { CURRENT_POSITION_REQUEST } from '../src/actions/currentPositionRequest'
import forecastCompleted, { FORECAST_COMPLETED } from '../src/actions/forecastCompleted'
import forecastRequest, { FORECAST_REQUEST } from '../src/actions/forecastRequest'
import weatherCompleted, { WEATHER_COMPLETED } from '../src/actions/weatherCompleted'
import weatherRequest, { WEATHER_REQUEST } from '../src/actions/weatherRequest'

import rootSaga, {
  watchForCurrentPositionRequest,
  watchForRequestWeather,
  watchForRequestForecast
} from '../src/sagas/watchersSaga'
import requestWeather from '../src/sagas/requestWeather'
import requestForecast from '../src/sagas/requestForecast'
import getCurrentPosition from '../src/sagas/getCurrentPosition'


describe('Sagas', () => {

  describe('Root', () => {

    describe('rootSaga', () => {
      const it = sagaHelper(rootSaga())
      it('takeLatest CURRENT_POSITION_REQUEST', result => {
        expect(result).toEqual(all([
          fork(watchForCurrentPositionRequest),
          fork(watchForRequestWeather),
          fork(watchForRequestForecast)
        ]))
      })
    })

    describe('watchForCurrentPositionRequest', () => {
      const it = sagaHelper(watchForCurrentPositionRequest())
      it('takeLatest CURRENT_POSITION_REQUEST', result => {
        expect(result).toEqual(takeLatest('CURRENT_POSITION_REQUEST', getCurrentPosition))
      })
    })

    describe('watchForRequestWeather', () => {
      const it = sagaHelper(watchForRequestWeather())
      it('takeLatest WEATHER_REQUEST', result => {
        expect(result).toEqual(takeLatest('WEATHER_REQUEST', requestWeather))
      })
    })

    describe('watchForRequestForecast', () => {
      const it = sagaHelper(watchForRequestForecast())
      it('takeLatest FORECAST_REQUEST', result => {
        expect(result).toEqual(takeLatest('FORECAST_REQUEST', requestForecast))
      })
    })
  })

  describe('Weather', () => {

    describe('Scenario 1: when every thing is ok', () => {
      const json = { temp: 1 }
      const action = weatherRequest()
      const { query } = action
      const it = sagaHelper(requestWeather(action))

      beforeEach(() => {
        sinon.stub(Api, 'invokeWeather').callsFake(function fakeFn() {
          return Promise.resolve(json)
        });
      })

      afterEach(() => {
        Api.invokeWeather.restore();
      })

      it('change page to progress', result => {
        expect(result).toEqual(put(changePage('progress')))
      });

      it('call api', result => {
        expect(result).toEqual(call(Api.invokeWeather, query))
        return json
      });

      it('update state weather', result => {
        expect(result).toEqual(put(weatherCompleted(query, json)))
      });

      it('request forecast', result => {
        expect(result).toEqual(put(forecastRequest(query)))
      });
    })

    describe('Scenario 2: when address not found and throw', () => {
      const json = { cod: '404' }
      const action = weatherRequest()
      const { query } = action
      const it = sagaHelper(requestWeather(action))

      beforeEach(() => {
        sinon.stub(Api, 'invokeWeather').callsFake(function fakeFn() {
          return Promise.resolve(json)
        });
      })

      afterEach(() => {
        Api.invokeWeather.restore();
      })

      it('change page to progress', result => {
        expect(result).toEqual(put(changePage('progress')))
      });

      it('call api', result => {
        expect(result).toEqual(call(Api.invokeWeather, query))
        return json
      });

      it('oops', result => {
        expect(result).toEqual(put(changePage('oops')))
      });
    })

    describe('Scenario 3: when address not found and request weather by latlng', () => {
      const q = 'wrong';
      const latlng = {
        lat: 1,
        lng: 1
      };
      const json = { cod: '404' }
      const action = weatherRequest({ q })
      const { query } = action
      const it = sagaHelper(requestWeather(action))

      beforeEach(() => {
        sinon.stub(Api, 'invokeWeather').callsFake(function fakeFn() {
          return Promise.resolve(json)
        });
        sinon.stub(Api, 'getLatLngByAddress').callsFake(function fakeFn() {
          return Promise.resolve(latlng)
        });
      })

      afterEach(() => {
        Api.invokeWeather.restore();
        Api.getLatLngByAddress.restore();
      })

      it('change page to progress', result => {
        expect(result).toEqual(put(changePage('progress')))
      });

      it('call api weather', result => {
        expect(result).toEqual(call(Api.invokeWeather, query))
        return json
      });

      it('call latlng api', result => {
        expect(result).toEqual(call(Api.getLatLngByAddress, q))
        return latlng;
      });

      it('request weather again', result => {
        expect(result).toEqual(put(weatherRequest({
          ...query,
          q: undefined,
          lat: latlng.lat,
          lon: latlng.lng
        })))
      })
    })
  })

  describe('Forecast', () => {

    describe('Scenario 1: when every thing is ok', () => {
      const json = { temp: 1 }
      const action = forecastRequest()
      const { query } = action
      const it = sagaHelper(requestForecast(action))

      beforeEach(() => {
        sinon.stub(Api, 'invokeForecast').callsFake(function fakeFn() {
          return Promise.resolve(json)
        });
      })

      afterEach(() => {
        Api.invokeForecast.restore();
      })

      it('call api', result => {
        expect(result).toEqual(call(Api.invokeForecast, query))
        return json
      });

      it('update state weather', result => {
        expect(result).toEqual(put(forecastCompleted(query, json)))
      });

      it('change page to weather', result => {
        expect(result).toEqual(put(changePage('weather')))
      });
    })

    describe('Scenario 2: when an error happen', () => {
      const action = forecastRequest()
      const { query } = action
      const it = sagaHelper(requestForecast(action))

      beforeEach(() => {
        sinon.stub(Api, 'invokeForecast').callsFake(function fakeFn() {
          return Promise.reject()
        });
      })

      afterEach(() => {
        Api.invokeForecast.restore();
      })

      it('call api', result => {
        expect(result).toEqual(call(Api.invokeForecast, query))
        return new Error('oops');
      });

      it('oops', result => {
        expect(result).toEqual(put(changePage('oops')))
      });
    })

  })

  describe('Current position', () => {

    describe('Scenario 1: when every thing is ok', () => {
      const it = sagaHelper(getCurrentPosition())
      const coords = {
        latitude: 1,
        longitude: 1
      };
      beforeEach(() => {
        sinon.stub(Api, 'getCurrentPosition').callsFake(function fakeFn() {
          return Promise.resolve(json)
        });
      })

      afterEach(() => {
        Api.getCurrentPosition.restore();
      })

      it('call api', result => {
        expect(result).toEqual(call(Api.getCurrentPosition))
        return coords
      });

      it('request weather', result => {
        expect(result).toEqual(put(weatherRequest({ lat: coords.latitude, lon: coords.longitude })))
      });
    })

    describe('Scenario 2: when an error happen', () => {
      const it = sagaHelper(getCurrentPosition())
      const coords = {
        latitude: 1,
        longitude: 1
      };
      beforeEach(() => {
        sinon.stub(Api, 'getCurrentPosition').callsFake(function fakeFn() {
          return Promise.reject()
        });
      })

      afterEach(() => {
        Api.getCurrentPosition.restore();
      })

      it('call api', result => {
        expect(result).toEqual(call(Api.getCurrentPosition))
        return new Error('oops')
      });

      it('go to search', result => {
        expect(result).toEqual(put(changePage('search')))
      });
    })

  })

})
