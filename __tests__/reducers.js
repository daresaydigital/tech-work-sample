import changePage, { CHANGE_PAGE } from '../src/actions/changePage'
import currentPositionRequest, { CURRENT_POSITION_REQUEST } from '../src/actions/currentPositionRequest'
import forecastCompleted, { FORECAST_COMPLETED } from '../src/actions/forecastCompleted'
import forecastRequest, { FORECAST_REQUEST } from '../src/actions/forecastRequest'
import weatherCompleted, { WEATHER_COMPLETED } from '../src/actions/weatherCompleted'
import weatherRequest, { WEATHER_REQUEST } from '../src/actions/weatherRequest'

import {
  page as pageReducer,
  weather as weatherReducer,
  forecast as forecastReducer
} from '../src/reducers';

describe('Reducers', () => {
  describe('page reducer', () => {
    it('should return the initial state', () => {
      expect(pageReducer(undefined, {})).toEqual('progress')
    })

    it('should handle CHANGE_PAGE', () => {
      const page = 'test'
      expect(pageReducer(null, changePage(page))).toEqual(page)
    })
  })

  describe('current position action', () => {
    it('should handle CURRENT_POSITION_REQUEST', () => {
      expect(currentPositionRequest()).toHaveProperty('type', CURRENT_POSITION_REQUEST)
    })
  })

  describe('weather reducer', () => {
    it('should return the initial state', () => {
      expect(weatherReducer(undefined, {})).toEqual({
        query: {},
        json: {}
      })
    })

    it('should handle WEATHER_REQUEST', () => {
      expect(weatherReducer(undefined, weatherRequest({
        units: 'metric'
      }))).toHaveProperty('query.units', 'metric')
    })

    it('should handle WEATHER_COMPLETED', () => {
      expect(weatherReducer(undefined, weatherCompleted({}, {
        temp: 1
      }))).toHaveProperty('json.temp', 1)
    })
  })

  describe('forecast reducer', () => {
    it('should return the initial state', () => {
      expect(forecastReducer(undefined, {})).toEqual({
        query: {},
        json: {}
      })
    })

    it('should handle FORECAST_REQUEST', () => {
      expect(forecastReducer(undefined, forecastRequest({
        units: 'metric'
      }))).toHaveProperty('query.units', 'metric')
    })

    it('should handle FORECAST_COMPLETED', () => {
      expect(forecastReducer(undefined, forecastCompleted({}, {
        temp: 1
      }))).toHaveProperty('json.temp', 1)
    })
  })
})
