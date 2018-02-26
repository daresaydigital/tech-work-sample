export const FORECAST_COMPLETED = 'FORECAST_COMPLETED';

export default (query: any, json: any) => {
  return {
    type: FORECAST_COMPLETED,
    query,
    json
  };
};
