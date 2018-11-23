export const getCitiesListData = (currCity, lat, long, que, APIkey) => {

  // widen the search for cities, with the current city as the center point
  const coordinateBox = (lat, long) => {
    const offset = 5;
    const str = (long - offset) + ',' + (lat - offset) + ',' + (long + offset) + ',' + (lat + offset);

    return str;
  }

  // get list of cities based on the above lat, long and offset
  const search = 'http://api.openweathermap.org/data/2.5/box/city?bbox=' + coordinateBox(lat, long) + ',20&callback=?&units=metric&APPID=' + APIkey

  req = $.getJSON(search, function (data) {
    var rawJson = JSON.stringify(data);
    const json = JSON.parse(rawJson);

    let newArray = json.list.map(city => {
      return    {
        'name': city.name || '',
        'weather': city.weather[0].main,
        'temp': Math.floor(city.main.temp) + '°C',
        'link': 'https://www.google.es/search?ei=dGq2W97bAYXMaIClhpgH&q=flights+' + currCity + '+' + city.name + '&oq=flights+' + currCity + '+' + city.name + '&gs_l=psy-ab.3...4710.10300.0.10510.0.0.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..0.0.0....0.1_7PSriwDpI'
      }
    }
  )

  // takes the created array, compares it with the current query and returns only the cities that match the query.
  let filteredArray = (newArray, que) => {
    switch(que){
      case que:
      return newArray.filter(city =>{
        return city.weather.toLowerCase().indexOf(que.toLowerCase()) !== -1;
      })
      default:
      return newArray;
    }
  }

    // set current city list to be the filtered array
    let cityList = filteredArray(newArray, que)

    if (cityList.length === 0) {
      Session.set( 'getWishCityData', false)
      Session.set( 'clearCitiesList', true)
    }

    if ((cityList.length > 0) && (cityList.length < 15)) {
      cityList.length = cityList.length;
      Session.set( 'getWishCityData', cityList)
      Session.set( 'clearCitiesList', false)
    }

    if (cityList.length >= 30) {
      cityList.length = 30;
      Session.set( 'getWishCityData', cityList)
      Session.set( 'clearCitiesList', false)
    }
  })
}
