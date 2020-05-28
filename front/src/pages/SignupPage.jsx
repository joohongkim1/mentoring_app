import React from 'react';
import SignupContainer from '../containers/SignupContainer'

export default function SignupPage({ match }) {
    return(
        <div>
            <h1>회원가입</h1>
            <SignupContainer />
        </div>
    )
}