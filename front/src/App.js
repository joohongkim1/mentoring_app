import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignupPage from './pages/SignupPage';
import RTC from './pages/RTC';
import ResumePage from './pages/ResumePage';
import JasoseoListPage from './pages/Jasoseo/JasoseoListPage';
import JasoseoWritePage from './pages/Jasoseo/JasosoeWritePage';
import ExperiencePage from './pages/ExperiencePage';
import ExperienceWritePage from './pages/Experience/ExperienceWritePage';

function App() {
  return (
    <div className="App">
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/rtc" component={RTC} />
        <Route path="/resume" component={ResumePage} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/signup" component={SignupPage} />
        <Route exact path="/jasoseo" component={JasoseoListPage} />
        <Route exact path="/jasoseowrite" component={JasoseoWritePage} />
        <Route exact path="/experience" component={ExperiencePage} />
        <Route exact path="/experiencewrite" component={ExperienceWritePage} />

        </Switch>
    </div>
  );
}

export default App;
