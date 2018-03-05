import React from 'react';
import PropTypes from 'prop-types';
import Moment from 'react-moment';
import roundTo from '../../helpers/roundTo';
import weatherIcons from '../../helpers/weatherIcons';

const Forecast = props => (
  props.data.length > 0 &&
    <table className={`wwise-forecast ${props.className}`}>
      <tbody>
        <tr>
          {props.data.map(entry => (
            <td key={entry.date}>
              <div className="dateTime">
                <Moment format={props.format}>
                  {entry.date}
                </Moment>
              </div>
              <h3>{roundTo(entry.temp, 1)}˚</h3>
              <span className="conditions-icon">
                {weatherIcons[entry.conditions] &&
                  weatherIcons[entry.conditions].unicode}
              </span>
              <h4>{entry.conditions}</h4>
              { props.minmax &&
                <div className="minmax">
                  <span className="temp max">{roundTo(entry.max, 1)}</span>
                  <span className="temp min">{roundTo(entry.min, 1)}</span>
                </div>}
            </td>
          ))}
        </tr>
      </tbody>
    </table>
);

Forecast.propTypes = {
  data: PropTypes.arrayOf(PropTypes.shape({
    conditions: PropTypes.string,
    temp: PropTypes.number,
    max: PropTypes.number,
    min: PropTypes.number,
    map: PropTypes.func,
  })),
  format: PropTypes.string,
  minmax: PropTypes.bool,
  className: PropTypes.string,
  opacity: PropTypes.number,
  hide: PropTypes.bool,
};

Forecast.defaultProps = {
  data: {
    conditions: null,
    temp: 0,
    max: 0,
    min: 0,
    map: null,
  },
  format: null,
  minmax: false,
  className: '',
  opacity: 1,
  hide: false,
};

export default Forecast;
