import * as React from 'react';
import { connect } from 'react-redux';
import changePage from '../actions/changePage';

interface Props extends React.Props<Oops> {
  changePage?: () => void;
}

export class Oops extends React.Component<Props, {}> {
  constructor(props: Props) {
    super(props);
  }

  public render() {
    return (
      <div className="h-100 row align-items-center">
        <div className="col text-center">
          <div className="display-4">
            <i className="wi wi-alien" />
          </div>
          <div className="lead">
            Oops, an error has occured !
          </div>
          <button type="button" className="btn btn-light btn-sm" onClick={this.props.changePage}>
            Try agian
          </button>
        </div>
      </div>
    );
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    changePage: () => {
      return dispatch(changePage('search'));
    }
  }
}

export default connect(
  null,
  mapDispatchToProps
)(Oops);
