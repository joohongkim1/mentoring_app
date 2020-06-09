import React from 'react';
import SingupForm from '../components/Signup/SignupForm';

import { useSelector, useDispatch } from 'react-redux';
import { changeField, registerSuccess, registerError, emailState } from '../modules/Signup/signup'

import Header from '../components/layout/Header';

function SignupContainer() {
  const { user } = useSelector(state => ({
    user: state.signupReducer.user
  }));

  const dispatch = useDispatch();

  return (
    <div>
      <Header/>
      <SingupForm
        user={user}
      />
    </div>
  );
}
export default SignupContainer;