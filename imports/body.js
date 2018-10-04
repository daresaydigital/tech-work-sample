import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';

import './body.html';

 Template.body.onCreated(function bodyOnCreated() {

   // Sets the query to match which icon the user has clicked
   this.query = new ReactiveVar();
   this.showSun = new ReactiveVar(false);
   this.showRain = new ReactiveVar(false);
   this.showWind = new ReactiveVar(false);
   this.showCloud = new ReactiveVar(false);
   this.showSnow = new ReactiveVar(false);
 });


Template.body.helpers({

 //returns data for the user's current city
 currCityData() {
   return [
     { name: 'Stockholm',
       temp: '23degrees',
       icon: 'wi wi-day-sunny',
       pic: 'https://images.unsplash.com/photo-1535403142432-2c6aaca60f79?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=31442c6bc53f65d8d6dbbf48be38fea6&auto=format&fit=crop&w=1576&q=80'
     }
   ]
 },

 //returns array of cities that match the selected option(s) from the user
 cityData() {
   console.log('QUERY', Template.instance().query.get())
   let query = Template.instance().query.get();
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

   const currCity = 'stockholm';
   const clickedCity = 'berlin';
   const link = 'https://www.google.es/search?ei=dGq2W97bAYXMaIClhpgH&q=flights+' + currCity + '+' + clickedCity + '&oq=flights+' + currCity + '+' + clickedCity + '&gs_l=psy-ab.3...4710.10300.0.10510.0.0.0.0.0.0.0.0..0.0....0...1c.1.64.psy-ab..0.0.0....0.1_7PSriwDpI'

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
