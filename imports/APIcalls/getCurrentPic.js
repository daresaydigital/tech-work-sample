export const getCurrentCityPic = (city, APIweatherkey) => {

// call pic api to get pic of current city
$.getJSON('https://api.unsplash.com/search/photos/?page=1&per_page=1&query=' + city + '&client_id=' + APIweatherkey, function (data) {


  var rawJson = JSON.stringify(data);
  picJson = JSON.parse(rawJson);

  console.log('----picJson.results[0].urls.regular-----', picJson.results['0'].urls.regular)
  console.log('----picJson.results[0]-----', picJson.results['0'])
  console.log('----picJson.results-----', picJson.results)
  console.log('----picJson-----', picJson)
  console.log('----picJson.results.length-----', picJson.results.length)
  console.log('----picJson.length-----', picJson.length)

  if(picJson.results['0'].urls.regular) {
    Session.set( 'picExists', true);
    Session.set( 'getCityPic', {
      'url': picJson.results['0'].urls.regular,
      'author': picJson.results['0'].user.profile_image.small || '#',
      'link': picJson.results['0'].user.portfolio_url  || picJson.results['0'].user.links.html
    });
  } else {
    Session.set( 'picExists', false);
  }
})

.fail(function() {
  Session.set( 'picExists', false);
});
}
