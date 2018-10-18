


export const getCitiesListData = (lat, long, que, APIkey) => {

  //widen the search for cities
  const coordinateBox = (lat, long) => {
    let latBottom = lat-3;
    let latTop = lat+3;
    let longLeft = long-3;
    let longRight = long+3;

    let stringMe = longLeft + ',' + latBottom + ',' + longRight + ',' + latTop;

    return stringMe;
  }

  //get list of cities based on the lat and long of the city you entered as a center point
  const search = 'http://api.openweathermap.org/data/2.5/box/city?bbox=' + coordinateBox(lat, long) + ',20&callback=?&units=metric&APPID=' + APIkey

  req = $.getJSON(search, function (data) {
    var rawJson = JSON.stringify(data);
    const json = JSON.parse(rawJson);
    let newArray = json.list.map(city => {
      return    {
        'name': city.name,
        'weather': city.weather[0].main,
       'temp': Math.floor(city.main.temp) + '°C',
      }
    }
  )

  //filter the returned list of cities, only return cities that match the query
  let filteredArray = (newArray, que) => {
    switch(que){
      case que:
      return newArray.filter(city =>{
        return city.weather === que
      })
      default:
      return newArray;
    }
  }
  console.log('filteredArray from API call', filteredArray(newArray, que))

  console.log('newArray from API call', newArray)

    //set current city list to be the filtered array
    Session.set( 'getWishCityData', filteredArray(newArray, que)) //this should instead take the filtered list
  })
}
