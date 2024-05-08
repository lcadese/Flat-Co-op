import { NavLink } from 'react-router-dom';

export default function Navbar() {
    return <nav className="nav">
        <a href="/" className="site-title">Flat Hub</a>
        <ul>
            <li>
                {/* <a href="/tasks">Tasks</a> */}
                <NavLink to="/tasks" activeClassName="active">Tasks</NavLink>
            </li>
            <li>
            <NavLink to="/payments" activeClassName="active">Payments</NavLink>
            </li>
        </ul>
    </nav>
}