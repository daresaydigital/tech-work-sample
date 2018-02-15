import React from 'react';
import PropTypes from 'prop-types';
import Moment from 'react-moment';
import roundTo from '../../../helpers/roundTo';

const Hourly = props => (
  <table>
    <tbody>
      {props.data.map(entry => (
        <tr key={entry.date}>
          <td>
            <h2>
              <Moment format="HH:mm">
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
  data: PropTypes.arrayOf(PropTypes.shape({
    conditions: PropTypes.string,
    temp: PropTypes.number,
    max: PropTypes.number,
    min: PropTypes.number,
    map: PropTypes.func,
  })),
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
