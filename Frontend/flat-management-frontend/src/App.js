import React, { useState } from 'react';
import axios from 'axios';
//import { BrowserRouter as Router, Route } from 'react-router-dom';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Welcome from './welcome';
import Login from './login';
import SignUp from './signup';
import LoginSuccess from './loginSuccess';
import CreateFlat from './createFlat';
import JoinFlat from './joinFlat';
import Calendar from './calendar';
import Navbar from './navbar';
import Tasks from './tasks';
import Payments from './payments';

function App() {
  const [view, setView] = useState('welcome');
  const [user, setUser] = useState(null);
  const [flat, setFlat] = useState(null);
  const [tasks, setTask] = useState([]);

  const handleShowLogin = () => {
    console.log("Login button clicked");
    setView('login');
  }
  const handleShowSignup = () => setView('signup');
  const handleSignupSuccess = () => setView('welcome');
  const handleSendJoinFlat = () => setView('createFlat');

  const handleJoinFlat = (flatData) => {
    setFlat(flatData);
    console.log("In handle",  flatData);
    setFlat(flatData)
    console.log("In handle", flatData);
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
    // setView('loginSuccess');
    console.log("In handle", userData);
    console.log(userData.flatID);
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


  let Component;
  switch (view) {
    case 'welcome':
      Component = <Welcome onShowLogin={handleShowLogin} onShowSignup={handleShowSignup} />;
      break;
    case 'login':
      Component = <Login onLoginSuccess={handleLoginSuccess} />;
      break;
    case 'signup':
      Component = <SignUp onSignUpSuccess={handleSignupSuccess} />;
      break;
    case 'loginSuccess':
      Component = <LoginSuccess user={user} />;
      break;
    case 'createFlat':
      Component = <CreateFlat onCreateSuccess={handleCreateFlat} userData={user} />;
      break;
    case 'JoinFlat':
      Component = <JoinFlat createFlat={handleSendJoinFlat} joinFlat={handleJoinFlat} userData={user} />;
      break;
    default:
      Component = null;
      break;
  }

  return (

    <div className="App">
      {/* {view === 'welcome' && <Welcome onShowLogin={handleShowLogin} onShowSignup={handleShowSignup}/>} */}


      <Router>

        <Navbar />
        <Routes>
          <Route
            path="/"
            element={Component} />
          <Route path="/tasks" element={<Tasks />} />
          <Route path="/payments" element={<Payments />} />
        </Routes>

      </Router>

      {/* 
      {view === 'welcome' && <Welcome onShowLogin={handleShowLogin} onShowSignup={handleShowSignup} />}
      {view === 'login' && <Login onLoginSuccess={handleLoginSuccess} />}
      {view === 'signup' && <SignUp onSignUpSuccess={handleSignupSuccess} />}
      {view === 'loginSuccess' && <LoginSuccess user={user} onCalendarTest = {handleCalendarTest}/>}
      {view === 'createFlat' && < CreateFlat onCreateSuccess = {handleCreateFlat} userData = {user}/>}
      {view === 'JoinFlat' && <JoinFlat createFlat = {handleSendJoinFlat} joinFlat = {handleJoinFlat} userData = {user}/>}
      {view === 'calendar' && <Calendar tasksData = {tasks} />}
      {view === 'JoinFlat' && <JoinFlat createFlat = {handleSendJoinFlat} joinFlat = {handleJoinFlat} userData = {user}/>} */}


    </div>

  );


    }

export default App;
