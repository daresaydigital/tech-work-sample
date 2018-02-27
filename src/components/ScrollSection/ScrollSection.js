import React, { Component } from 'react';
import PropTypes from 'prop-types';
import smoothScrollTo from '../../modules/smooth-scroll-to';

const scrollDuration = 1000;

export default class ScrollSection extends Component {
  constructor() {
    super();
    this.state = {
      scrollPos: {
        left: 0,
        top: 0,
      },
      opacity: 1,
    };
    this.handleWheel = this.handleWheel.bind(this);
  }

  handleWheel(event) {
    event.preventDefault();
    const { scrollPos } = this.state;

    if (event.deltaX < 0) {
      /* Scroll to left */
      smoothScrollTo(0, 0, scrollDuration, event.currentTarget);

    } else if (event.deltaX > 0) {
      /* Scroll to right */
      smoothScrollTo(event.currentTarget.scrollWidth, 0, scrollDuration, event.currentTarget);
    }
    if (event.deltaY < 0) {
      /* Scroll to top */
      smoothScrollTo(0, 0, scrollDuration, event.currentTarget);
    } else if (event.deltaY > 0) {
      /* Scroll to bottom */
      smoothScrollTo(0, event.currentTarget.scrollHeight, scrollDuration, event.currentTarget);
    }

    /* The opacity of the first element is determined by the amount of scroll. I calculate it on both
    axis as a fraction of the total scroll, and then take the largest amount and substract it from 1,
    so that it is inversely proportional */
    const xPos = event.currentTarget.scrollTop / (event.currentTarget.scrollHeight / 2);
    const yPos = event.currentTarget.scrollLeft / (event.currentTarget.scrollWidth / 2);
    const opacity = xPos > yPos ? 1 - xPos : 1 - yPos;

    this.setState({
      scrollPos: {
        left: event.currentTarget.scrollLeft,
        top: event.currentTarget.scrollTop,
      },
      opacity,
    });
  }

  render() {
    const className = `wwise-scroll-section ${this.props.className}`;
    let CustomTag;
    /* I could just pass the value of the element property to the CustomTag
    variable, but using a switch ensures that the "element" property always has
    an expected value and does not break the markup. I can add more available tags
    tags later on */
    switch (this.props.element) {
      case 'section':
        CustomTag = 'section';
        break;
      default:
        CustomTag = 'div';
        break;
    }

    /* Pass on the opacity to children by cloning them */
    const children = this.props.children.map(child =>
      React.cloneElement(child, {
        opacity: this.state.opacity,
        left: this.state.scrollPos.left,
        top: this.state.scrollPos.top,
      }),
    );
    return (
      <CustomTag
        className={className}
        onWheel={this.handleWheel}
      >
        {children}
      </CustomTag>
    );
  }
}

ScrollSection.propTypes = {
  children: PropTypes.arrayOf(PropTypes.shape()),
  element: PropTypes.string,
  className: PropTypes.string,
};

ScrollSection.defaultProps = {
  children: [],
  element: 'div',
  className: '',
};
