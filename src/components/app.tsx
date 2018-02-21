import * as React from 'react';
import { connect } from 'react-redux';
import Progress from './progress';
import Weather from './weather';
import Search from './search';
import Oops from './oops';

interface Props extends React.Props<App> {
  page?: any;
  currentPositionRequest?: () => void;
}

export class App extends React.Component<Props, {}> {
  public render() {
    switch (this.props.page) {
      case 'progress':
        return <Progress />;
      case 'search':
        return <Search />;
      case 'weather':
        return <Weather />;
      default:
        return <Oops />;
    }
  }
}

const mapStateToProps = (state) => {
  return {
    page: state.page,
  }
}

export default connect(
  mapStateToProps
)(App);
