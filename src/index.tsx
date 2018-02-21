import * as React from 'react';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import reducers from './reducers';
import createSagaMiddleware from 'redux-saga';
import * as ReactDOM from 'react-dom';
import sagaWatchers from './sagas/watchersSaga';
import App from './components/app';
import currentPositionRequest from './actions/currentPositionRequest';

import './css/site.scss';
import './css/weather-icons.css';

const sagaMiddleware = createSagaMiddleware();

let store = createStore(
  reducers,
  applyMiddleware(sagaMiddleware)
);

sagaMiddleware.run(sagaWatchers);

store.dispatch(currentPositionRequest());

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);
