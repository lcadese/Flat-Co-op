import React from 'react';

const LoginSuccess = ({ user }) => {
  return (
    <div>
      <h1>Login Successful</h1>
      <h2>Welcome back, {user.firstName} {user.lastName}!</h2>
      <h2>You are in Flat: {user.flatID} </h2>
    </div>
  );
};

export default LoginSuccess;
