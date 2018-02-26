import React from 'react'
import Enzyme, { mount, shallow } from 'enzyme'
import Adapter from 'enzyme-adapter-react-16';
import DayItem from '../src/components/common/dayItem'
import Progress from '../src/components/progress'
import { App } from '../src/components/app'
import ForecastConnected, { Forecast } from '../src/components/forecast'
import OopsConnected, { Oops } from '../src/components/oops'
import SearchConnected, { Search } from '../src/components/search'
import WeatherConnected, { Weather } from '../src/components/weather'

Enzyme.configure({ adapter: new Adapter() });

describe('Components', () => {
  describe('App', () => {
    it('should render progress', () => {
      const props = {
        page: 'progress',
        currentPositionRequest: jest.fn
      };
      const wrapper = shallow(<App {...props} />)
      expect(wrapper.find(Progress).length).toBe(1);
    })

    it('should render search', () => {
      const props = {
        page: 'search',
        currentPositionRequest: jest.fn
      };
      const wrapper = shallow(<App {...props} />)
      expect(wrapper.find(SearchConnected).length).toBe(1);
    })

    it('should render weather', () => {
      const props = {
        page: 'weather',
        currentPositionRequest: jest.fn
      };
      const wrapper = shallow(<App {...props} />)
      expect(wrapper.find(WeatherConnected).length).toBe(1);
    })

    it('should render oops', () => {
      const props = {
        page: 'wrong !!!',
        currentPositionRequest: jest.fn
      };
      const wrapper = shallow(<App {...props} />)
      expect(wrapper.find(OopsConnected).length).toBe(1);
    })
  })

  describe('DayItem', () => {
    it('should render self', () => {
      const props = {
        dt: 1,
        weather: [
          {
            id: 1,
            main: 'test',
          }
        ],
        temp: {
          min: 1,
          max: 1,
        },
      };

      const enzymeWrapper = mount(<DayItem {...props} />)

      expect(enzymeWrapper.find('.day-name').length).toBe(1)
      expect(enzymeWrapper.find('.day-icon').length).toBe(1)
      expect(enzymeWrapper.find('.day-temp').length).toBe(1)
    })
  })

  describe('Forecast', () => {
    it('should render self', () => {
      const props = {
        forecast: {
          json: {
            list: []
          }
        }
      };
      const enzymeWrapper = mount(<Forecast {...props} />)
      expect(enzymeWrapper.find('.forecast').length).toBe(1)
    })

    it('should render dayItems', () => {
      const props = {
        forecast: {
          json: {
            list: Array(7).fill()
          }
        }
      };
      const wrapper = shallow(<Forecast {...props} />)
      expect(wrapper.find(DayItem).length).toBe(7)
    })
  })

  describe('Oops', () => {
    it('should render self', () => {
      const props = {
      };
      const enzymeWrapper = mount(<Oops {...props} />)

      expect(enzymeWrapper.find('.lead').length).toBe(1)
    })
  })

  describe('Progress', () => {
    it('should render self', () => {
      const props = {
      };
      const enzymeWrapper = mount(<Progress {...props} />)

      expect(enzymeWrapper.find('.weather-progreess').length).toBe(1)
    })
  })

  describe('Weather', () => {
    it('should render forecast', () => {
      const props = {
        weather: {
          query: {},
          json: {
            weather: [{}],
            wind: {}
          }
        }
      };
      const wrapper = shallow(<Weather {...props} />)
      expect(wrapper.find(ForecastConnected).length).toBe(1)
    })
  })
})
