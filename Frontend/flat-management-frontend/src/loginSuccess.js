// loginSuccess.js

import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LoginSuccess = ({ user, onCalendarTest }) => {
  const [userData, setUserData] = useState(null);
  const [error, setError] = useState('');
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    if (user && user.userID) {
      const fetchUserData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/user/${user.userID}`);
          setUserData(response.data);
          
          const response2 = await axios.get(`http://localhost:8080/tasks/userID/${user.userID}`);
          setTasks(response2.data.filter(task => !task.completed));
        } catch (error) {
          setError('Failed to fetch user data ' + error);
        }
      };

      fetchUserData();
    }
  }, [user]);

  const handleTaskComplete = async (task) => {
    const updatedTask = { ...task, completed: true };

    try {
      await axios.put(`http://localhost:8080/tasks/${task.taskID}`, updatedTask);
      setTasks(tasks.filter(t => t.taskID !== task.taskID));
    } catch (error) {
      console.error('Error updating task status', error);
      setError('An error occurred while updating the task status');
    }
  };

  return (
    <div>
      <h1>Login Successful</h1>
      {userData ? (
        <>
          <h2>Welcome back, {userData.firstName} {userData.lastName}!</h2>
          <h2>Use the following code to invite someone else to the flat:</h2>
          <h2>{userData.flatID}</h2>
        </>
      ) : (
        <p>Loading user data...</p>
      )}
      {error && <p>{error}</p>}
      <h1>Current tasks:</h1>
      {tasks.map(task => (
        <div key={task.taskID}>
          <h2>{task.taskName}:<input 
            type="checkbox" 
            onChange={() => handleTaskComplete(task)}
          /></h2>
          <h3>{task.description}</h3>
          <h3>by: {task.requestedDate.substring(0, 10)}</h3>
          
        </div>
      ))}
    </div>
  );
};

export default LoginSuccess;
