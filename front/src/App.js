import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignupPage from './pages/SignupPage';
import RTC from './pages/RTC';
import ResumePage from './pages/ResumePage';

function App() {
  return (
    <div className="App">
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/login" component={Login} />
        <Route path="/signup" component={SignupPage} />
        <Route path="/rtc" component={RTC} />
        <Route path="/resume" component={ResumePage} />
      </Switch>
    </div>
  );
}

export default App;
