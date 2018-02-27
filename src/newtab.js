/* global document chrome */
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import smoothScrollTo from './modules/smooth-scroll-to';
import Logo from './components/Logo';
import Weather from './components/Weather';
import ScrollSection from './components/ScrollSection';
import Forecast from './components/Forecast';
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
    // document.body.addEventListener('scroll', this.handleScroll, false);
  }

  componentWillUnmount() {
    // document.body.removeEventListener('scroll', this.handleScroll, false);
  }

  render() {
    return (
      <ScrollSection
        className="wwise-app"
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
        <ScrollSection
          element="section"
          className="wwise-slider"
        >
          <Forecast
            data={this.state.frontend.data.forecast.daily}
            format="dddd, MMM Do"
            minmax
            hide
          />
          <Forecast
            data={this.state.frontend.data.forecast.hourly}
            format="HH:mm - ddd /D"
          />
        </ScrollSection>
      </ScrollSection>
    );
  }
}

ReactDOM.render(<NewTab />, document.getElementById('root'));
