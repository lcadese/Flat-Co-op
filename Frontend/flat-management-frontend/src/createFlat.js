import React, { useState } from 'react';
import axios from 'axios';

const CreateFlat = ({ onCreateSuccess,userData }) => {
    const [address, setAddress] = useState('');
    const [error, setError] = useState('');
    const [flatID, setFlatID] = useState('');

    const handleCreateFlat = async (e) => {
        e.preventDefault();
        try {
            const userID = userData.userID;
            const response = await axios.post('http://localhost:8080/flat/create', {
                flatID,
                address,
                host:userID
            });
            if (response.status === 201) {

                await axios.put('http://localhost:8080/user/'+userID,{
                    userID,
                    flatID
                });
                // console.log("test")
                onCreateSuccess(response.data);
            }
        } catch (error) {
            console.log(error)
            setError('An error occurred while creating the flat');
        }
    };
    return (
        <div>
            <h2>Create Flat</h2>
            <form onSubmit={handleCreateFlat}>
                <div>
                    <label>Name/FlatID:</label>
                    <input type="text" value={flatID} onChange={e => setFlatID(e.target.value)} />
                </div>
                <div>
                    <label>Address:</label>
                    <input type="text" value={address} onChange={e => setAddress(e.target.value)} />
                </div>
                <button type="submit">Create Flat</button>
                {error && <p>{error}</p>}
            </form>
        </div>
    );
};

export default CreateFlat;