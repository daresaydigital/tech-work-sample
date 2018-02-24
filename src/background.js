/* global chrome */
import { XmlEntities } from 'html-entities';
import weather from './helpers/weather';

/* Interval in minutes between updates. The first update will
be executed when the clock strikes a multiple of the interval. */
const interval = 1;

/* Declare the App state */
type AppState = {
  frontend:{
    config: {
      language:string,
      units:string,
    },
    data:{
      weather: {
        temp:number,
        max:number,
        min:number,
        conditions:string,
      },
      forecast:{
        hourly:array,
        daily:array,
      },
    },
  },
  backend:{
    port:string,
    time:date,
  }
}

/* Get the state from Chrome storage if available */
chrome.storage.sync.get((storedState:AppState) => {
  let state: AppState = {};
  let defaultState = storedState;
  let local = {};

  local.setState = function(newState: AppState) {
    state = Object.assign(state, newState);
  }

  /* Listen for messages from other parts of the extension */
  chrome.runtime.onConnect.addListener((port) => {
    const backend = state.backend;
    backend.port = port;
    local.setState({backend});
    port.onMessage.addListener(function(msg) {
      switch(msg.type){
        case 'get':
          /* Another part of the extension is requesting the state */
          port.postMessage({state: state.frontend});
          break;
        case 'post':
          /* Another part of the extension is sending data */
          break;
        default:
          /* Default case */
          break;
      }
    });

    port.onDisconnect.addListener(function() {
      const backend = state.backend;
      backend.port = null;
      local.setState({backend});
    });
  });

  /* Send messages to content.js */
  local.message = function(status, content){
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
      if(tabs.length > 0){
        chrome.tabs.sendMessage(tabs[0].id, { status, content }, function(response) {
        });
      }
    });
  }

  /* Handle context click */
  local.onContextClick = (info, tab) => {
    local.fetchData('weather', entries => {
      local.message( 'ok', entries );
    }, e => {
      local.message( 'error', info.selectionText );
    }, info.selectionText);
  }

  /* Create context menu item for each context type. */
  const contexts = ["selection","link"];
  for (var i = 0; i < contexts.length; i++) {
    const context = contexts[i];
    const title = "Search on Weatherwise";
    const id = chrome.contextMenus.create({
      "title": title, "contexts":[context],
      "onclick": local.onContextClick
    });
  }

  local.dispatchUpdates = function(){
    /* Update current weather conditions */
    local.updateData('weather');
    /* Get current time to compare with time of last update */
    const now = new Date();
    /* If an hour has passed since last update, update hourly forecast */
    if(now.getHours() > state.backend.time.getHours()){
      local.updateData('forecast');
    }
    /* If a day has passed since last update, update daily forecast */
    if(now.getDate() > state.backend.time.getDate()){
      local.updateData('forecast/daily');
    }
    const backend = state.backend;
    /* Save time of update */
    backend.time = now;
    local.setState({ backend });
  }

  /* Get the number of miliseconds until the next multiple of the interval */
  local.millisTillNext = function(){
    const now = new Date();
    const minutes = now.getMinutes();
    const nextPeriod = minutes + (interval - (minutes % interval) );
    return new Date(now.getFullYear(), now.getMonth(), now.getDate(), now.getHours(), nextPeriod, 0, 0) - now;
  }

  local.toDate = function(text){
    return new Date(text);
  }

  local.updateData = (endpoint) =>{
    local.fetchData(endpoint, entries =>{
      const frontend = state.frontend;
      switch(endpoint){
        case 'forecast':
          frontend.data.forecast.hourly = entries;
          break;
        case 'forecast/daily':
          frontend.data.forecast.daily = entries;
          break;
        default:
          frontend.data.weather = entries;
          break;
      }
      local.setState({ frontend });
      if(state.backend.port){
        state.backend.port.postMessage({state: state.frontend});
      }
    });
  }

  local.capitalizeFirstLetter = (string) => {
      return string.charAt(0).toUpperCase() + string.slice(1);
  }

  local.fetchData = (endpoint, callback, error, address) => {
    const options = {
        'endpoint': endpoint,
        'params':{
          units:'metric',
          language:'en',
        }
      }
    if(address){
      /* If an address is received, pass it on. */
      options.address = address;
    }
    weather(options).then( values => {
        let entries;
        console.log(values['data']);
        switch(endpoint){
          case 'forecast':
            /* Format each entry, limiting to 9 entries (enough to show 24+ hours) */
            entries = values['data'].list.slice(0,9).map(entry => ({
              temp: entry.main.temp,
              max: entry.main.temp_min,
              min: entry.main.temp_max,
              conditions: local.capitalizeFirstLetter(entry.weather[0].description),
              /* Save the date in miliseconds*/
              date: entry.dt * 1000,
            }))
            break;
          case 'forecast/daily':
            /* Format each entry, removing the first entry */
            entries = values['data'].list.slice(1).map(entry => ({
              temp: entry.temp.day,
              max: entry.temp.min,
              min: entry.temp.max,
              conditions: local.capitalizeFirstLetter(entry.weather[0].description),
              /* Save the date in miliseconds*/
              date: entry.dt * 1000,
            }))
            break;
          default:
            entries = {
              temp: values['data'].main.temp,
              max: values['data'].main.temp_min,
              min: values['data'].main.temp_max,
              conditions: local.capitalizeFirstLetter(values['data'].weather[0].description),
            }
            /* When using geocoding, the parsed location should be part of the response */
            if (values['data'].location) {
              entries.location = values['data'].location;
            }
            break;
        }
        callback(entries)
      } , (e)=>{ error(e) });
  }

  defaultState = {
    frontend:{
      config: {
        language:'en',
        units:'metric',
      },
      data:{
        weather: {
          temp:0,
          max:0,
          min:0,
          conditions:'',
        },
        forecast:{
          hourly:[],
          daily:[],
        },
      },
    },
    backend:{
      port:'',
      time:new Date(1970, 1, 1, 0, 0, 0, 0),
    },
  }
  local.setState({ ...defaultState, ...storedState });
  local.dispatchUpdates();
  setTimeout( () => {
    /* Use interval in miliseconds */
    setInterval(local.dispatchUpdates, interval * 60 * 1000);
  }, local.millisTillNext());
});
