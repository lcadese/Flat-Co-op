import React, { useState } from 'react';
import axios from 'axios';
import { v4 as uuidv4 } from 'uuid'; // Import the UUID generation function

const SignUp = ({ onSignUpSuccess }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');

  const handleSignUp = async (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    
    const userID = uuidv4();

    try {
      const response = await axios.post('http://localhost:8080/user/signup', {
        userID, 
        username,
        firstName,
        lastName,
        email,
        flatID: null,
        password
      });
      if (response.status === 201) {
        onSignUpSuccess();
      }
    } catch (error) {
      setError('Failed to create account');
    }
  };

  return (
    <div>
      <h2>Create Account</h2>
      <form onSubmit={handleSignUp}>
        <label>Username: </label>
        <input type="text" value={username} onChange={e => setUsername(e.target.value)} required />
        <br /><br />
        <label>Password: </label>
        <input type="password" value={password} onChange={e => setPassword(e.target.value)} required />
        <br /><br />
        <label>Confirm Password: </label>
        <input type="password" value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} required />
        <br /><br />
        <label>First Name: </label>
        <input type="text" value={firstName} onChange={e => setFirstName(e.target.value)} required />
        <br /><br />
        <label>Last Name: </label>
        <input type="text" value={lastName} onChange={e => setLastName(e.target.value)} required />
        <br /><br />
        <label>Email Address: </label>
        <input type="email" value={email} onChange={e => setEmail(e.target.value)} required />
        <br /><br />
        <button type="submit">Create Account</button>
        {error && <p>{error}</p>}
      </form>
    </div>
  );
};

export default SignUp;
