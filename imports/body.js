import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { weatherIcons } from './weatherIcons.js';
import { APIkey, APIweatherkey } from '../settings.json';
import { getCurrentCityData } from './APIcalls/getCurrentCity.js'
import { getCitiesListData } from './APIcalls/getCitiesList.js'

import './body.html';

Template.body.onCreated(function bodyOnCreated() {

  // current city of the user
  this.city = new ReactiveVar();

  // sets the query to match which icon the user has clicked
  this.query = new ReactiveVar();

  // initiates showing the list of cities the user wants to go to, and the associated icon.
  this.hasSelectedOption = new ReactiveVar(false);
  this.wishCityIcon = new ReactiveVar();

  this.noSuchBunny = new ReactiveVar(false);
 });

if (Meteor.isClient) {

  Template.body.helpers({

   selectedIcon() {
     return Template.instance().wishCityIcon.get();
   },

   noBunny(){
     return Template.instance().noSuchBunny.get()
   },

   currCityData() {
     const city = Template.instance().city.get();
     const data = Session.get( 'getCityData');
     const pic = Session.get( 'getCurrentCityPic');
     const icon = Session.get( 'getWeatherIcon');

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
       return Template.instance().noSuchBunny.set(true);
     }
   },

   //returns array of cities that match the selected option(s) from the user
   wishCitiesList() {

      //checks if user has selected an option, and only runs the below in case they have.
      if (Template.instance().hasSelectedOption.get() === true){
       var currCity = Template.instance().city.get();
       var data = Session.get( 'getWishCityData');

       //sets link to take the user to a google flight search for the city
       return data.map(city => {
        return { name: city.name,
            temp: city.temp,
            link: 'https://www.google.es/search?ei=dGq2W97bAYXMaIClhpgH&q=flights+' + currCity + '+' + city.name + '&oq=flights+' + currCity + '+' + city.name + '&gs_l=psy-ab.3...4710.10300.0.10510.0.0.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..0.0.0....0.1_7PSriwDpI'
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
      getCurrentCityData(city, APIkey, APIweatherkey, weatherIcons)
    },

    // set weather query to be passed to the wish list
    'click .option': function (event){

      //sets that the user has selected an option
      Template.instance().hasSelectedOption.set(true);

      // Call weather api to return a range of cities within an area of the current city, which matches the weather query
      const currCityData = Session.get( 'getCityData');
      let long = currCityData.long;
      let lat = currCityData.lat;

      console.log('------clicking weather options gives --------')
      console.log('currCityData:', currCityData)
      console.log('long, lat:', long, lat)

      let filteredArray;

      //checks what the user selected and sets icon and query accordingly
      if(event.currentTarget.id === 'rain') {
        Template.instance().wishCityIcon.set('wi wi-rain');
        Template.instance().query.set('Rain');
        filteredArray = getCitiesListData(lat, long, 'Rain', APIkey);
      }
      if(event.currentTarget.id === 'snow') {
        Template.instance().wishCityIcon.set('wi wi-snow');
        Template.instance().query.set('snow');
        filteredArray = getCitiesListData(lat, long, 'Snow', APIkey);
      }
      if(event.currentTarget.id === 'cloud') {
        Template.instance().wishCityIcon.set('wi wi-cloud');
        Template.instance().query.set('Clouds');
        filteredArray = getCitiesListData(lat, long, 'Clouds', APIkey);
      }
      if(event.currentTarget.id === 'clear') {
        Template.instance().wishCityIcon.set('wi wi-day-sunny');
        Template.instance().query.set('Clear');
        filteredArray = getCitiesListData(lat, long, 'Clear', APIkey);
      }
      if(event.currentTarget.id === 'wind') {
        Template.instance().wishCityIcon.set('wi wi-strong-wind');
        Template.instance().query.set('Wind');
        filteredArray = getCitiesListData(lat, long, 'Wind', APIkey);
      }

      console.log('filteredArray', filteredArray)
      console.log('----------------------------------------------')

      return filteredArray;
    }

})
}
