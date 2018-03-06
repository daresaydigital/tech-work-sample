/* eslint quote-props: ["error", "consistent"] */

const weatherIcons = {
  'Few clouds': {
    unicode: '\uf00d',
    ligature: 'clear-sky',
    className: 'clear',
  },
  'Sky is clear': {
    unicode: '\uf00d',
    ligature: 'clear-sky',
    className: 'clear',
  },
  'Scattered clouds': {
    unicode: '\uf00d',
    ligature: 'clear-sky',
    className: 'clear',
  },
  'Clear sky': {
    unicode: '\uf00d',
    ligature: 'clear-sky',
    className: 'clear',
  },
  'Overcast clouds': {
    unicode: '\uf013',
    ligature: 'overcast-clouds',
    className: 'cloudy',
  },
  'Shower rain': {
    unicode: '\uf015',
    ligature: 'shower-rain',
    className: 'cloudy',
  },
  'Light intensity shower rain': {
    unicode: '\uf015',
    ligature: 'light-intensity-shower-rain',
    className: 'cloudy',
  },
  'Thunder': {
    unicode: '\uf016',
    ligature: 'thunder',
    className: 'stormy',
  },
  'Sleet': {
    unicode: '\uf017',
    ligature: 'sleet',
    className: 'cloudy',
  },
  'Shower sleet': {
    unicode: '\uf017',
    ligature: 'shower-sleet',
    className: 'cloudy',
  },
  'Light rain and snow': {
    unicode: '\uf017',
    ligature: 'light-rain-and-snow',
    className: 'cloudy',
  },
  'Rain and snow': {
    unicode: '\uf017',
    ligature: 'rain-and-snow',
    className: 'stormy',
  },
  'Light shower snow': {
    unicode: '\uf017',
    ligature: 'light-shower-snow',
    className: 'cloudy',
  },
  'Shower snow': {
    unicode: '\uf017',
    ligature: 'shower-snow',
    className: 'cloudy',
  },
  'Heavy shower snow': {
    unicode: '\uf017',
    ligature: 'heavy-shower-snow',
    className: 'stormy',
  },
  'Heavy intensity shower rain': {
    unicode: '\uf018',
    ligature: 'heavy-intensity-shower-rain',
    className: 'stormy',
  },
  'Ragged shower rain': {
    unicode: '\uf018',
    ligature: 'ragged-shower-rain',
    className: 'stormy',
  },
  'Rain': {
    unicode: '\uf019',
    ligature: 'rain',
    className: 'cloudy',
  },
  'Heavy intensity rain': {
    unicode: '\uf019',
    ligature: 'heavy-intensity-rain',
    className: 'stormy',
  },
  'Very heavy rain': {
    unicode: '\uf019',
    ligature: 'very-heavy-rain',
    className: 'stormy',
  },
  'Extreme rain': {
    unicode: '\uf019',
    ligature: 'extreme-rain',
    className: 'stormy',
  },
  'Freezing rain': {
    unicode: '\uf019',
    ligature: 'freezing-rain',
    className: 'cloudy',
  },
  'Light intensity drizzle': {
    unicode: '\uf01a',
    ligature: 'light-intensity-drizzle',
    className: 'cloudy',
  },
  'Drizzle': {
    unicode: '\uf01a',
    ligature: 'drizzle',
    className: 'cloudy',
  },
  'Heavy intensity drizzle': {
    unicode: '\uf01a',
    ligature: 'heavy-intensity-drizzle',
    className: 'cloudy',
  },
  'Drizzle rain': {
    unicode: '\uf01a',
    ligature: 'drizzle-rain',
    className: 'cloudy',
  },
  'Heavy intensity drizzle rain': {
    unicode: '\uf01a',
    ligature: 'heavy-intensity-drizzle-rain',
    className: 'stormy',
  },
  'Shower rain and drizzle': {
    unicode: '\uf01a',
    ligature: 'shower-rain-and-drizzle',
    className: 'cloudy',
  },
  'Heavy shower rain and drizzle': {
    unicode: '\uf01a',
    ligature: 'heavy-shower-rain-and-drizzle',
    className: 'stormy',
  },
  'Shower drizzle': {
    unicode: '\uf01a',
    ligature: 'shower-drizzle',
    className: 'cloudy',
  },
  'Light rain': {
    unicode: '\uf01a',
    ligature: 'light-rain',
    className: 'cloudy',
  },
  'Moderate rain': {
    unicode: '\uf01a',
    ligature: 'moderate-rain',
    className: 'cloudy',
  },
  'Light snow': {
    unicode: '\uf01b',
    ligature: 'light-snow',
    className: 'cloudy',
  },
  'Snow': {
    unicode: '\uf01b',
    ligature: 'snow',
    className: 'cloudy',
  },
  'Heavy snow': {
    unicode: '\uf01b',
    ligature: 'heavy-snow',
    className: 'cloudy',
  },
  'Thunderstorm with light rain': {
    unicode: '\uf01d',
    ligature: 'thunderstorm-with-light-rain',
    className: 'stormy',
  },
  'Light thunderstorm': {
    unicode: '\uf01d',
    ligature: 'light-thunderstorm',
    className: 'stormy',
  },
  'Thunderstorm with light drizzle': {
    unicode: '\uf01d',
    ligature: 'thunderstorm-with-light-drizzle',
    className: 'stormy',
  },
  'Thunderstorm with drizzle': {
    unicode: '\uf01d',
    ligature: 'thunderstorm-with-drizzle',
    className: 'stormy',
  },
  'Thunderstorm with heavy drizzle': {
    unicode: '\uf01d',
    ligature: 'thunderstorm-with-heavy-drizzle',
    className: 'stormy',
  },
  'Thunderstorm': {
    unicode: '\uf01e',
    ligature: 'thunderstorm',
    className: 'stormy',
  },
  'Thunderstorm with rain': {
    unicode: '\uf01e',
    ligature: 'thunderstorm-with-rain',
    className: 'stormy',
  },
  'Thunderstorm with heavy rain': {
    unicode: '\uf01e',
    ligature: 'thunderstorm-with-heavy-rain',
    className: 'stormy',
  },
  'Heavy thunderstorm': {
    unicode: '\uf01e',
    ligature: 'heavy-thunderstorm',
    className: 'stormy',
  },
  'Ragged thunderstorm': {
    unicode: '\uf01e',
    ligature: 'ragged-thunderstorm',
    className: 'stormy',
  },
  'Fog': {
    unicode: '\uf021',
    ligature: 'fog',
    className: 'cloudy',
  },
  'Haze': {
    unicode: '\uf021',
    ligature: 'haze',
    className: 'cloudy',
  },
  'Broken clouds': {
    unicode: '\uf041',
    ligature: 'broken-clouds',
    className: 'cloudy',
  },
  'Windy': {
    unicode: '\uf050',
    ligature: 'windy',
    className: 'clear',
  },
  'Mist': {
    unicode: '\uf063',
    ligature: 'mist',
    className: 'cloudy',
  },
  'Hail': {
    unicode: '\uf064',
    ligature: 'hail',
    className: 'stormy',
  },
  'Hot': {
    unicode: '\uf072',
    ligature: 'hot',
    className: 'clear',
  },
  'Storm': {
    unicode: '\uf073',
    ligature: 'storm',
    className: 'stormy',
  },
  'Violent storm': {
    unicode: '\uf073',
    ligature: 'violent-storm',
    className: 'stormy',
  },
  'Tornado': {
    unicode: '\uf073',
    ligature: 'tornado',
    className: 'stormy',
  },
  'Tropical storm': {
    unicode: '\uf073',
    ligature: 'tropical storm',
    className: 'stormy',
  },
  'Hurricane': {
    unicode: '\uf073',
    ligature: 'hurricane',
    className: 'stormy',
  },
  'Smoke': {
    unicode: '\uf074',
    ligature: 'smoke',
    className: 'smoke',
  },
  'Sand': {
    unicode: '\uf074',
    ligature: 'sand',
    className: 'smoke',
  },
  'Dust whirls': {
    unicode: '\uf074',
    ligature: 'dust-whirls',
    className: 'smoke',
  },
  'Cold': {
    unicode: '\uf076',
    ligature: 'cold',
    className: 'clear',
  },
  'Calm': {
    unicode: '\uf0b7',
    ligature: 'calm',
    className: 'clear',
  },
  'Light breeze': {
    unicode: '\uf0b8',
    ligature: 'light-breeze',
    className: 'clear',
  },
  'Gentle breeze': {
    unicode: '\uf0b9',
    ligature: 'gentle-breeze',
    className: 'clear',
  },
  'Moderate breeze': {
    unicode: '\uf0ba',
    ligature: 'moderate-breeze',
    className: 'clear',
  },
  'Fresh breeze': {
    unicode: '\uf0bb',
    ligature: 'fresh-breeze',
    className: 'clear',
  },
  'Strong breze': {
    unicode: '\uf0bc',
    ligature: 'strong-breeze',
    className: 'clear',
  },
  'High wind': {
    unicode: '\uf0bd',
    ligature: 'high-breeze',
    className: 'clear',
  },
  'Gale': {
    unicode: '\uf0be',
    ligature: 'gale',
    className: 'clear',
  },
  'Severe gale': {
    unicode: '\uf0bf',
    ligature: 'severe-gale',
    className: 'clear',
  },
  'Volcanic ash': {
    unicode: '\uf0c8',
    ligature: 'volcanic-ash',
    className: 'smoke',
  },
};

module.exports = weatherIcons;
