import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LoginSuccess = ({ user, onCalendarTest }) => {
  const [userData, setUserData] = useState(null);
  const [error, setError] = useState('');
  const [tasksDisplay, setTasksDisplay] = useState([]);
  const [payments, setPayments] = useState([]);

  useEffect(() => {
    if (user && user.userID) {
      const fetchUserData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/user/${user.userID}`);
          setUserData(response.data);
        } catch (error) {
          setError('Failed to fetch user data ' + error);
        }
      };
      const fetchPaymentData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/payments/userID/`+user.userID);
          const updatedPayments = [];
          for (const payment of response.data) {
            const userDetail = await fetchUserDetails(payment.userID);
            if (userDetail) {
              payment.userDetails = userDetail;
              updatedPayments.push(payment);
            }
          }
          setPayments(updatedPayments);
        } catch (error) {
          console.log(error);
          setError('Failed to fetch payment data');
        }
      };

      fetchUserData();
      fetchPaymentData();
    }
  }, [user]);

  const fetchUserDetails = async (userID) => {
    try {
      const response = await axios.get(`http://localhost:8080/user/${userID}`);
      return response.data;
    } catch (error) {
      console.error("Error fetching user details:", error);
      return null;
    }
  };

  const handleCheckboxChange = async (paymentID, currentStatus) => {
    try {
      await axios.put(`http://localhost:8080/payments/${paymentID}`, !currentStatus, {
        headers: {
          'Content-Type': 'application/json'
        }
      });
      setPayments(payments.map(payment =>
        payment.paymentID === paymentID ? { ...payment, payed: !currentStatus } : payment
      ));
    } catch (error) {
      console.error('Error updating payment status', error);
      setError('An error occurred while updating the payment status');
    }
  };

  const handleCalendar = async (e) => {
    e.preventDefault();
    if (userData && userData.flatID) {
      try {
        const response = await axios.get(`http://localhost:8080/tasks/flatID/${userData.flatID}`);
        onCalendarTest(response.data);
      } catch (error) {
        setError('Failed to fetch tasks data');
      }
    } else {
      setError('User data not loaded');
    }
  };

  return (
    <div>
      <h1>Login Successful</h1>
      {userData ? (
        <>
          <h2>Welcome back, {userData.firstName} {userData.lastName}!</h2>
          <h2>Use the following code to invite someone else to the flat:</h2>
          <h2>{userData.flatID}</h2>
        </>
      ) : (
        <p>Loading user data...</p>
      )}
      {error && <p>{error}</p>}
      <button onClick={handleCalendar}>Show Calendar</button>
      <h1>Current tasks:</h1>
      {tasksDisplay}
      <h1>Payments:</h1>
      <div>
        {payments.map(payment => (
          <div key={payment.paymentID}>
            <span>User Name: {payment.userDetails?.firstName} {payment.userDetails?.lastName}, </span>
            <span>Amount: ${payment.amount} </span>
            <input
              type="checkbox"
              checked={payment.payed}
              onChange={() => handleCheckboxChange(payment.paymentID, payment.payed)}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default LoginSuccess;
