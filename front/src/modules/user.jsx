import useAxios from 'axios-hooks';

// 액션 타입
const LOGIN = 'user/LOGIN';
const LOGIN_REQUEST = 'user/LOGIN_REQUEST';
const LOGIN_SUCCESS = 'user/LOGIN_SUCCESS';
const LOGIN_FAILURE = 'user/LOGIN_FAILURE';

// export const loginRequest = (id, password) => {
//   const [{ data: result = {}, loading }, doLogin] = useAxios(
//     {
//       url: '/auth/login',
//       method: 'POST',
//       data: {
//         email,
//         password,
//       },
//     },
//     { manual: true },
//   );
// };

export const login = (id, password) => ({
  type: LOGIN,
  promise: { method: 'post', url: '/login', data: { id, password } },
});

// 초기 상태
const initialState = {
  isLoggedIn: false,
  fetchingUpdate: false,
  user: {},
};

export const loginSuccess = (id) => ({
  type: LOGIN_SUCCESS,
  payload: id,
});
export const loginFailure = (error) => ({
  type: LOGIN_FAILURE,
  payload: error,
});

const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOGIN_REQUEST:
      return {
        ...state,
        fetchingUpdate: true,
      };
    case LOGIN_SUCCESS:
      return {
        ...state,
        fetchingUpdate: false,
        isLoggedIn: true,
        user: action.result,
      };
    case LOGIN_FAILURE:
      return {
        ...state,
        fetchingUpdate: false,
      };
    default:
      return state;
  }
};

export default userReducer;
