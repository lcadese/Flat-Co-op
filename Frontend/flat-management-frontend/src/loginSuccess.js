import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LoginSuccess = ({ user ,onCalendarTest}) => {
  const [userData, setUserData] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/user/${user.userID}`);
        setUserData(response.data);
      } catch (error) {
        setError('Failed to fetch user data');
      }
    };

    fetchUserData();
  }, [user.userID]); // Depend on user.userID to refetch if it changes

    const handleCalendar = async (e) => {
      e.preventDefault();
      const response = await axios.get('http://localhost:8080/tasks/flatID/'+userData.flatID);
      onCalendarTest(response.data)
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
    </div>
  );
};

export default LoginSuccess;