import React from 'react';
import PropTypes from 'prop-types';
import Moment from 'react-moment';
import roundTo from '../../../helpers/roundTo';

/* function roundTo(number, places) {
  return Math.round(number * (10 ** places)) / (10 ** places);
} */

const Hourly = props => (
  <table>
    <tbody>
      {props.data.map(entry => (
        <tr>
          <td>
            <h2>
              <Moment format="h:mm:ss">
                {entry.date}
              </Moment>
            </h2>
          </td>
          <td>
            <h3>{roundTo(entry.temp, 1)}˚ {entry.conditions}</h3>
          </td>
          <td>
            <p>Max {roundTo(entry.max, 1)}˚ | Min {roundTo(entry.min, 1)}˚</p>
          </td>
        </tr>
      ))}
    </tbody>
  </table>
);

Hourly.propTypes = {
  data: PropTypes.shape({
    conditions: PropTypes.string,
    temp: PropTypes.number,
    max: PropTypes.number,
    min: PropTypes.number,
    map: PropTypes.func,
  }),
};

Hourly.defaultProps = {
  data: {
    conditions: null,
    temp: null,
    max: null,
    min: null,
    map: null,
  },
};

export default Hourly;
