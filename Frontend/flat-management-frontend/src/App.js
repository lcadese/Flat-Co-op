import React, { useState } from 'react';
import axios from 'axios';
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
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
  const [user, setUser] = useState(null);
  const [flat, setFlat] = useState(null);
  const [tasks, setTasks] = useState([]);

  return (
    <div className="App">
      <Router>
        <Navbar user={user} />
        <RouterComponent 
          user={user}
          setUser={setUser}
          flat={flat}
          setFlat={setFlat}
          tasks={tasks}
          setTasks={setTasks}
        />
      </Router>
    </div>
  );
}

function RouterComponent({ user, setUser, flat, setFlat, tasks, setTasks }) {
  const navigate = useNavigate();

  const handleLoginSuccess = async (userData) => {
    setUser(userData);
    if (userData.flatID === null) {
      navigate('/joinFlat');
    } else {
      try {
        const response = await axios.get(`http://localhost:8080/flat/${userData.flatID}`);
        setFlat(response.data);
        navigate('/loginSuccess');
      } catch (error) {
        console.error('Failed to fetch flat data', error);
      }
    }
  };

  const handleCalendarTest = async (tasksData) => {
    setTasks(tasksData);
    console.log("In handle", tasksData);
    navigate('/calendar');
  }

  const handleSignupSuccess = () => navigate('/');

  return (
    <Routes>
      <Route path="/" element={<Welcome onShowLogin={() => navigate('/login')} onShowSignup={() => navigate('/signup')} />} />
      <Route path="/login" element={<Login onLoginSuccess={handleLoginSuccess} />} />
      <Route path="/signup" element={<SignUp onSignUpSuccess={handleSignupSuccess} />} />
      <Route path="/loginSuccess" element={user ? <LoginSuccess user={user} onCalendarTest={handleCalendarTest} /> : <div>Loading...</div>} />
      <Route path="/createFlat" element={<CreateFlat onCreateSuccess={(flatData) => { setFlat(flatData); navigate('/loginSuccess'); }} userData={user} />} />
      <Route path="/joinFlat" element={<JoinFlat createFlat={() => navigate('/createFlat')} joinFlat={(flatData) => { setFlat(flatData); navigate('/loginSuccess'); }} userData={user} />} />
      <Route path="/calendar" element={<Calendar tasksData={tasks} />} />
      <Route path="/tasks" element={<Tasks flatData = {flat} />} />
      <Route path="/payments" element={<Payments flatData = {flat}/>} />
    </Routes>
  );
}

export default App;
