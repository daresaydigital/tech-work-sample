/* global document chrome */
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import smoothScrollTo from './modules/smooth-scroll-to';
import Logo from './components/Logo';
import Weather from './components/Weather';
import Daily from './components/Forecast/Daily';
import Hourly from './components/Forecast/Hourly';
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
      scrollPos: [],
    };
    /* this.handleScrollX = this.handleScrollX.bind(this);
    this.handleScrollY = this.handleScrollY.bind(this); */
    this.handleScroll = this.handleScroll.bind(this);
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
    document.body.addEventListener('scroll', this.handleScroll, false);
  }

  componentWillUnmount() {
    document.body.removeEventListener('scroll', this.handleScroll, false);
  }

  handleScroll(event) {
    event.stopPropagation();
    const { scrollPos } = this.state;
    const defaults = {
      top: 0,
      left: 0,
    };
    let elPos = scrollPos[event.currentTarget] || defaults;
    if (elPos.left > event.currentTarget.scrollLeft) {
      /* Scroll to left */
      smoothScrollTo(0, 300, event.currentTarget, 'horizontal');
    } else if (elPos.left < event.currentTarget.scrollLeft) {
      /* Scroll to right */
      smoothScrollTo(event.currentTarget.scrollWidth, 300, event.currentTarget, 'horizontal');
    }
    if (elPos.top > event.currentTarget.scrollTop) {
      /* Scroll to top */
      smoothScrollTo(0, 300, event.currentTarget, 'vertical');
    } else if (elPos.top < event.currentTarget.scrollTop) {
      /* Scroll to bottom */
      smoothScrollTo(event.currentTarget.scrollHeight, 300, event.currentTarget, 'vertical');
    }

    elPos = {
      left: event.currentTarget.scrollLeft,
      top: event.currentTarget.scrollTop,
    };

    scrollPos[event.currentTarget] = elPos;
    this.setState({ scrollPos });
  }

  render() {
    return (
      <div className="wwise-app">
        <Logo
          url="http://www.pedroese.com"
          alt="Go to my website, http://www.pedroese.com/"
        />
        <Weather
          data={this.state.frontend.data.weather}
        />
        <section
          className="wwise-forecast-section"
          onScroll={this.handleScroll}
        >
          <Forecast
            data={this.state.frontend.data.forecast.daily}
            format="dddd, MMM Do"
            minmax
          />
          <Forecast
            data={this.state.frontend.data.forecast.hourly}
            format="HH:mm - ddd /D"
          />
        </section>
      </div>
    );
  }
}

ReactDOM.render(<NewTab />, document.getElementById('root'));
