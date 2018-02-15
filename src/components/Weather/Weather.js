import React, { Component } from 'react';
import Time from 'react-time-format';
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
      <div className="da-container da-weather">
        <h1>
          {<Time value={this.state.time} format="hh:mm:ss" />}
        </h1>
        <h3>{roundTo(this.props.data.temp, 1)}˚ {this.props.data.conditions}</h3>
        <p>Max {roundTo(this.props.data.max, 1)}˚ | Min {roundTo(this.props.data.min, 1)}˚</p>
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
