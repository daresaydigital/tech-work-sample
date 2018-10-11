import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';

import './body.html';

 Template.body.onCreated(function bodyOnCreated() {

   // Sets the query to match which icon the user has clicked
   this.query = new ReactiveVar();
   this.city = new ReactiveVar();
   this.showSun = new ReactiveVar(false);
   this.showRain = new ReactiveVar(false);
   this.showWind = new ReactiveVar(false);
   this.showCloud = new ReactiveVar(false);
   this.showSnow = new ReactiveVar(false);

   this.cityData = new ReactiveVar();
   this.cityPic = new ReactiveVar();
 });

 // performSearch = (query = 'sun') => {
 //   Promise.all([
 //     axios.get('https://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=9b5e8856a8ad837d711bf4c74b6e0ecf'),
 //     axios.get('https://api.unsplash.com/search/photos/?page=1&per_page=10&query=${query}&client_id=bf54ced5af6f6f686d571a602ff4611ebf8fc2ebdc59989cdf768941e85148bb')
 //   ])
 //   // use arrow function to avoid loosing context
 //   .then(([weatherResponse, picsResponse]) => {
 //     console.log('weatherResponse.data', weatherResponse.data)
 //     console.log('picsResponse.data.results', picsResponse.data.results)
 //           this.setState({weather: weatherResponse.data, pics: picsResponse.data.results});
 //     })
 // 		.catch(err => {
 // 			console.log('Error happened during fetching!', err);
 // 		});
 // }


//API calls
// 1. get current location (enter city)
// 2. return a) curr city b) curr degrees c) curr weather picsResponse
// 3. search function that takes query indicating weather (sun, snow, rain...)(to be tied to on click for when user clicks a weather icon) and returns data cities matching

function wishCitiesWeather(weather) {
   $.getJSON('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&APPID=9b5e8856a8ad837d711bf4c74b6e0ecf', function (data) {
     var rawJson = JSON.stringify(data);
     var json = JSON.parse(rawJson);
     console.dir(json); //Update Weather parameters
     this.cityData.set(json);
   });
 };

function currentCityPic() {
   let city = Template.instance().city.get();
   $.getJSON('https://api.unsplash.com/search/photos/?page=1&per_page=1&query=' + city + '&client_id=bf54ced5af6f6f686d571a602ff4611ebf8fc2ebdc59989cdf768941e85148bb', function (data) {
     var rawJson = JSON.stringify(data);
     var json = JSON.parse(rawJson);
     console.log('---------currentCityPic gets this city: ----------------', city)
     console.log('---------currentCityPic returns: ----------------', json)

     Template.instance().cityPic.set(json.results["0"].urls.regular);
   });
 };

function currentCityWeather() {
   let city = Template.instance().city.get();
   $.getJSON('http://api.openweathermap.org/data/2.5/weather?q=' + city + '&APPID=9b5e8856a8ad837d711bf4c74b6e0ecf', function (data) {
     var rawJson = JSON.stringify(data);
     var json = JSON.parse(rawJson);
     console.log('---------currentCityWeather returns: ----------------', json)
     Template.instance().cityData.set(json);
   });
 };



Template.body.helpers({

 currCityData() {
   const data = Template.instance().cityData.get();
   const pic = Template.instance().cityPic.get();
   console.log('------data----', data)
   console.log('------pic----', pic)
   return [
     { name: data.name,
       temp: data.main.temp,
       pic: pic,
     }
   ]
 },

 //returns array of cities that match the selected option(s) from the user
 cityData() {
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

  // set the current city/centerpoint for the search
  'submit form': function(event){
    event.preventDefault();
    var city = event.target.cityName.value;
    Template.instance().city.set(city);
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
