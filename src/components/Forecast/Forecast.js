import React from 'react';
import PropTypes from 'prop-types';
import Moment from 'react-moment';
import roundTo from '../../helpers/roundTo';
import weatherIcons from '../../helpers/weatherIcons';

const sameDay = (d1, d2) =>
  d1.getFullYear() === d2.getFullYear() &&
    d1.getMonth() === d2.getMonth() &&
    d1.getDate() === d2.getDate();

const Forecast = props => (
  props.data.length > 0 &&
    <div className={`wwise-forecast ${props.className}`}>
      <table>
        <tbody>
          <tr>
            {props.data.map((entry) => {
              let format;
              if (props.format === 'daily') {
                format = 'dddd, MMM Do';
              } else {
                /* For the hourly forecast, I compare the date of each
                entry with the current date to format them accordingly. All dates
                for the current day will only show the time of the day */
                const now = new Date();
                const then = new Date(entry.date);
                format = sameDay(now, then) ? 'HH:mm' : 'HH:mm - ddd D';
              }
              return (
                <td key={entry.date}>
                  <div className="dateTime">
                    <Moment format={format}>
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
              );
            })}
          </tr>
        </tbody>
      </table>
      {props.cta &&
        <span className="cta">{props.cta}</span>
      }
    </div>
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
