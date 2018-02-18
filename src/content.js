/* global chrome */
import React, { Component } from 'react';
import { render } from 'react-dom';
import ReactTooltip from 'react-tooltip';
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
      if (request.status === 'ok') {
        const weather = request.content;
        this.setState({
          isVisible: true,
          weather,
        });
        this.replaceSelectedText();
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

  replaceSelectedText = () => {
    let sel;
    let range;
    if (window.getSelection) {
      sel = window.getSelection();
      if (sel.rangeCount) {
        range = sel.getRangeAt(0);
        const anchor = document.getElementById('anchor');
        anchor.textContent = range;
        range.deleteContents();
        range.insertNode(anchor);
        this.simulateClick(anchor);
      }
    }/* else if (document.selection && document.selection.createRange) {
      range = document.selection.createRange();
      range.text = replacementText;
    } */
  }

  simulateClick = (el) => {
    const event = new MouseEvent('click', {
      view: window,
      bubbles: true,
      cancelable: true,
    });
    setTimeout(() => {
      el.dispatchEvent(event);
    }, 500);
  }

  render() {
    return (
      this.state.isVisible &&
        <div>
          <span
            id="anchor"
            data-tip={
            `<div>
              <h1>${this.state.weather.location}</h1>
              <h2>${this.state.weather.temp}˚</h2>
              <h3>${this.state.weather.conditions}</h3>
              <h4>Min ${this.state.weather.min}˚ | Max ${this.state.weather.max}˚</h4>
            </div>`}
          />
          <ReactTooltip
            effect="solid"
            event="click"
            html
          />
        </div>
    );
  }
}

const injectDOM = document.createElement('div');
injectDOM.className = 'wwise-tooltip';
document.body.appendChild(injectDOM);
render(<Content />, injectDOM);
