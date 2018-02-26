import * as React from 'react';
import * as moment from 'moment';

interface Props extends React.Props<DayItem> {
  dt: number;
  weather: [{id: number, main: string}];
  temp: {min: number, max: number};
}

// Nice tsx guide: https://github.com/Microsoft/TypeScript/wiki/JSX
export default class DayItem extends React.PureComponent<Props, {}> {
  public render() {
    const {
      dt,
      weather: [{id, main}],
      temp: {min, max}
    } = this.props;
    var weatherClass = id ? ('wi wi-owm-' + id) : 'wi wi-na';
    return (
      <div className="col">
        <div className="text-center day-name">
          <span className="text-small">
            { moment(dt * 1000).format('ddd') }
          </span>
        </div>
        <div className="text-center day-icon">
          <i className={ weatherClass }></i>
        </div>
        <div className="text-center day-temp">
          <span className="text-nowrap">
            { Math.round(max) }° <span className="day-min">{ Math.round(min) }°</span>
          </span>
        </div>
      </div>
    );
  }
}
