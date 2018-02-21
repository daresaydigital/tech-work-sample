import * as React from 'react';
import { connect } from 'react-redux';
import PlacesAutocomplete, { geocodeByAddress, getLatLng } from 'react-places-autocomplete'
import changePage from '../actions/changePage';
import weatherRequest from '../actions/weatherRequest';

interface Props extends React.Props<Search> {
  getWeather?: (query: any) => void;
  changePage?: (page: string) => void;
}

interface State {
  q: string
}

export class Search extends React.Component<Props, State> {
  constructor(props) {
    super(props);

    this.state = {
      q: ''
    };
  }

  shouldFetchSuggestions = ({ value }) => value.length > 2

  renderSuggestion = ({ formattedSuggestion }) => (
    <div className="search-suggestion-item">
      <strong>{formattedSuggestion.mainText}</strong>{' '}
      <small className="text-muted">{formattedSuggestion.secondaryText}</small>
    </div>
  )

  handleSelect = (q) => {
    this.setState({ q });
    this.props.getWeather({ q });
  }

  handleError = (status, clearSuggestions) => {
    clearSuggestions();
    this.props.changePage('oops');
  }

  render() {
    const inputProps = {
      type: 'text',
      value: this.state.q,
      placeholder: 'Show me the weather in ...',
      onChange: (q) => this.setState({ q }),
      autoFocus: true
    };

    const cssClasses = {
      root: 'form-group',
      input: 'search-input',
      autocompleteContainer: 'autocomplete-container',
    };

    return (
      <div className="h-50 row justify-content-center">
        <div className="col-6 align-self-center">
          <PlacesAutocomplete
            renderSuggestion={this.renderSuggestion}
            inputProps={inputProps}
            classNames={cssClasses}
            onSelect={this.handleSelect}
            onError={this.handleError}
            shouldFetchSuggestions={this.shouldFetchSuggestions}
          />
        </div>
      </div>
    )
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    getWeather: (query: any) => {
      return dispatch(weatherRequest(query));
    },
    changePage: (name: string) => {
      return dispatch(changePage(name));
    }
  }
}

export default connect(
  null,
  mapDispatchToProps
)(Search);
