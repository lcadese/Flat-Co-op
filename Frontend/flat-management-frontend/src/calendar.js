import React, { useEffect, useState } from 'react';
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid' // a plugin!
import axios from 'axios';

function test(tasks)
{
    console.log(tasks);
    const events = [];
    for (let i = 0; i < tasks.length; i++) {
        events.push({
            title: tasks[i].taskName,
            date: tasks[i].requestedDate
        })
    }
    return events;
}

const Calendar = ({tasksData}) => {

  return (
    <FullCalendar
      plugins={[ dayGridPlugin ]}
      initialView="dayGridMonth"
      events = {test(tasksData)}
    />
  )
};

export default Calendar;