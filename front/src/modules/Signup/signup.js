const SIGNUP = 'user/REGISTER'
const REGISTER_REQUEST = 'user/REGISTER_REQUEST';
const REGISTER_SUCCESS = 'user/REGISTER_SUCCESS';
const REGISTER_ERROR = 'user/REGISTER_ERROR';
const EMAIL_STATE = 'user/EMAIL_STATE';

export const sigup = (email, password) => ({
    type: SIGNUP,
    promise: {method: 'post', url: '/signup', data: {email, password}}
});

const initialState = {
    user: {},
    isRegisterSuccess: false,
    fetchingUpdate: false,
    registerError: '',
    emailState: '',
};

export const registerSuccess = isRegisterSuccess => ({
    type: 'user/REGISTER_SUCCESS',
    isRegisterSuccess
})
export const registerError = registerError => ({
    type: 'user/REGISTER_ERROR',
    registerError
})
export const emailState = emailState => ({
    type: 'user/EMAIL_STATE',
    emailState
})

// export default function register() {
//     return ({})
// }

export default function signupReducer(state = initialState, action) {
    switch (action.type) {
        case REGISTER_SUCCESS:
            return {
                ...state,
                isRegisterSuccess: true
            };
        case REGISTER_ERROR:
            return {
                ...state,
                registerError: action.registerError,
                isRegisterSuccess: false
            };
        case EMAIL_STATE:
            return {
                ...state,
                emailState: action.emailState
            };

        default:
            return state;
    }
}
