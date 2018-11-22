import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { weatherIcons } from './weatherIcons.js';

//API Calls
import { APIkey, APIweatherkey } from '../settings.json';
import { getCurrentCityData } from './APIcalls/getCurrentCity.js'
import { getCurrentCityPic } from './APIcalls/getCurrentPic.js'
import { getCitiesListData } from './APIcalls/getCitiesList.js'

import './body.html';

Template.body.onCreated(function bodyOnCreated() {

  // checks if user has submitted the city form
  this.hasSubmittedForm = new ReactiveVar(false);

  // checks if user has selected an option for weather
  this.hasSelectedOption = new ReactiveVar(false);

  // sets the query to match which option the user has clicked
  this.query = new ReactiveVar();

  // sets the icon to match which option the user has clicked
  this.wishCityIcon = new ReactiveVar();
 });

if (Meteor.isClient) {

  Template.body.helpers({

   cityExists(){
     const cityExists = Session.get( 'cityExists');

     if (cityExists){
       const picExists = Session.get( 'picExists')
       let pic;

       if(picExists) {
         pic = Session.get( 'getCityPic');
         // set the picture background with a pic of the current city
         $('body').css('background-image', 'url(' + pic.url + ')');
         return pic;
       }
     } else {
       // unset the picture background
       $('body').css('background-image', 'url(#');
     }
     return cityExists;
   },

   weatherExists(){
     return Session.get('weatherExists');
   },

   // sets the current city data and pic info
   currCity(){
     const cityExists = Session.get('cityExists')
     let cityObject;

     city = Session.get('getCityData');
     pic = Session.get('getCityPic');

     //set cityObject
     cityObject = {
        name: city.name,
        temp: city.temp,
        icon: city.icon,
        description: city.description,
        picAuthor: pic.author,
        picLink: pic.link
     }
     // sets cityExists to false if there is no temperature data point
     if(!city.temp){
       Session.set('cityExists', false);
     }
     return cityObject;

   },

   hasSubmittedForm(){
     return Template.instance().hasSubmittedForm.get();
   },

   hasSelectedOption(){
     return Template.instance().hasSelectedOption.get();
   },

   selectedIcon() {
     return Template.instance().wishCityIcon.get();
   },

   noResult() {
     return Template.instance().noResults.get();
   },

   //returns array of cities that match the selected option(s) from the user
   weatherList() {
     const weatherCities = Session.get('getWishCityData')
     const weatherExists = Session.get('weatherExists')

     if(!weatherExists){
       return false;
     }
     return weatherCities;
   }

 })

   Template.body.events({

    // set the current city/centerpoint for the search
    'submit form': function(event){
      event.preventDefault();
      var city = event.target.cityName.value;

      Template.instance().hasSubmittedForm.set(true);

      // make API calls to get current city data and current city pic
      getCurrentCityData(city, APIkey, weatherIcons);
      getCurrentCityPic(city, APIweatherkey);
    },

    // set weather query to be passed to the wish list
    'click .option': function (event){

      console.log('----event.target-----', event.target.wi)

      // sets that the user has selected an option
      Template.instance().hasSelectedOption.set(true);

      this.$('.option').toggleClass('active')

      // sets the current city params to be passed to the second weather call getCitiesListData
      const currCityData = Session.get( 'getCityData');
      let lat = currCityData.lat;
      let long = currCityData.long;

      // checks what weather option the user clicked and sets icon and query accordingly
      let weatherType = event.currentTarget.id;
      let weatherTypeUc = weatherType[0].toUpperCase() + weatherType.substr(1);
      Template.instance().wishCityIcon.set('wi wi-' + weatherType);
      Template.instance().query.set(weatherTypeUc);

      // call weather api to return a range of cities within an area of the current city, which matches the weather query
      const currWeatherSet = getCitiesListData(currCityData.name, lat, long, weatherTypeUc, APIkey);
      console.log('-------currWeatherSet------', currWeatherSet)
    }

})
}
