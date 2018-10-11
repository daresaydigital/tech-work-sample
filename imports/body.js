import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';

import './body.html';

Template.body.onCreated(function bodyOnCreated() {

  // current city of the user
  this.city = new ReactiveVar();

  // current city object
  this.cityData = new ReactiveVar();

  // city picture for any city object
  this.cityPic = new ReactiveVar();

  // sets the query to match which icon the user has clicked
  this.query = new ReactiveVar();

  // sets the currently selected city from the wishlist
  this.selectedCity = new ReactiveVar();

  // shows/hides lists based on weather
  this.showSun = new ReactiveVar(false);
  this.showRain = new ReactiveVar(false);
  this.showWind = new ReactiveVar(false);
  this.showCloud = new ReactiveVar(false);
  this.showSnow = new ReactiveVar(false);
 });

// if (Meteor.isServer) {
//   Meteor.methods({
//
//   //API calls
//
//   //Call to openweathermap to get weather data for our current city
//   currentCityWeather: function() {
//    let city = Template.instance().city.get();
//    $.getJSON('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&APPID=9b5e8856a8ad837d711bf4c74b6e0ecf', function (data) {
//      var rawJson = JSON.stringify(data);
//      var json = JSON.parse(rawJson);
//      console.log('---------currentCityWeather returns this json: ----------------', json)
//      Template.instance().cityData.set(json);
//      return json;
//    });
//  },
//
//   //Call to openweathermap to get data for a list of cities with a specified weather within a certain  area of our current city
//   wishCitiesWeather: function(weather) {
//     $.getJSON('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&APPID=9b5e8856a8ad837d711bf4c74b6e0ecf', function (data) {
//       var rawJson = JSON.stringify(data);
//       var json = JSON.parse(rawJson);
//       console.dir(json); //Update Weather parameters
//       this.cityData.set(json);
//     });
//   },
//
  //Call to unsplash to get a pic of the current city
//   currentCityPic: function() {
//      let city = Template.instance().city.get();
//      $.getJSON('https://api.unsplash.com/search/photos/?page=1&per_page=1&query=' + city + '&client_id=bf54ced5af6f6f686d571a602ff4611ebf8fc2ebdc59989cdf768941e85148bb', function (data) {
//        var rawJson = JSON.stringify(data);
//        var json = JSON.parse(rawJson);
//        console.log('---------currentCityPic gets this city: ----------------', city)
//        console.log('---------currentCityPic returns: ----------------', json)
//
//        Template.instance().cityPic.set(json.results["0"].urls.regular);
//      });
//    }
//
//   })
// }


if (Meteor.isClient) {

  Template.body.helpers({

   currCityData() {
     const city = Template.instance().city.get();
     const data = Session.get( 'getCityData');
     const pic = Session.get( 'getCurrentCityPic');

     console.log('-----------currCityData------------')
     console.log('Template.instance().city.get() is----', city)
     console.log('Session.get( getCityData) is----', data)
     console.log('Session.get( getCurrentCityPic) is----', pic)
     console.log('-----------------------------------')
     return [
       { name: data.name,
         temp: data.temp,
         pic: pic.url,
       }
     ]
   },

   //returns array of cities that match the selected option(s) from the user
   cityData() {
     var query = Template.instance().query.get();
     var currCity = Template.instance().city.get();
     var selectedCity = Template.instance().selectedCity.get();

     let icon;
     if(query = 'rain') {
      icon = 'wi wi-rain';
     }
     if(query = 'snow') {
      icon = 'wi wi-snow';
     }
     if(query = 'cloud') {
      icon = 'wi wi-cloud';
     }
     if(query = 'sunny') {
       icon = 'wi wi-day-sunny';
     }
     if(query = 'wind') {
       icon = 'wi wi-strong-wind';
     }

     const link = 'https://www.google.es/search?ei=dGq2W97bAYXMaIClhpgH&q=flights+' + currCity + '+' + selectedCity + '&oq=flights+' + currCity + '+' + selectedCity + '&gs_l=psy-ab.3...4710.10300.0.10510.0.0.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..0.0.0....0.1_7PSriwDpI'

     return [
       { name: 'Berlin',
         pic: 'https://images.unsplash.com/photo-1519231581888-9494c843a957?ixlib=rb-0.3.5&s=fb849b3ff98711115aee09ea3622d2e6&auto=format&fit=crop&w=634&q=80',
         icon: icon,
         temp: "24degrees",
         upUpAway: link
       },
       { name: 'Moscow',
         pic: 'https://images.unsplash.com/photo-1531153741856-3f88dad1dbd1?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=93adb4b9d2aca4a138fe1cdd0eff3101&auto=format&fit=crop&w=676&q=80',
         icon: icon,
         temp: "19degrees",
         upUpAway: link
        },
       { name: 'Prague',
         pic: 'https://images.unsplash.com/photo-1527001804454-f52ecc68533c?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=3a0caf7344e08cbda15a1b19db93118e&auto=format&fit=crop&w=1050&q=80',
         icon: icon,
         temp: "26degrees",
         upUpAway: link
       }
     ]
   },
  });


  Template.body.events({

    // set the current city/centerpoint for the search
    'submit form': function(event){
      event.preventDefault();
      var city = event.target.cityName.value;
      Template.instance().city.set(city);

      console.log('------submitted form - outside call-------')
      console.log('event.target.cityName.value is:', city)
      console.log('Template.instance().city.get() is:', Template.instance().city.get())
      console.log('-------------------------------------')

      // Call weather api to get weather of current city
      $.getJSON('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&units=metric&APPID=9b5e8856a8ad837d711bf4c74b6e0ecf', function (data) {
        var rawJson = JSON.stringify(data);
        const json = JSON.parse(rawJson);
        Session.set( 'getCityData', {
          'name': json.name,
          'temp': Math.floor(json.main.temp) + '°C'
        })

        console.log('------submitted form - inside call-------')
        console.log('json is:', json)
        console.log('-------------------------------------')
      });

      // Call pic api to get pic of current city
      $.getJSON('https://api.unsplash.com/search/photos/?page=1&per_page=1&query=' + city + '&client_id=bf54ced5af6f6f686d571a602ff4611ebf8fc2ebdc59989cdf768941e85148bb', function (data) {
        var rawJson = JSON.stringify(data);
        const json = JSON.parse(rawJson);
        Session.set( 'getCurrentCityPic', {
          'url': json.results['0'].urls.regular,
          'author': 'test'
        })

        console.log('------submitted form - inside PIC call-------')
        console.log('json is:', json)
        console.log('-------------------------------------')
      });

    },

    // set selected city from the wish list
    'click .selected-city': function (event){
      event.preventDefault();
      console.log('---you clicked .selected-city---here is event.target:', event.target)
      var selectedCity = event.target.selectedCity.value;
      Template.instance().selectedCity.set(selectedCity);
    },

    'click .rain': function (event) {
      console.log('you clicked on rain')
      return Template.instance().query.set('rain');
    },

    'click .snow': function (event) {
      console.log('you clicked on snow')
      return Template.instance().query.set('snow');
    },

    'click .sun': function (event) {
      console.log('you clicked on sun')
      return Template.instance().query.set('sun');
    },

    'click .cloud': function (event) {
      console.log('you clicked on clouds')
      return Template.instance().query.set('cloud');
    },

    'click .wind': function (event) {
      console.log('you clicked on wind')
      return Template.instance().query.set('wind');
    },
  });
}
