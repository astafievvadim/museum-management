import React, { useEffect, useState } from 'react';
import { getAllEvents, deleteEvent } from '../../api/api';

const EventList = () => {
  const [events, setEvents] = useState([]);

  const fetchEvents = async () => {
    try {
      const data = await getAllEvents();
      setEvents(data);
    } catch (err) {
      console.error(err);
      alert('Failed to fetch events.');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this event?')) return;
    try {
      await deleteEvent(id);
      fetchEvents();
    } catch (err) {
      console.error(err);
      alert('Failed to delete event.');
    }
  };

  useEffect(() => {
    fetchEvents();
  }, []);

  return (
    <div className="event-list">
      <h2>Events</h2>
      <ul>
        {events.map(event => (
          <li key={event.id}>
            <strong>{event.name}</strong> ({event.startDate?.substring(0, 16)} - {event.endDate?.substring(0, 16)})
            <button onClick={() => handleDelete(event.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EventList;
