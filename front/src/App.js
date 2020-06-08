import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignupPage from './pages/SignupPage';
import JasoseoListPage from './pages/Jasoseo/JasoseoListPage';
import JasoseoWritePage from './pages/Jasoseo/JasosoeWritePage';
import ExperiencePage from './pages/ExperiencePage';
import ExperienceWritePage from './pages/Experience/ExperienceWritePage';
import RTC from './pages/RTC';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Route exact path="/" component={Home} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/signup" component={SignupPage} />
        <Route exact path="/jasoseo" component={JasoseoListPage} />
        <Route exact path="/jasoseowrite" component={JasoseoWritePage} />
        <Route exact path="/experience" component={ExperiencePage} />
        <Route exact path="/experiencewrite" component={ExperienceWritePage} />

        <Route exact path="/rtc" component={RTC} />
      </BrowserRouter>
    </div>
  );
}

export default App;
