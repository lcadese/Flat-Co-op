import React, { useState } from 'react';
import Welcome from './welcome';
import Login from './login';
import SignUp from './signup';
import LoginSuccess from './loginSuccess';

function App() {
  const [view, setView] = useState('welcome');
  const [user, setUser] = useState(null);

  const handleShowLogin = () => setView('login');
  const handleShowSignup = () => setView('signup');
  const handleSignupSuccess = () => setView('welcome');
  const handleLoginSuccess = (userData) => {
    setUser(userData);
    setView('loginSuccess');
  };

  return (
    <div className="App">
      {view === 'welcome' && <Welcome onShowLogin={handleShowLogin} onShowSignup={handleShowSignup} />}
      {view === 'login' && <Login onLoginSuccess={handleLoginSuccess} />}
      {view === 'signup' && <SignUp onSignUpSuccess={handleSignupSuccess} />}
      {view === 'loginSuccess' && <LoginSuccess user={user} />}
    </div>
  );
}

export default App;
