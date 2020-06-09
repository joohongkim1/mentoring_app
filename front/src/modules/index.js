import { combineReducers } from 'redux';
import userReducer from './user';
import signupReducer from './Signup/signup';


const rootReducer = combineReducers({
  userReducer,
  signupReducer,
});

export default rootReducer;
