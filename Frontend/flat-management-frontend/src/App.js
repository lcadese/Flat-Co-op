// App.js

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
import Profile from './profile';

function App() {
  const [user, setUser] = useState(null);
  const [flat, setFlat] = useState(null);
  const [tasks, setTasks] = useState([]);

  const handleLogout = () => {
    setUser(null);
    setFlat(null);
  };

  const handleCalendarTest = async (flatID) => {
    try {
      const response = await axios.get(`http://localhost:8080/tasks/flatID/${flatID}`);
      const tasksWithUsers = await Promise.all(response.data.map(async task => {
        const assignedUsersResponse = await axios.get(`http://localhost:8080/assigned/task/${task.taskID}`);
        const assignedUsersDetails = await Promise.all(assignedUsersResponse.data.map(async assignment => {
          const userResponse = await axios.get(`http://localhost:8080/user/${assignment.userID}`);
          return `${userResponse.data.firstName}`;
        }));
        return { ...task, assignedUsers: assignedUsersDetails.join(', ') };
      }));
      setTasks(tasksWithUsers);
    } catch (error) {
      console.error('Failed to fetch tasks data', error);
    }
  };

  return (
    <div className="App">
      <Router>
        <Navbar user={user} onLogout={handleLogout} onShowCalendar={() => handleCalendarTest(user.flatID)} />
        <RouterComponent 
          user={user}
          setUser={setUser}
          flat={flat}
          setFlat={setFlat}
          tasks={tasks}
          setTasks={setTasks}
          onShowCalendar={handleCalendarTest}
        />
      </Router>
    </div>
  );
}

function RouterComponent({ user, setUser, flat, setFlat, tasks, setTasks, onShowCalendar }) {
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

  const handleSignupSuccess = () => navigate('/');

  return (
    <Routes>
      <Route path="/" element={<Welcome onShowLogin={() => navigate('/login')} onShowSignup={() => navigate('/signup')} />} />
      <Route path="/login" element={<Login onLoginSuccess={handleLoginSuccess} />} />
      <Route path="/signup" element={<SignUp onSignUpSuccess={handleSignupSuccess} />} />
      <Route path="/loginSuccess" element={user && flat ? <LoginSuccess user={user} /> : <div>Loading...</div>} />
      <Route path="/createFlat" element={<CreateFlat onCreateSuccess={(flatData) => { setFlat(flatData); localStorage.setItem('flat', JSON.stringify(flatData)); navigate('/loginSuccess'); }} userData={user} />} />
      <Route path="/joinFlat" element={<JoinFlat createFlat={() => navigate('/createFlat')} joinFlat={(flatData) => { setFlat(flatData); localStorage.setItem('flat', JSON.stringify(flatData)); navigate('/loginSuccess'); }} userData={user} />} />
      <Route path="/calendar" element={flat ? <Calendar tasksData={tasks} /> : <div>Loading...</div>} />
      <Route path="/tasks" element={flat ? <Tasks flatData={flat} /> : <div>Loading...</div>} />
      <Route path="/payments" element={flat && user ? <Payments flatData={flat} user={user} /> : <div>Loading...</div>} />
      <Route path="/loginSuccess" element={user ? <LoginSuccess user={user} onCalendarTest={onShowCalendar} /> : <div>Loading...</div>} />
      <Route path="/createFlat" element={<CreateFlat onCreateSuccess={(flatData) => { setFlat(flatData); navigate('/loginSuccess'); }} userData={user} />} />
      <Route path="/joinFlat" element={<JoinFlat createFlat={() => navigate('/createFlat')} joinFlat={(flatData) => { setFlat(flatData); navigate('/loginSuccess'); }} userData={user} />} />
      <Route path="/calendar" element={<Calendar tasksData={tasks} />} />
      <Route path="/tasks" element={<Tasks flatData={flat} />} />
      <Route path="/payments" element={<Payments flatData={flat} user={user} />} />
      <Route path="/profile" element={user ? <Profile user={user} /> : <div>Loading...</div>} />
    </Routes>
  );
}

export default App;

