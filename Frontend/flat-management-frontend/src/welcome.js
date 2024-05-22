import React from 'react';

const Welcome = ({ onShowLogin, onShowSignup }) => {
    return (
        <div className="welcome-container" style={{ textAlign: 'center' }}>
            <img src="flathub-logo-zip-file/png/logo-no-background.png" className="logo-img" alt="Flat Management System Logo"></img>
            <h1>Welcome to the Flat Management System</h1>
            <h2>A shared app to organise students’ lives</h2>
            </div>
        
    );
};

export default Welcome;
