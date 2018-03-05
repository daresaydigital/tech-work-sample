import React, { Component } from 'react';
import PropTypes from 'prop-types';

export default class ScrollSection extends Component {
  constructor() {
    super();
    this.state = {
      classX: 'slider-left',
      classY: 'slider-top',
    };
    this.handleWheel = this.handleWheel.bind(this);
  }

  handleWheel(event) {
    event.preventDefault();
    let { classX } = this.state;
    let { classY } = this.state;
    if (event.deltaX < -10) {
      /* Scroll to left */
      classX = 'slider-left';
    } else if (event.deltaX > 10) {
      /* Scroll to right */
      classX = 'slider-right';
    } else if (event.deltaY < -10) {
      /* Scroll to top */
      classY = 'slider-top';
    } else if (event.deltaY > 10) {
      /* Scroll to bottom */
      classY = 'slider-bottom';
    }
    this.setState({
      classX,
      classY,
    });
  }

  render() {
    const className = `wwise-scroll-section ${this.props.className} ${this.state.classX} ${this.state.classY}`;
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

    return (
      <CustomTag
        className={className}
        onWheel={this.handleWheel}
      >
        {this.props.children}
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
