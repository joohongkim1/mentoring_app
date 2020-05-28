import React from 'react';
import SingupForm from '../components/Signup/SignupForm';

import { useSelector, useDispatch } from 'react-redux';
import { changeField, registerSuccess, registerError, emailState } from '../modules/Signup/signup'

function SignupContainer() {
    const { user } = useSelector(state => ({
        user: state.signup.user
    }));

    const dispatch = useDispatch();

    const onChangeField = user => {
        dispatch(changeField(user));
        console.log(user);
    }

    return (
        <SingupForm
            user={user}
            onChangeField={onChangeField} 
        />
    );
}
export default SignupContainer;