import React from 'react';
import PropTypes from 'prop-types';
import Moment from 'react-moment';
import roundTo from '../../../helpers/roundTo';

const Daily = props => (
  <table>
    <tbody>
      <tr>
        {props.data.map(entry => (
          <td>
            <h2>
              <Moment format="dddd, MMM Do">
                {entry.date}
              </Moment>
            </h2>
            <h3>{roundTo(entry.temp, 1)}˚ {entry.conditions}</h3>
            <p>Max {roundTo(entry.max, 1)}˚ | Min {roundTo(entry.min, 1)}˚</p>
          </td>
        ))}
      </tr>
    </tbody>
  </table>
);

Daily.propTypes = {
  data: PropTypes.shape({
    conditions: PropTypes.string,
    temp: PropTypes.number,
    max: PropTypes.number,
    min: PropTypes.number,
    map: PropTypes.func,
  }),
};

Daily.defaultProps = {
  data: {
    conditions: null,
    temp: null,
    max: null,
    min: null,
    map: null,
  },
};

export default Daily;
