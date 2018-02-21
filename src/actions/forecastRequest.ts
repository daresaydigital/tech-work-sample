export const FORECAST_REQUEST = 'FORECAST_REQUEST';

export default (query: any) => {
  return {
    type: FORECAST_REQUEST,
    query: {units: 'metric', ...query}
  };
};
