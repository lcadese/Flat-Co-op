import React from 'react';

const Profile = ({ user }) => {
  return (
    <div className="profile-container">
      <h1 className="profile-heading">Profile</h1>
      <div className="profile-details">
      <p><strong>Username:</strong> {user.username}</p>
      <p><strong>First Name:</strong> {user.firstName}</p>
      <p><strong>Last Name:</strong> {user.lastName}</p>
      <p><strong>Email:</strong> {user.email}</p>
      <p><strong>Flat ID:</strong> {user.flatID}</p>
    </div>
    </div>
  );
};

export default Profile;
