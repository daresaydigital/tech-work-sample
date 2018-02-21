import { CHANGE_PAGE } from '../actions/changePage';

// Just to show how combine reducers work, we have
// divided into two reducers member load + member load/update/delete
export default (state: string = 'progress', action) => {

  switch (action.type) {
    case CHANGE_PAGE:
      return action.page;

    default:
      return state;
  }
};
