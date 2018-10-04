import { Template } from 'meteor/templating';

import './body.html';

Template.body.helpers({
  cityData: [
    { cityName: 'Stockholm' },
    { cityName: 'Moscow' },
    { cityName: 'Prague' },
  ],
});
