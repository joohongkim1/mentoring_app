const initialState = {
    user: {},
    isRegisterSuccess: false,
    registerError: '',
    emailState: '',
};

const CHANGE_FIELD = 'signup/CHANGE_FIELD';
const REGISTER_REQUEST = 'sginup/REGISTER_REQUEST';
const REGISTER_SUCCESS = 'signup/REGISTER_SUCCESS';
const REGISTER_ERROR = 'signup/REGISTER_ERROR';
const EMAIL_STATE = 'signup/EMAIL_STATE';

export const changeField = user => ({
    type: 'signup/CHANGE_FIELD',
    user
});


export const registerSuccess = isRegisterSuccess => ({
    type: 'signup/REGISTER_SUCCESS',
    isRegisterSuccess
})

export const registerError = registerError => ({
    type: 'signup/REGISTER_ERROR',
    registerError
})

export const emailState = emailState => ({
    type: 'signup/EMAIL_STATE',
    emailState
})

// export default function register() {
//     return ({})
// }

export default function signup(state = initialState, action) {
    switch (action.type) {
        case CHANGE_FIELD:
            return {
                ...state,
                user: action.user
            };
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
