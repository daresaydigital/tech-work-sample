/* global document chrome */
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Logo from './components/Logo';
import Weather from './components/Weather';
import Daily from './components/Forecast/Daily';
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
      port: null,
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
    return (
      <div className="da-app">
        <Logo
          url="http://www.pedroese.com"
          alt="Go to my website, http://www.pedroese.com/"
        />
        <Weather
          data={this.state.frontend.data.weather}
        />
        {<Daily
          data={this.state.frontend.data.forecast.daily}
        />}
      </div>
    );
  }
}

ReactDOM.render(<NewTab />, document.getElementById('root'));
