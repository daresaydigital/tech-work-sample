import * as objectAssign from 'object-assign';
import { FORECAST_REQUEST } from '../actions/forecastRequest'
import { FORECAST_COMPLETED } from '../actions/forecastCompleted';

class Forecast {
  query: Object;
  json: Object;

  public constructor() {
    this.query = {};
    this.json = {};
  }
}

// Just to show how combine reducers work, we have
// divided into two reducers member load + member load/update/delete
export default (state: Forecast = new Forecast(), action) => {
  let newState: Forecast = null;

  switch (action.type) {
    case FORECAST_REQUEST:
      var { query } = action;
      newState = objectAssign({}, state, { query });
      return newState;

    case FORECAST_COMPLETED:
      var { query, json } = action;
      newState = objectAssign({}, state, { query, json });
      return newState;

    default:
      return state;
  }
};
