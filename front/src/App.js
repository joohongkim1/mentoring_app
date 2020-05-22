import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import Home from './pages/home';
import SignupPage from './pages/SignupPage';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Route exact path="/" component={Home} />
        <Route exact path="/signup" component={SignupPage} />
      </BrowserRouter>
    </div>
  );
}

export default App;
