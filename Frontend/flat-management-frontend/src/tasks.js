import React, { useEffect,useState } from 'react';
import axios from 'axios';

const Tasks = ({flatData}) => {
    const [options, setOptions] = useState([]);
    const [selected, setSelected] = useState([]);
    const [people, setPeople] = useState([]);
    const [taskName, setTaskName] = useState('');
    const [description, setDescription] = useState('');
    const [requestedDate, setRequestedDate] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        const dataRes = async () => {
            const response = await axios.get('http://localhost:8080/flatUsers/'+flatData.flatID);
            console.log(response.data);
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

    function removePerson(event)
    {
        console.log("help?",options);
        const value = event.target.value;
        setOptions([...options,<option value={parseInt(value)}>{people[parseInt(value)].firstName + " " + people[parseInt(value)].lastName}</option>]);
        setSelected(
            selected.filter(a => a.props.value !== parseInt(value))
        );
    }


    function addPerson(event){
        const value = event.target.value;
        event.target.value = "nan";
        if (value == "nan")
        {
            return null;
        }
        setSelected([...selected,<button onClick = {removePerson} value = {parseInt(value)}>{"Remove " + people[parseInt(value)].firstName + " " + people[parseInt(value)].lastName}</button>]);
        const temp = options.filter(a => a.props.value !== parseInt(value));
        setOptions(
            options.filter(a => a.props.value !== parseInt(value))
        );
        console.log("test",options);
    }

    const handleCreateTask = async (e) => {
        e.preventDefault();
        try{
            const response = await axios.post('http://localhost:8080/tasks', {
                taskID:null,
                taskName,
                description,
                requestedDate:requestedDate+"T00:00",
                flatID:flatData.flatID,
                completed:false
            });
            if (response.status === 201) {
                for(let i=0; i < selected.length ; i++)
                {
                    const response2 = await axios.post('http://localhost:8080/assigned', {
                       taskID:response.data.taskID,
                       userID:people[selected[i].props.value].userID
                    });
                }
            }
        } catch (error) {
            console.log(error)
            setError('An error occurred while creating the flat');
        }
    };

    return (
        <div>
            <h1>Add a Task</h1>
            <form onSubmit = {handleCreateTask}>
                <label>Name: </label>
                <input type="text" value={taskName} onChange={e => setTaskName(e.target.value)} required />
                <label>Description: </label>
                <input type="text" value={description} onChange={e => setDescription(e.target.value)} required />
                <label>Date: </label>
                <input type="date" value={requestedDate} onChange={e => setRequestedDate(e.target.value)} required />
                <label>Assign task:</label>
                <select id="assign" onChange={addPerson}>
                  <option value="nan">Select Flat Mate</option>
                  {options}
                </select>
                <button type="submit">Create Task</button>
            </form>
            <h2>Flat Mate's assigned:</h2>
            {selected}
        </div>
    )
}

export default Tasks;