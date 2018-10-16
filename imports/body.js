import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { weatherIcons } from './weatherIcons.js';
import { APIkey, APIweatherkey } from '../settings.json';

import './body.html';

Template.body.onCreated(function bodyOnCreated() {

  // current city of the user
  this.city = new ReactiveVar();

  // sets the query to match which icon the user has clicked
  this.query = new ReactiveVar();

  // initiates showing the list of cities the user wants to go to, and the associated icon.
  this.hasSelectedOption = new ReactiveVar(false);
  this.wishCityIcon = new ReactiveVar();

 });

if (Meteor.isClient) {

  Template.body.helpers({

   currCityData() {
     const city = Template.instance().city.get();
     const data = Session.get( 'getCityData');
     const pic = Session.get( 'getCurrentCityPic');
     const icon = Session.get( 'getWeatherIcon');

     // console.log('-----------currCityData------------')
     // console.log('Template.instance().city.get() is----', city)
     // console.log('Session.get( getCityData) is----', data)
     // console.log('Session.get( getCurrentCityPic) is----', pic)
     // console.log('-----------------------------------')
     if(data){
     return [
       { name: data.name,
         temp: data.temp,
         description: data.description,
         pic: pic.url,
         picauthor: pic.author,
         piclink: pic.link,
         icon: icon.icon,
       }
     ]
   } else {
     return '';
   }
   },

   //returns array of cities that match the selected option(s) from the user
   wishCitiesList() {

      //checks if user has selected an option, and only runs the below in case they have.
      if (Template.instance().hasSelectedOption.get() === true){
       var query = Template.instance().query.get();
       var currCity = Template.instance().city.get();
       var icon = Template.instance().wishCityIcon.get();
       var data = Session.get( 'getWishCityData');

       //sets link to take the user to a google flight search for the city

       console.log('----WISH CITIES-----')
       console.log('currCity is:', currCity)
       console.log('icon is:', icon)
       console.log('current set of wished cities is:', data)
       console.log('--------------')


       return data.map(city => {
return { name: city.name,//data.name
    icon: null,//icon,
    temp: '23',//data.temp,
    link: '#',
    //'https://www.google.es/search?ei=dGq2W97bAYXMaIClhpgH&q=flights+' + currCity + '+' + data.name + '&oq=flights+' + currCity + '+' + name + '&gs_l=psy-ab.3...4710.10300.0.10510.0.0.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..0.0.0....0.1_7PSriwDpI'
  }

       })
     } else {
       return false;
     }
   },

  });


  Template.body.events({

    // set the current city/centerpoint for the search
    'submit form': function(event){
      event.preventDefault();
      var city = event.target.cityName.value;
      Template.instance().city.set(city);

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
          'url': json.results['0'].urls.regular,
          'author': json.results['0'].user.profile_image.small,
          'link': json.results['0'].user.portfolio_url  || json.results['0'].user.links.html,
        })
      });

    },

    // set weather query to be passed to the wish list
    'click .option': function (event){

      //sets that the user has selected an option
      Template.instance().hasSelectedOption.set(true);

      //checks what the user selected and sets icon and query accordingly
      if(event.currentTarget.id === 'rain') {
        Template.instance().wishCityIcon.set('wi wi-rain');
        Template.instance().query.set('rain');
      }
      if(event.currentTarget.id === 'snow') {
        Template.instance().wishCityIcon.set('wi wi-snow');
        Template.instance().query.set('snow');
      }
      if(event.currentTarget.id === 'cloud') {
        Template.instance().wishCityIcon.set('wi wi-cloud');
        return Template.instance().query.set('cloud');
      }
      if(event.currentTarget.id === 'sun') {
        Template.instance().wishCityIcon.set('wi wi-day-sunny');
        return Template.instance().query.set('sun');
      }
      if(event.currentTarget.id === 'wind') {
        Template.instance().wishCityIcon.set('wi wi-strong-wind');
        return Template.instance().query.set('wind');
      }

      // Call weather api to return a range of cities within an area of the current city, which matches the weather query
      const currCityData = Session.get( 'getCityData');
      const long = currCityData.long;
      const lat = currCityData.lat;
      console.log ('current lat and long')
      console.log (lat)
      console.log (long)
      const search = 'http://api.openweathermap.org/data/2.5/find?lat=' + lat + '&lon=' + long + '&cnt=50&callback=?&units=metric&APPID=' + APIkey
      const que = Template.instance().query.get();

      console.log('------QUE?-------', que)

      req = $.getJSON(search, function (data) {
        var rawJson = JSON.stringify(data);
        const json = JSON.parse(rawJson);
        let newArray = json.list.map(city => {
          return    { 'name': city.name,
          'weather': city.weather[0].main,
          'temp': Math.floor(city.main.temp) + '°C',
        }
      }
    )
    console.log("this is my array========>" , newArray)
        Session.set( 'getWishCityData', newArray)
      })
    }

})
}
