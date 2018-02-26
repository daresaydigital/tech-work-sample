import * as React from 'react';

class Progress extends React.Component<{}, {}> {
  constructor(props: null) {
    super(props);
  }

  public render() {
    return (
      <div className="h-100 row align-items-center">
        <div className="col">
          <div className="weather-progreess">
            <div className="icon sun-shower">
              <div className="cloud"></div>
              <div className="sun">
                <div className="rays"></div>
              </div>
              <div className="rain"></div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Progress;
