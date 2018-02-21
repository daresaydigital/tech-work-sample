import sinon from 'sinon';
import sinonStubPromise from 'sinon-stub-promise';
import Api from '../src/api'

sinonStubPromise(sinon);

describe('Api', () => {

  describe('getCurrentPosition', () => {
    var tmp = {};
    const mockLocalStorage = {
      setItem: (k, v) => tmp[k] = v,
      getItem: (k) => tmp[k]
    }
    global.localStorage = mockLocalStorage;

    const mockGeolocation = {
      getCurrentPosition: jest.fn(),
      watchPosition: jest.fn()
    };
    global.navigator.geolocation = mockGeolocation;

    var stub = sinon.stub(mockGeolocation, 'getCurrentPosition')
    mockGeolocation.getCurrentPosition.callsArgWith(0, {
      coords: {
        latitude: 1,
        longitude: 1
      }
    });

    it('first call and check result', () => {
      Api.getCurrentPosition().then(coords => expect(coords).toHaveProperty('latitude'))
    })

    it('second call and read from cache', () => {
      Api.getCurrentPosition().then(coords => expect(stub.callCount).toBe(1))
    })
  })

  describe('invokeWeather', () => {
    it('first call and check result', () => {
      var mock = global.fetch = sinon.stub().returnsPromise();
      var json = {
        temp: 1
      };
      mock.resolves({json: () => json});
      Api.invokeWeather().then(r => {
        expect(mock.callCount).toBe(1)
        expect(r).toEqual(json)
      });
    })

    it('call with error', () => {
      var mock = global.fetch = sinon.stub().returnsPromise();
      var json = {
        temp: 1
      };
      mock.rejects('wrong');
      Api.invokeWeather().catch(e => {
        expect(e).toEqual('wrong');
      })
    })
  })

  describe('invokeForecast', () => {
    it('first call and check result', () => {
      var mock = global.fetch = sinon.stub().returnsPromise();
      var json = {
        temp: 1
      };
      mock.resolves({json: () => json});
      Api.invokeForecast().then(r => {
        expect(mock.callCount).toBe(1)
        expect(r).toEqual(json)
      });
    })

    it('call with error', () => {
      var mock = global.fetch = sinon.stub().returnsPromise();
      var json = {
        temp: 1
      };
      mock.rejects('wrong');
      Api.invokeForecast().catch(e => {
        expect(e).toEqual('wrong');
      })
    })
  })

  describe('getLatLngByAddress', () => {
    it('first call and check result', () => {
      var json = {lat: 1, lng: 1};
      var mock1 = sinon.stub(Api, 'geocodeByAddress').callsFake(function fakeFn() {
        return Promise.resolve(['ok'])
      });
      var mock2 = sinon.stub(Api, 'getLatLng').callsFake(function fakeFn() {
        return Promise.resolve(json)
      });
      Api.getLatLngByAddress().then(r => {
        expect(r).toEqual(json)
        expect(mock1.callCount).toBe(1)
        expect(mock2.callCount).toBe(1)
      });
    })
  })
})
