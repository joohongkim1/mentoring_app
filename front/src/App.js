import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import SignupPage from './pages/SignupPage';
import Mentoring from './pages/Mentoring';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Route exact path="/" component={Home} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/signup" component={SignupPage} />
        <Route exact path="/mentoring" component={Mentoring} />
      </BrowserRouter>
    </div>
  );
}

export default App;
