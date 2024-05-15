import { NavLink } from 'react-router-dom';

function Navbar({ user }) {
  return (
    <nav className="nav">
      <NavLink to="/" className="site-title">Flat Hub</NavLink>
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
