import React from 'react';

const Welcome = ({ onShowLogin, onShowSignup }) => {
  return (
    <div>
      <h1>Welcome to the Flat Management System</h1>
      <button onClick={onShowLogin}>Login</button>
      <button onClick={onShowSignup}>Create Account</button>
    </div>
  );
};

export default Welcome;
