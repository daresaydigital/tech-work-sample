import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { Geolocation } from 'meteor/mdg:geolocation';
import { Tracker } from 'meteor/tracker'
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

  console.log('So - the geolocation in my environment did not work as I am on an insecure network. ' +
  'I made a variation that instead 1. asks for a city 2. gets the weather for this and 3. sets that city ' +
  'as the center point for a second API call that returns a list of cities with a certain weather type.'
  )
 });

if (Meteor.isClient) {

  Template.body.helpers({

   cityExists(){
     const cityExists = Session.get( 'cityExists');

     if (cityExists){
       const pic = Session.get( 'getCityPic');
       // set the picture background with a pic of the current city
       $('body').css('background-image', 'url(' + pic.url + ')');
     } else {
       const pict = 'https://images.unsplash.com/photo-1504387103978-e4ee71416c38?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=864eaecaf88546a104521ac381504e4b&auto=format&fit=crop&w=634&q=80';
       // set the picture background with a default pic
       $('body').css('background-image', 'url(' + pict + ')');
     }
     // return true or false - used to show/hide elements in UI
     return cityExists;
   },

   // sets the current city data and pic info
   currCity(){
     const cityExists = Session.get('cityExists')
     let cityObject;

     city = Session.get('getCityData');
     pic = Session.get('getCityPic');

     if(city && pic) {
       //set cityObject
       cityObject = {
          name: city.name,
          temp: city.temp,
          icon: city.icon,
          description: city.description,
          picAuthor: pic.author,
          picLink: pic.link
       }
     } else {
       Session.set('clearCitiesList', true);
       Session.set('cityExists', false);
     }
     return cityObject;
   },

   // returns true if we are currently loading the list of cities
   loadingList(){
     return Session.get('loadingList');
   },

   // returns a text message if there are no cities with the selected weather
   textMessage(){
     return Session.get('textMessage');
   },

   // determines when the list of returned cities should be cleared
   clearCitiesList(){
     return Session.get( 'clearCitiesList');
   },

   // checks if user has entered a city name
   hasSubmittedForm(){
     return Template.instance().hasSubmittedForm.get();
   },

   // returns an icon matching which weather option the user selected
   selectedIcon() {
     return Template.instance().wishCityIcon.get();
   },

   //returns array of cities that match the selected option(s) from the user
   weatherList() {
     const weatherCities = Session.get('getWishCityData')

     if(!weatherCities || (weatherCities === false)){
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
      Session.set( 'clearCitiesList', true)
      Session.set( 'loadingList', false)
      Session.set('textMessage', false)

      // make API calls to get current city data and current city pic
      getCurrentCityData(city, APIkey, weatherIcons);
      getCurrentCityPic(city, APIweatherkey);
    },

    // set weather query to be passed to the wish list
    'click .option': function (event){
      Session.set( 'loadingList', false)
      Session.set('textMessage', false)
      Session.set( 'clearCitiesList', true)

      // sets that the user has selected an option
      Template.instance().hasSelectedOption.set(true);

      // sets the current city params to be passed to the second weather call getCitiesListData
      const currCityData = Session.get( 'getCityData');
      let lat = currCityData.lat;
      let long = currCityData.long;

      // checks what weather option the user clicked and sets icon and query accordingly
      let weatherType = event.currentTarget.id;
      const clickedId = '#' + weatherType;

      $('i.active').removeClass('active');
      $(clickedId).toggleClass('active');

      if (weatherType === 'clear'){
        Template.instance().wishCityIcon.set('wi wi-day-sunny');
      } else {
        Template.instance().wishCityIcon.set('wi wi-' + weatherType);
      }
      let weatherTypeUc = weatherType[0].toUpperCase() + weatherType.substr(1);
      Template.instance().query.set(weatherTypeUc);

      // call weather api to return a range of cities within an area of the current city, which matches the weather query
      getCitiesListData(currCityData.name, lat, long, weatherTypeUc, APIkey);
    }

  })
}
