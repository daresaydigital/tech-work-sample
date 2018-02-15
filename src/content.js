/* global chrome */
import React, { Component } from 'react';
import { render } from 'react-dom';
import './content.css';

class Content extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isVisible: false,
      weather: {},
    };
  }

  componentDidMount() {
    const local = this;
    chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
      if (typeof (request.message) !== 'undefined') {
        alert(request.message);
      }
      /* if (typeof (request.content) !== 'undefined') {
        local.setState({
          weather: request.weather,
          isVisible: true,
        });
        sendResponse({ confirmation: 'Successfully created div' });
      } */
    });
  }

  render() {
    return (
      this.state.isVisible &&
        <div>
          This is the weather at that location.
          {/* this.state.weather */}
        </div>
    );
  }
}

const injectDOM = document.createElement('div');
injectDOM.className = 'wwise-tooltip';
document.body.appendChild(injectDOM);
render(<Content />, injectDOM);
