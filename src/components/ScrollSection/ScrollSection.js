import React, { Component } from 'react';
import PropTypes from 'prop-types';
import smoothScrollTo from '../../modules/smooth-scroll-to';

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
    this.handleScroll = this.handleScroll.bind(this);
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
      let opacity;
      if(this.props.axis === 'x'){
       opacity  = scrollPos.left / (event.currentTarget.scrollWidth / 2);
     } else {
       opacity  = scrollPos.top / event.currentTarget.scrollHeight;
     }

    this.setState({
      scrollPos: {
        left: event.currentTarget.scrollLeft,
        top: event.currentTarget.scrollTop,
      },
      opacity
    });
  }

  render() {
    const childrenClones = React.Children.map(this.props.children, child =>
      React.cloneElement(child,
        { opacity: this.state.opacity }
      )
    );
    return (
      <section
        className="wwise-forecast-section"
        onScroll={this.handleScroll}
      >
        {childrenClones}
      </section>
    );
  }
}

ScrollSection.propTypes = {
  opacity: PropTypes.number,
  children: PropTypes.shape(),
};

ScrollSection.defaultProps = {
  opacity: 1,
  children: {},
};
