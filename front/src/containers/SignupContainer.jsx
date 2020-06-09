import React from 'react';
import SingupForm from '../components/Signup/SignupForm';

import { useSelector, useDispatch } from 'react-redux';
import { changeField, registerSuccess, registerError, emailState } from '../modules/Signup/signup'

function SignupContainer() {
  const { user } = useSelector(state => ({
    user: state.signupReducer.user
  }));

  const dispatch = useDispatch();
  
  return (
    <SingupForm
      user={user}
    />
  );
}
export default SignupContainer;