import React, { useState } from 'react';
import axios from 'axios';

const CreateFlat = ({ onCreateSuccess }) => {
    const [flatID, setFlatID] = useState('');
    const [address, setAddress] = useState('');
    const [host, setHost] = useState('');
    const [name, setName] = useState('');
    const [error, setError] = useState('');

    const handleCreateFlat = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/flat/create', {
                flatID,
                address,
                name,
                host
            });
            if (response.status === 201) {
                // console.log("test")
                onCreateSuccess();
            }
        } catch (error) {
            console.log(error)
            setError('An error occurred while creating the flat');
        }
    };;
    return (
        <div>
            <h2>Create Flat</h2>
            <form onSubmit={handleCreateFlat}>
                <div>
                    <label>Flat ID:</label>
                    <input type="text" value={flatID} onChange={e => setFlatID(e.target.value)} />
                </div>
                <div>
                    <label>Address:</label>
                    <input type="text" value={address} onChange={e => setAddress(e.target.value)} />
                </div>
                <div>
                    <label>Name:</label>
                    <input type="text" value={name} onChange={e => setName(e.target.value)} />
                </div>
                <div>
                    <label>Host:</label>
                    <input type="text" value={host} onChange={e => setHost(e.target.value)} />
                </div>
                <button type="submit">Create Flat</button>
                {error && <p>{error}</p>}
            </form>
        </div>
    );
};

export default CreateFlat;