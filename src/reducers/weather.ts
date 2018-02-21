import * as objectAssign from 'object-assign';
import { WEATHER_REQUEST } from '../actions/weatherRequest'
import { WEATHER_COMPLETED } from '../actions/weatherCompleted';

class Weather {
  query: Object;
  json: Object;

  public constructor() {
    this.query = {};
    this.json = {};
  }
}

// Just to show how combine reducers work, we have
// divided into two reducers member load + member load/update/delete
export default (state: Weather = new Weather, action) => {
  let newState: Weather = null;

  switch (action.type) {
    case WEATHER_REQUEST:
      var { query } = action;
      newState = objectAssign({}, state, { query });
      return newState;


    case WEATHER_COMPLETED:
      var { json, query } = action;
      newState = objectAssign({}, state, { json, query });
      return newState;

    default:
      return state;
  }
};
