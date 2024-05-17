import React, { useEffect,useState } from 'react';
import axios from 'axios'
// export default function Payments(){
//     return <h1>Add a Payment</h1>
// }

const Payment = ({flatData}) => {
    const [options, setOptions] = useState([]);
    const [people, setPeople] = useState([]);
    const [selected, setSelected] = useState([]);
    const [amount, setAmount] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        const dataRes = async () => {
            const response = await axios.get('http://localhost:8080/flatUsers/'+flatData.flatID);
            // console.log(response.data);
            setPeople(response.data);
            const temp = [];
            for(let i=0; i < response.data.length ; i++)
            {
                temp.push(<option value={i}>{response.data[i].firstName + " " + response.data[i].lastName}</option>)
            }
            setOptions(temp);
            setSelected([]);
        }
        dataRes();
    }, []);

    function removePerson(index) {
        const person = selected[index];
        const personToAddBack = (
            <option key={person.userID} value={people.findIndex(p => p.userID === person.userID)}>
                {person.firstName + ' ' + person.lastName}
            </option>
        );

        setOptions(prevOptions => [...prevOptions, personToAddBack]);
        setSelected(selected.filter((_, i) => i !== index));
    }

    

    function addPerson(event) {
        const value = parseInt(event.target.value);
        event.target.value = 'nan';
        if (isNaN(value)) {
            return;
        }

        const person = people[value];
        setSelected([...selected, person]);
        setOptions(options.filter(a => a.props.value !== value));
    }
    
    const handleCreatePayment = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.get('http://localhost:8080/payments');
            console.log(response.data)
        } catch (error) {
            console.log(error)
        }

        try {
            for (const person of selected) {
                const response = await axios.post('http://localhost:8080/payments', {
                    paymentID: null,
                    userID: person.userID,
                    amount,
                    payed: false
                });
                if (response.status === 201) {
                    console.log(response.data);
                }
            }
        } catch (error) {
            console.log(error);
            setError('An error occurred while posting the payment');
        }
    };

    return (
        <div>
            <h1>Add a Payment</h1>
            <form onSubmit={handleCreatePayment}>
                <label>Amount: </label>
                <input type="number" value={amount} onChange={e => setAmount(e.target.value)} required />
                <label>Assign user:</label>
                <select id="assign" onChange={addPerson}>
                    <option value="nan">Select Flat Mate</option>
                    {options}
                </select>
                <button type="submit">Create Payment</button>
            </form>
            <h2>Flat Mate's assigned:</h2>
            <div>
                {selected.map((person, index) => (
                    <div key={person.userID}>
                        {person.firstName + ' ' + person.lastName}
                        <button onClick={() => removePerson(index)}>Remove</button>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Payment;