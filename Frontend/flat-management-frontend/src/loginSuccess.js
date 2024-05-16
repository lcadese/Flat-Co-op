import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LoginSuccess = ({ user, onCalendarTest }) => {
  const [userData, setUserData] = useState(null);
  const [error, setError] = useState('');
  const [tasks, setTasks] = useState([]);
  const [tasksDisplay, setTasksDisplay] = useState([]);

  useEffect(() => {
    if (user && user.userID) {
      const fetchUserData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/user/${user.userID}`);
          setUserData(response.data);

          const response2 = await axios.get(`http://localhost:8080/assigned/user/${user.userID}`);

          const temp = [];
          const temp2 = [];
          for(let i=0; i < response2.data.length ; i++)
          {
            const response3 = await axios.get(`http://localhost:8080/tasks/${response2.data[i].taskID}`);
            console.log(response3.data);
            temp.push(<div>
                <h2>{response3.data.taskName}: </h2>
                <h3>{response3.data.description}</h3>
                <h3>by:{response3.data.requestedDate.substring(0,10)}</h3>
            </div>);
            temp2.push(response3.data);
          }
          setTasksDisplay(temp);
          setTasks(temp2);
        } catch (error) {
          setError('Failed to fetch user data '+error);
        }
      };

      fetchUserData();
    }
  }, [user]);

  const handleCalendar = async (e) => {
    e.preventDefault();
    if (userData && userData.flatID) {
      try {
        const response = await axios.get(`http://localhost:8080/tasks/flatID/${userData.flatID}`);
        onCalendarTest(response.data);
      } catch (error) {
        setError('Failed to fetch tasks data');
      }
    } else {
      setError('User data not loaded');
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
      <button onClick={handleCalendar}>Show Calendar</button>
      <h1>Current tasks:</h1>
      {tasksDisplay}
    </div>
  );
};

export default LoginSuccess;
