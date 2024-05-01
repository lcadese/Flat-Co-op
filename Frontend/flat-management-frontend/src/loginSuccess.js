import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LoginSuccess = ({ user }) => {
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

  return (
    <div>
      <h1>Login Successful</h1>
      {userData ? (
        <>
          <h2>Welcome back, {userData.firstName} {userData.lastName}!</h2>
          <h2>You are in Flat: {userData.flatID}</h2>
        </>
      ) : (
        <p>Loading user data...</p>
      )}
      {error && <p>{error}</p>}
    </div>
  );
};

export default LoginSuccess;