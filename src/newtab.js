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
      scrollPos: {
        left: 0,
        top: 0,
      },
    };
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
    if (scrollPos.left > event.currentTarget.scrollLeft) {
      /* Scroll to left */
      smoothScrollTo(0, 0, 300, event.currentTarget);
    } else if (scrollPos.left < event.currentTarget.scrollLeft) {
      /* Scroll to right */
      smoothScrollTo(event.currentTarget.scrollWidth, 0, 300, event.currentTarget);
    }
    if (scrollPos.top > event.currentTarget.scrollTop) {
      /* Scroll to top */
      smoothScrollTo(0, 0, 300, event.currentTarget);
    } else if (scrollPos.top < event.currentTarget.scrollTop) {
      /* Scroll to bottom */
      smoothScrollTo(0, event.currentTarget.scrollHeight, 300, event.currentTarget);
    }
    this.setState({
      scrollPos: {
        left: event.currentTarget.scrollLeft,
        top: event.currentTarget.scrollTop,
      },
    });
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
          opacity={this.state.scrollPos.top}
        />
        <ScrollSection
          axis="x"
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
        </ScrollSection>
      </div>
    );
  }
}

ReactDOM.render(<NewTab />, document.getElementById('root'));
