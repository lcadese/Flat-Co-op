import React, { useEffect, useState } from 'react';
import axios from 'axios';

const LoginSuccess = ({ user }) => {
  const [userData, setUserData] = useState(null);
  const [error, setError] = useState('');
  const [tasks, setTasks] = useState([]);
  const [tasksDisplay, setTasksDisplay] = useState([]);
  const [payments, setPayments] = useState([]);

  useEffect(() => {
    if (user && user.userID) {
      const fetchUserData = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/user/${user.userID}`);
          setUserData(response.data);

         const response2 = await axios.get(`http://localhost:8080/tasks/userID/${user.userID}`);
         const temp = [];
         setTasks(response2);
         for(let i=0; i < response2.data.length ; i++)
         {
          temp.push(<div>
              <h2>{response2.data[i].taskName}: </h2>
              <h3>{response2.data[i].description}</h3>
              <h3>by:{response2.data[i].requestedDate.substring(0,10)}</h3>
           </div>);
          }
          setTasksDisplay(temp);
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
      <h1 id="pad">Current tasks:</h1>
      {tasksDisplay}
      <h1 id="pad">Payments:</h1>
      <div>
        {payments.map(payment => (
          <div key={payment.paymentID} id="pay">
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
