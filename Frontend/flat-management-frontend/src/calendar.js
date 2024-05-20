// calendar.js
import React from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';

function test(tasks) {
  const events = tasks.map(task => ({
    title: `${task.taskName} - ${task.assignedUsers}`,
    date: task.requestedDate
  }));
  return events;
}

const Calendar = ({ tasksData }) => {
  return (
    <FullCalendar
      plugins={[dayGridPlugin]}
      initialView="dayGridMonth"
      events={test(tasksData)}
    />
  );
};

export default Calendar;
