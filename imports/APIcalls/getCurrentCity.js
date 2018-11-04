
export const getCurrentCityData = (city, APIkey, weatherIcons) => {

// Call weather api to get weather of current city
$.getJSON('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&units=metric&APPID=' + APIkey, function (data) {

  Session.set( 'cityExists', true);

  // get json for current city weather data
  var rawJson = JSON.stringify(data);
  const json = JSON.parse(rawJson);

  // get matching weather icon
  var prefix = 'wi wi-';
  var code = json.weather[0].id;
  var icon = weatherIcons[code].icon;
  if (!(code > 699 && code < 800) && !(code > 899 && code < 1000)) {
    icon = 'day-' + icon;
  }
  icon = prefix + icon;

  Session.set( 'getCityData', {
    'name': json.name,
    'temp': Math.floor(json.main.temp) + '°C',
    'lat': json.coord.lat,
    'long': json.coord.lon,
    'description': json.weather[0].description,
    'icon': icon
  });

})

.fail(function() {
  Session.set( 'cityExists', false);
  Session.set( 'picExists', false);
  console.log('does city exist? from within fail in getCurrentCity', Session.get( 'cityExists'))
  console.log('does pic exist? from within fail in getCurrentCity', Session.get( 'picExists'))
});

}
