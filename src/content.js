/* global chrome */
import React, { Component } from 'react';
import { render } from 'react-dom';
import './content.css';

class Content extends Component {
  constructor(props) {
    super(props);
    this.state = {
      weather: {},
      range: null,
      isVisible: false,
    };
  }

  componentDidMount() {
    chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
      if (request.status === 'ok') {
        const weather = request.content;
        this.setState({ weather });
        /* Insert the tooltip */
        this.toggleTooltip(true);
      }
      /* sendResponse({ confirmation: 'Weather received!' }); */
    });
    window.addEventListener('click', (e) => {
      /* Whenever there is a click on the page, the tooltip will be removed */
      this.toggleTooltip(false);
    });
  }

  resetOldRange(searchTerm) {
    if (this.state.range) {
      const oldRange = this.state.range;
      /* Create a text node, insert the contents of the search term
      wrapper in it and insert it */
      const textNode = document.createTextNode(searchTerm.textContent);
      oldRange.insertNode(textNode);
    }
  }

  toggleTooltip = (insert) => {
    /* Select our outer wrapper element */
    const anchor = document.getElementById('wwise-anchor');
    /* Select our search term wrapper element */
    const searchTerm = document.getElementById('wwise-searchTerm');
    /* Get the selected text, if possible */
    if (insert) {
      const sel = window.getSelection();
      if (sel.rangeCount) {
        /* Get the selected range */
        const range = sel.getRangeAt(0);
        /* If our search term wrapper already has a search term in it,
        retrieve the old range from the state to reset it to its original state */
        this.resetOldRange(searchTerm);
        /* insert the contents of the search in the search term wrapper */
        searchTerm.textContent = range;
        /* Save the new range in the state for reference next time */
        this.setState({ range });
        /* Empty the range */
        range.deleteContents();
        /* Insert the whole outer wrapper in the range */
        range.insertNode(anchor);
        /* Make the tooltip visible */
        this.setState({ isVisible: true });
      }
    } else {
      /* If there is no selected text, reset the old range
      and the range in the state */
      this.setState({ isVisible: false });
      /* Wait for the css transition to be done */
      setTimeout(() => {
        this.resetOldRange(searchTerm);
        this.setState({ range: null });
        document.body.appendChild(anchor);
      }, 300);
    }
  }

  render() {
    return (
      <span id="wwise-anchor" className={this.state.isVisible ? 'visible' : ''}>
        <div id="wwise-tooltip">
          <h1>{this.state.weather.location}</h1>
          <h2>{this.state.weather.temp}˚</h2>
          <h3>{this.state.weather.conditions}</h3>
          <p>Min {this.state.weather.min}˚ | Max {this.state.weather.max}˚</p>
        </div>
        <span id="wwise-searchTerm" />
      </span>
    );
  }
}

const injectDOM = document.createElement('div');
injectDOM.className = 'wwise-temp';
document.body.appendChild(injectDOM);
render(<Content />, injectDOM);
