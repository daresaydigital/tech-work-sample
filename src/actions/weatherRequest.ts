export const WEATHER_REQUEST = 'WEATHER_REQUEST';

export default (query: any, showProgress: boolean = true) => {
  return {
    type: WEATHER_REQUEST,
    query: {units: 'metric', ...query},
    showProgress
  };
};
