import React from 'react';

const LoginSuccess = ({ user }) => {
  return (
    <div>
      <h1>Login Successful</h1>
      <p>Welcome back, {user.firstName} {user.lastName}!</p>
    </div>
  );
};

export default LoginSuccess;
