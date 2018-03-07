/* global document chrome */
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Logo from './components/Logo';
import Weather from './components/Weather';
import ScrollSection from './components/ScrollSection';
import Forecast from './components/Forecast';
import weatherIcons from './helpers/weatherIcons';
import './styles.css';

class NewTab extends Component {
  constructor() {
    super();
    this.state = {
      frontend: {
        config: {
          language: 'en',
          units: 'metric',
        },
        data: {
          weather: {},
          forecast: {
            hourly: [],
            daily: [],
          },
        },
      },
    };
  }

  componentDidMount() {
    const local = this;
    const port = chrome.runtime.connect({ name: 'stateConn' });
    local.setState({ port });
    port.postMessage({ type: 'get' });
    port.onMessage.addListener((msg) => {
      if (msg.state) {
        const frontend = msg.state;
        local.setState({ frontend, loading: false });
      }
    });
  }

  render() {
    /* Set the style based on the current weather conditions */
    let weatherStyle = 'clear';
    if (typeof weatherIcons[this.state.frontend.data.weather.conditions] !== 'undefined') {
      weatherStyle = weatherIcons[this.state.frontend.data.weather.conditions].className;
    }
    return (
      <ScrollSection
        className={`wwise-app ${weatherStyle}`}
        element="div"
      >
        <Logo
          url="http://www.pedroese.com"
          alt="Go to my website, http://www.pedroese.com/"
        />
        <Weather
          data={this.state.frontend.data.weather}
          hide
        />
        <section
          className="wwise-slider"
          onWheel={this.wheelHandler}
        >
          <Forecast
            data={this.state.frontend.data.forecast.hourly}
            format="hourly"
            cta="Scroll right for the weekly forecast"
          />
          <Forecast
            data={this.state.frontend.data.forecast.daily}
            format="daily"
            minmax
            hide
          />
        </section>
      </ScrollSection>
    );
  }
}

ReactDOM.render(<NewTab />, document.getElementById('root'));
