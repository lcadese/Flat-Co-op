import React, { useState } from 'react';
import axios from 'axios';
//import { BrowserRouter as Router, Route } from 'react-router-dom';
import Welcome from './welcome';
import Login from './login';
import SignUp from './signup';
import LoginSuccess from './loginSuccess';
import CreateFlat from './createFlat';
import JoinFlat from './joinFlat';
import Calendar from './calendar';

function App() {
  const [view, setView] = useState('welcome');
  const [user, setUser] = useState(null);
  const [flat, setFlat] = useState(null);
  const [tasks, setTask] = useState([]);

  const handleShowLogin = () => setView('login');
  const handleShowSignup = () => setView('signup');
  const handleSignupSuccess = () => setView('welcome');
  const handleSendJoinFlat = () => setView('createFlat');

  const handleJoinFlat = (flatData) => {
    setFlat(flatData);
    console.log("In handle",  flatData);
    setView('loginSuccess');
  }

  const handleCalendarTest = async (tasksData) => {
    setTask(tasksData);
    console.log("In handle",  tasksData);
    setView('calendar');
  }

  const handleLoginSuccess = async (userData) => {
    setUser(userData);

    console.log("In handle",  userData);
    if (userData.flatID === null) {
      setView('JoinFlat');
    } else {
      const response = await axios.get('http://localhost:8080/flat/'+userData.flatID);
      setFlat(response.data);
      setView('loginSuccess');
    }

  };
  const handleCreateFlat = (flatData) => {
    setFlat(flatData);
    console.log("In handle",  flatData);
    setView('loginSuccess');
  }

  return (
   
    <div className="App">
      {view === 'welcome' && <Welcome onShowLogin={handleShowLogin} onShowSignup={handleShowSignup}/>}
      {view === 'login' && <Login onLoginSuccess={handleLoginSuccess} />}
      {view === 'signup' && <SignUp onSignUpSuccess={handleSignupSuccess} />}
      {view === 'loginSuccess' && <LoginSuccess user={user} onCalendarTest = {handleCalendarTest}/>}
      {view === 'createFlat' && < CreateFlat onCreateSuccess = {handleCreateFlat} userData = {user}/>}
      {view === 'JoinFlat' && <JoinFlat createFlat = {handleSendJoinFlat} joinFlat = {handleJoinFlat} userData = {user}/>}
      {view === 'calendar' && <Calendar tasksData = {tasks} />}
    </div>
     
  );


}

export default App;