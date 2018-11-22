export const getCurrentCityPic = (city, APIweatherkey) => {

// call pic api to get pic of current city
$.getJSON('https://api.unsplash.com/search/photos/?page=1&per_page=1&query=' + city + '&client_id=' + APIweatherkey, function (data) {

  Session.set( 'picExists', true);

  var rawJson = JSON.stringify(data);
  picJson = JSON.parse(rawJson);

  const bgResult = picJson.results['0'].urls.regular;

  let bgpic;
  if (( bgResult === undefined) || (!bgResult)) {
    bgpic = '#';
  } else {
    bgpic = picJson.results['0'].urls.regular;
  }

  Session.set( 'getCityPic', {
    'url': bgpic,
    'author': picJson.results['0'].user.profile_image.small || '#',
    'link': picJson.results['0'].user.portfolio_url  || picJson.results['0'].user.links.html
  });

})

.fail(function() {
  Session.set( 'picExists', false);
});

}
