import React from 'react';

const Welcome = ({ onShowLogin, onShowSignup }) => {
    return (
        <div className="welcome-container" style={{ textAlign: 'center' }}>
            <img src="flathub-logo-zip-file/png/logo-no-background.png" className="logo-img" alt="Flat Management System Logo"></img>
            <h1>Welcome to the Flat Management System</h1>
            <div className="button-container">
                <button onClick={onShowLogin}>Login</button>
                <button onClick={onShowSignup}>Create Account</button>
            </div>
        </div>
    );
};

export default Welcome;
