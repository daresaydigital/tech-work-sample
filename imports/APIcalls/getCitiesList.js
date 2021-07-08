export const getCitiesListData = (currCity, lat, long, que, APIkey) => {

  // widen the search for cities, with the current city as the center point
  const coordinateBox = (lat, long) => {
    const offset = 6;
    const str = (long - offset) + ',' + (lat - offset) + ',' + (long + offset) + ',' + (lat + offset);

    return str;
  }

  Session.set('loadingList', true)

  console.log("Hold on hold on - it usually returns a list in about five seconds. " +
  "Three...two...one...if it hasn't returned anything by now try Stockholm, Inverness, New York, " +
  "Hong Kong, Beijing, Edinburgh, Johannesburg, Lagos, Sydney, Cairo, Des Moines, Toronto...")

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
      Session.set('loadingList', false)
      Session.set('textMessage', "Oh boo - there are no cities with that weather close to your entered city. Try again.")
      console.log("There we go. Aargh - there are no cities with that weather close to your entered city. Try again.")
    }

    if ((cityList.length > 0) && (cityList.length < 50)) {
      cityList.length = cityList.length;
      Session.set( 'getWishCityData', cityList)
      Session.set( 'clearCitiesList', false)
      Session.set('loadingList', false)
      Session.set('textMessage', false)
      console.log("There we go, less than 50 cities with the selected weather. Try clicking on a city and see where it takes you.")
    }

    if (cityList.length >= 50) {
      cityList.length = 50;
      Session.set( 'getWishCityData', cityList)
      Session.set( 'clearCitiesList', false)
      Session.set('loadingList', false)
      Session.set('textMessage', false)
      console.log("There we go, more than 50 cities with the selected weather. Try clicking on a city and see where it takes you.")
    }

  })
}
