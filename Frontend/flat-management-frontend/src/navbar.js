// navbar.js
import React from 'react';
import { NavLink, useNavigate } from 'react-router-dom';

function Navbar({ user }) {
  const navigate = useNavigate();

  const handleLogoClick = (e) => {
    e.preventDefault();
    if (user) {
      navigate('/loginSuccess');
    } else {
      navigate('/');
    }
  };

  return (
    <nav className="nav">
      <NavLink 
        to="/" 
        className="site-title" 
        onClick={handleLogoClick}
        style={{ textDecoration: 'none', color: 'inherit' }}
      >
        Flat Hub
      </NavLink>
      <ul>
        {user ? (
          <>
            <li>
              <NavLink 
                to="/tasks" 
                className={({ isActive }) => (isActive ? 'active' : '')}
              >
                Tasks
              </NavLink>
            </li>
            <li>
              <NavLink 
                to="/payments" 
                className={({ isActive }) => (isActive ? 'active' : '')}
              >
                Payments
              </NavLink>
            </li>
            <li>
              <NavLink 
                to="/profile" 
                className={({ isActive }) => (isActive ? 'active' : '')}
              >
                Profile
              </NavLink>
            </li>
          </>
        ) : (
          <>
            <li>
              <NavLink 
                to="/login" 
                className={({ isActive }) => (isActive ? 'active' : '')}
              >
                Login
              </NavLink>
            </li>
            <li>
              <NavLink 
                to="/signup" 
                className={({ isActive }) => (isActive ? 'active' : '')}
              >
                Create Account
              </NavLink>
            </li>
          </>
        )}
      </ul>
    </nav>
  );
}

export default Navbar;
