import React from 'react';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core';
import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import Header from './components/layout/Header';

import rootReducer from './modules';

const theme = createMuiTheme({
  // typography: {
  //   fontFamily: 'SCDream',
  // },
  // palette: {
  //   primary: {
  //     main: '#7dabd0',
  //     contrastText: '#fff',
  //   },
  //   secondary: {
  //     main: '#de586d',
  //     contrastText: '#fff',
  //   },
  //   warning: {
  //     main: '#ecdb54',
  //     contrastText: '#fff',
  //   },
  // },
});

const store = createStore(
  rootReducer,
  window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__(),
);

ReactDOM.render(
  <Provider store={store}>
    <MuiThemeProvider theme={theme}>
      <BrowserRouter>
        <Header />
        <App />
      </BrowserRouter>
    </MuiThemeProvider>
  </Provider>,

  document.getElementById('root'),
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
