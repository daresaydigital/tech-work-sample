import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';
import { weatherIcons } from './weatherIcons.js';
import { APIkey, APIweatherkey } from '../settings.json';
import { getCurrentCityData } from './APIcalls/getCurrentCity.js'
import { getCurrentCityPic } from './APIcalls/getCurrentPic.js'
import { getCitiesListData } from './APIcalls/getCitiesList.js'

import './body.html';

Template.body.onCreated(function bodyOnCreated() {

  // name of city the user sets as center point
  this.city = new ReactiveVar();

  // sets the query to match which option the user has clicked
  this.query = new ReactiveVar();

  // initiates showing the list of cities the user wants to go to, and it's associated icon.
  this.hasSelectedOption = new ReactiveVar(false);
  this.wishCityIcon = new ReactiveVar();

  // initiates the original state of a list which the user then populates when clicking an option
  this.weatherList = new ReactiveVar();
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

   // sets the current city data and pic info
   currCity(){
     const cityExists = Session.get( 'cityExists')
     let cityObject;

     city = Session.get( 'getCityData');
     pic = Session.get( 'getCityPic');

     //set cityObject
     cityObject = {
        name: city.name,
        temp: city.temp,
        icon: city.icon,
        description: city.description,
        picAuthor: pic.author,
        picLink: pic.link
     }
     return cityObject;

   },

   currQuery(){
     return Template.instance().query.get();
   },

   selectedIcon() {
     return Template.instance().wishCityIcon.get();
   },

   weatherList() {
     return Template.instance().weatherList.get();
   },

   //returns array of cities that match the selected option(s) from the user
   wishCitiesList() {

      //checks if user has selected an option, and only runs the below in case they have.
      if (Template.instance().hasSelectedOption.get()){
       var currCity = Template.instance().city.get();
       var data = Session.get( 'getWishCityData');

       //sets link to take the user to a google flight search for the city
       return data.map(city => {
        return {
            name: city.name,
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

      // make API calls to get current city data and current city pic
      getCurrentCityData(city, APIkey, weatherIcons);
      getCurrentCityPic(city, APIweatherkey);
    },

    // set weather query to be passed to the wish list
    'click .option': function (event){

      //sets that the user has selected an option
      Template.instance().hasSelectedOption.set(true);

      // call weather api to return a range of cities within an area of the current city, which matches the weather query
      const currCityData = Session.get( 'getCityData');

      let long = currCityData.long;
      let lat = currCityData.lat;

      // checks what the user selected and sets icon and query accordingly
      let weatherType = event.currentTarget.id;
      let weatherTypeUc = weatherType[0].toUpperCase() + weatherType.substr(1);
      Template.instance().wishCityIcon.set('wi wi-' + weatherType);
      Template.instance().query.set(weatherTypeUc);

      // make the getCitiesList API call and set the result as 'weatherList'
      Template.instance().weatherList.set(getCitiesListData(lat, long, weatherTypeUc, APIkey));
    }

})
}
