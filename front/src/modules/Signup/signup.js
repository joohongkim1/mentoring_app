const SIGNUP = 'user/REGISTER'
const REGISTER_INITIALIZE_INPUT = "user/REGISTER_INITIALIZE_INPUT";
const REGISTER_CHANGE_INPUT_ALL = "user/REGISTER_CHANGE_INPUT_ALL";
const REGISTER_CHANGE_INPUT = "user/REGISTER_CHANGE_INPUT";
const REGISTER_REQUEST = 'user/REGISTER_REQUEST';
const REGISTER_SUCCESS = 'user/REGISTER_SUCCESS';
const REGISTER_ERROR = 'user/REGISTER_ERROR';

const initialState = {
  user: {
    "stu_auth": 0,
    "stu_id_email": "",
    "stu_major": "",
    "stu_name": "",
    "stu_password": "",
    "stu_school": "",
    "stu_total_mileage": 0
  },
  isRegisterSuccess: false,
  fetchingUpdate: false,
  registerError: '',
};

export const sigup = (email, password) => ({
  type: SIGNUP,
  promise: { method: 'post', url: '/signup', data: { email, password } }
});

export const initializeInput = () => ({
  type: REGISTER_INITIALIZE_INPUT
});

export const changeInputAll = user => ({
  type: REGISTER_CHANGE_INPUT_ALL,
  user
});

export const changeInput = ({ name, value }) => ({
  type: REGISTER_CHANGE_INPUT,
  payload: {
    name,
    value
  }
});

export const registerSuccess = isRegisterSuccess => ({
  type: REGISTER_SUCCESS,
  isRegisterSuccess
})
export const registerError = registerError => ({
  type: REGISTER_ERROR,
  registerError
})

// export default function register() {
//     return ({})
// }

export default function signupReducer(state = initialState, action) {
  switch (action.type) {
    case REGISTER_INITIALIZE_INPUT:
      return {
        ...state,
        user: {
          username: "",
          password: ""
        }
      };
    case REGISTER_CHANGE_INPUT_ALL:
      return {
        ...state,
        user: action.user
      };
    case REGISTER_CHANGE_INPUT:
      let newUser = state.user;
      newUser[action.payload.name] = action.payload.value;
      return {
        ...state,
        user: newUser
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
    default:
      return state;
  }
}
