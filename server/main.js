import { Meteor } from 'meteor/meteor';

Meteor.startup(() => {
  // code to run on server at startup
});
//
// Meteor.methods({
//
// getCityData(city) {
//   var key = '9b5e8856a8ad837d711bf4c74b6e0ecf'; //Meteor.settings.apiKey;
//   var url = 'http://api.openweathermap.org/data/2.5/weather?zip=' + city + '&APPID=' + key;
//   $.getJSON(url, function (data) {
//     var rawJson = JSON.stringify(data);
//     var json = JSON.parse(rawJson);
//
//     return json;
//   })
//  }
// });
