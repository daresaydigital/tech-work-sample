import * as React from 'react';
import { connect } from 'react-redux';
import * as R from 'ramda';
import DayItem from './common/dayItem';

interface Props extends React.Props<Forecast> {
  forecast?: any;
}

// Nice tsx guide: https://github.com/Microsoft/TypeScript/wiki/JSX
export class Forecast extends React.Component<Props, {}> {
  public render() {
    var dayList = this.props.forecast.json.list.map((day, index) => {
      return <DayItem key={ index } { ...day } />;
    });
    return (
      <div className="forecast row">
        { dayList }
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    forecast: state.forecast
  }
};

export default connect(
  mapStateToProps
)(Forecast);
