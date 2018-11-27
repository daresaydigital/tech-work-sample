export const getCurrentCityPic = (city, APIweatherkey) => {

// call pic api to get pic of current city
$.getJSON('https://api.unsplash.com/search/photos/?page=1&per_page=1&query=' + city + '&client_id=' + APIweatherkey, function (data) {

  var rawJson = JSON.stringify(data);
  picJson = JSON.parse(rawJson);

  if(picJson.results.length > 0) {
    Session.set( 'getCityPic', {
      'url': picJson.results['0'].urls.regular || '#',
      'author': picJson.results['0'].user.profile_image.small || '#',
      'link': picJson.results['0'].user.portfolio_url  || picJson.results['0'].user.links.html
    });
  } else {
    Session.set( 'picExists', false);
    Session.set( 'clearCitiesList', true);
  }

})
}
