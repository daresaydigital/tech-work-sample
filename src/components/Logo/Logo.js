import React from 'react';
import PropTypes from 'prop-types';
import InlineSVG from 'svg-inline-react';
import logoSVG from '../../assets/pedro-s.svg';

const Logo = props => (
  <a
    className="weatherwise-pedros-logo"
    href={props.url}
    title="By Pedro *S*"
  >
    <InlineSVG src={logoSVG} />
  </a>
);

export default Logo;

Logo.propTypes = {
  url: PropTypes.string,
};

Logo.defaultProps = {
  url: null,
};
