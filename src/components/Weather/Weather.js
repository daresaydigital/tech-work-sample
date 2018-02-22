import React, { Component } from 'react';
import Time from 'react-time-format';
import Moment from 'react-moment';
import PropTypes from 'prop-types';

function roundTo(number, places) {
  return Math.round(number * (10 ** places)) / (10 ** places);
}

export default class Weather extends Component {
  constructor() {
    super();
    this.state = {
      time: null,
    };
  }

  componentWillMount() {
    setInterval(() => {
      const time = new Date();
      this.setState({ time });
    }, 1000);
  }

  render() {
    return (
      <div className="wwise-weather">
        {this.state.time &&
          <div className="dateTime">
            <Moment className="moment date" format="dddd, MMM Do">
              {this.state.time}
            </Moment>
            <Moment className="moment time" format="HH:mm:ss">
              {this.state.time}
            </Moment>
          </div>
        }
        <h1 className="temp">
          {roundTo(this.props.data.temp, 1)}
        </h1>
        <h2>
          {this.props.data.conditions}
        </h2>
        <div className="minmax">
          <span className="temp max">{roundTo(this.props.data.max, 1)}</span>
          <span className="temp min">{roundTo(this.props.data.min, 1)}</span>
        </div>
      </div>
    );
  }
}

Weather.propTypes = {
  data: PropTypes.shape({
    conditions: PropTypes.string,
    temp: PropTypes.number,
    max: PropTypes.number,
    min: PropTypes.number,
    summary: PropTypes.string,
  }),
};

Weather.defaultProps = {
  data: {
    conditions: null,
    temp: null,
    max: null,
    min: null,
    summary: null,
  },
};
