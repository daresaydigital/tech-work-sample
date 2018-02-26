export const WEATHER_COMPLETED = 'WEATHER_COMPLETED';

export default (query: any, json: any) => {
  return {
    type: WEATHER_COMPLETED,
    query,
    json
  };
};
