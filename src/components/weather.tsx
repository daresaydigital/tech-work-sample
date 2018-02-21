import * as React from 'react';
import { connect } from 'react-redux';
import * as R from 'ramda';
import * as moment from 'moment';
import * as TimeAgo from 'time-ago';
import * as ReactPullToRefresh from 'react-pull-to-refresh';
import Forecast from './forecast';
import weatherRequest from '../actions/weatherRequest';
import changePage from '../actions/changePage';

interface Props extends React.Props<Weather> {
  weather?: any;
  changePage?: (name: string) => void;
  weatherRequest?: (query: object, showProgress?: boolean) => void;
}

// Nice tsx guide: https://github.com/Microsoft/TypeScript/wiki/JSX
export class Weather extends React.Component<Props, {}> {

  backToChangeCity = () => {
    this.props.changePage('search');
  }

  handleRefresh = (resolve, reject) => {
    this.props.weatherRequest(this.props.weather.query);
  }

  handleChangeUnits = (units) => () => {
    var { query } = this.props.weather;
    if (units == query.units) {
      return false;
    }
    this.props.weatherRequest({
      ...query,
      units
    }, false);
  };

  public render() {
    var { query, json } = this.props.weather;

    var { temp, temp_min, temp_max, humidity } = R.mapObjIndexed(
      num => Math.round(num),
      json.main
    );

    var { weather: [info], wind: { speed } } = json;

    var { units } = query;

    var iconClass = ('wi wi-owm-' + info.id);

    var unitClass = (unitElm) => {
      var normalUnit = { metric: 'celsius', imperial: 'fahrenheit' }[units];
      return ('wi wi-' + unitElm + ' d-block ' + (unitElm != normalUnit ? 'clickable light' : ''));
    };

    return (
      <ReactPullToRefresh onRefresh={this.handleRefresh}>
        <div id="weather-div">

          <div className="row">

            <div className="col-md-8 offset-md-2">
              <span className="text-small light clickable" onClick={this.backToChangeCity}>
                <i className="wi wi-direction-left" /> Change city
              </span>
            </div>

            <div className="col-md-8 offset-md-2">
              <div className="h5 text-capitalize text-center">
                {json.name}
              </div>
            </div>

            <div className="col-md-8 offset-md-2">

              <div className="row align-items-center temp-row">
                <div className="col-6 text-center">
                  <div className="weather-icon">
                    <i className={iconClass}></i>
                  </div>
                </div>
                <div className="col-6 text-center">
                  <div className="row no-gutters">
                    <div className="col-6 text-right weather-temp">
                      {temp}
                    </div>
                    <div className="col-6 text-left change-units">
                      <i className={unitClass('celsius')} onClick={this.handleChangeUnits('metric')} />
                      <i className={unitClass('fahrenheit')} onClick={this.handleChangeUnits('imperial')} />
                    </div>
                  </div>
                  <div className="row no-gutters">
                    <div className="col-6 text-right text-nowrap">
                      <i className="wi wi-direction-up" /> {temp_max}°
                    </div>
                    <div className="col-6 text-left text-nowrap">
                      <i className="wi wi-direction-down" /> {temp_min}°
                    </div>
                  </div>
                </div>
              </div>

            </div>

            <div className="col-md-8 offset-md-2">
              <div className="row">
                <div className="col text-center">
                  <i className="wi wi-humidity" /> {humidity}
                </div>
                <div className="col text-center">
                  <div className="text-capitalize">
                    {info.main}
                  </div>
                  <div className="text-small light">
                    {moment(json.dt * 1000).format('ddd hh:mm A')}
                  </div>
                </div>
                <div className="col text-center">
                  <i className="wi wi-strong-wind" /> {Math.round(speed)} <span className="text-small">km/h</span>
                </div>
              </div>
              <div className="row">
                <div className="col text-left text-nowrap">
                  <span className="text-small light">
                    <i className="wi wi-refresh" /> {TimeAgo.ago(json.dt * 1000)}
                  </span>
                </div>
                <div className="col text-right text-nowrap">
                  <span className="text-small light">
                    Powered by Forecast
                </span>
                </div>
              </div>

              <div className="divider light" />
            </div>

            <div className="col-md-8 offset-md-2">
              <Forecast />
            </div>
          </div>
        </div>
      </ReactPullToRefresh>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    weather: state.weather
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    changePage: (name: string) => {
      return dispatch(changePage(name));
    },
    weatherRequest: (query: object, showProgress: boolean) => {
      return dispatch(weatherRequest(query, showProgress));
    }
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Weather);
