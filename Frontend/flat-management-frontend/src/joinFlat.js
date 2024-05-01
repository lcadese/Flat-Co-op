import React, { useState } from 'react';
import axios from 'axios';

const JoinFlat = ({createFlat,joinFlat}) => {
  const [flatID,setFlatID] = useState('');
  const [error, setError] = useState('');

  const handleJoin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get('http://localhost:8080/flat/'+flatID);
      if (response.data) {
        joinFlat(response.data);
      }
    } catch (error) {
      setError('Invalid Flat ID');
    }
  };


    return (
        <div>
            <form onSubmit={handleJoin}>
                <label>Flat Code:
                </label><input required type="text" value={flatID} onChange={e => setFlatID(e.target.value)}/>
                <button type="submit">Join Flat</button>
                {error && <p>{error}</p>}
            </form>
            <button onClick={createFlat}>Create Flat</button>
        </div>
    );
};
export default JoinFlat;