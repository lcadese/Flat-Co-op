export default function Tasks(){
    function test(event){
        console.log(event.target.value)
    }


    return (
        <div>
            <h1>Add a Task</h1>
            <form>
                <label>Name: </label>
                <input type="text" required />
                <label>Description: </label>
                <input type="text" required />
                <label>Date: </label>
                <input type="date" required />
                <button type="submit">Create Task</button>
                <label for="cars">Add People:</label>
                <select id="people" onChange={test}>
                  <option value="1">Example1</option>
                  <option value="2">Example2</option>
                  <option value="3">Example3</option>
                  <option value="4">Example4</option>
                </select>
            </form>
        </div>
    )
}