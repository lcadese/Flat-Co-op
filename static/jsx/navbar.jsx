// Navbar.js

import React from 'react';

console.log("Navbar component is being executed");

function Navbar() {
  return (
    <nav>
      <ul>
        <li><a href="index.html">My Flat</a></li>
        <li><a href="tasks.html">Tasks</a></li>
        <li><a href="payments.html">Payments</a></li>
        <li><a href="account.html">Account</a></li>
      </ul>
    </nav>
  );
}

export default Navbar;
