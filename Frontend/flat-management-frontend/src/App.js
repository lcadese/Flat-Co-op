import React, { useState } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Welcome from './welcome';
import Login from './login';
import SignUp from './signup';
import LoginSuccess from './loginSuccess';
import CreateFlat from './createFlat';
import JoinFlat from './joinFlat';
import Navbar from './navbar';

function App() {
  const [view, setView] = useState('welcome');
  const [user, setUser] = useState(null);
  const [flat, setFlat] = useState(null);

  const handleShowLogin = () => setView('login');
  const handleShowSignup = () => setView('signup');
  const handleSignupSuccess = () => setView('welcome');
  const handleSendJoinFlat = () => setView('createFlat');
  const handleJoinFlat = (flatData) => {
    setFlat(flatData)
    console.log("In handle",  flatData);
    setView('loginSuccess');
  }
  const handleLoginSuccess = (userData) => {
    setUser(userData);
    // setView('loginSuccess');
    console.log("In handle",  userData);
    console.log(userData.flatID);
    if (userData.flatID === null) {
      setView('JoinFlat');
    } else {
      setView('loginSuccess');
    }

    // setView('createflat');
  };
  const handleCreateFlat = (flatData) => {
    setFlat(flatData)
    console.log("In handle",  flatData);
    setView('loginSuccess');
  } 

  return (
   
    <div className="App">
      {view === 'welcome' && <Welcome onShowLogin={handleShowLogin} onShowSignup={handleShowSignup} />}
      {view === 'login' && <Login onLoginSuccess={handleLoginSuccess} />}
      {view === 'signup' && <SignUp onSignUpSuccess={handleSignupSuccess} />}
      {view === 'loginSuccess' && <LoginSuccess user={user} />}
      {view === 'createFlat' && < CreateFlat onCreateSuccess = {handleCreateFlat} userData = {user}/>}
      {view === 'JoinFlat' && <JoinFlat createFlat = {handleSendJoinFlat} joinFlat = {handleJoinFlat} userData = {user}/>}
   
    </div>
     
  );


}

export default App;
 