// calendar.js

import React from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import { Tooltip } from 'react-tooltip';

function test(tasks) {
  const events = tasks
    .filter(task => !task.completed) // Filter out completed tasks
    .map(task => ({
      title: `${task.taskName} - ${task.assignedUsers}`,
      date: task.requestedDate.substring(0, 10),
      extendedProps: {
        description: task.taskName,
        assignedUsers: task.assignedUsers
      },
      id: task.taskID
    }));
  return events;
}

const Calendar = ({ tasksData }) => {
  const eventContent = (arg) => {
    return (
      <>
        <div data-tooltip-id={`tooltip-${arg.event.id}`} data-tooltip-content={`${arg.event.extendedProps.description} - ${arg.event.extendedProps.assignedUsers}`}>
          <div style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
            {arg.event.title}
          </div>
        </div>
        <Tooltip id={`tooltip-${arg.event.id}`} place="top" effect="solid" />
      </>
    );
  };

  return (
    <FullCalendar
      plugins={[dayGridPlugin]}
      initialView="dayGridMonth"
      events={test(tasksData)}
      eventContent={eventContent}
    />
  );
};

export default Calendar;
