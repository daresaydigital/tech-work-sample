
export const getCurrentCityData = (city, APIkey, APIweatherkey, weatherIcons) => {

// Call weather api to get weather of current city
req = $.getJSON('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&callback=?&units=metric&APPID=' + APIkey, function (data) {
  var rawJson = JSON.stringify(data);
  const json = JSON.parse(rawJson);
  Session.set( 'getCityData', {
    'name': json.name,
    'temp': Math.floor(json.main.temp) + '°C',
    'lat': json.coord.lat,
    'long': json.coord.lon,
    'description': json.weather[0].description
  })

  // Get matching weather icon
  req.then(function(json) {
    var prefix = 'wi wi-';
    var code = json.weather[0].id;
    var icon = weatherIcons[code].icon;

    if (!(code > 699 && code < 800) && !(code > 899 && code < 1000)) {
      icon = 'day-' + icon;
    }

    icon = prefix + icon;
    Session.set( 'getWeatherIcon', {
      'icon': icon,
    })
  });
});

// Call pic api to get pic of current city
$.getJSON('https://api.unsplash.com/search/photos/?page=1&per_page=1&query=' + city + '&client_id=' + APIweatherkey, function (data) {
  var rawJson = JSON.stringify(data);
  const json = JSON.parse(rawJson);
  Session.set( 'getCurrentCityPic', {
    'url': json.results['0'].urls.regular  || '#',
    'author': json.results['0'].user.profile_image.small || '#',
    'link': json.results['0'].user.portfolio_url  || json.results['0'].user.links.html,
  })
});
}
