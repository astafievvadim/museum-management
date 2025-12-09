import React, { useState, useEffect } from 'react';
import { getEventById, createEvent, updateEvent } from '../../api/api';

const EventForm = ({ existingEventId, onSuccess }) => {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    startDate: '',
    endDate: '',
    capacity: '',
    hallId: '',
  });

  useEffect(() => {
    if (existingEventId) {
      getEventById(existingEventId).then(event => {
        setFormData({
          ...event,
          startDate: event.startDate ? event.startDate.substring(0, 16) : '',
          endDate: event.endDate ? event.endDate.substring(0, 16) : '',
        });
      });
    }
  }, [existingEventId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (existingEventId) {
        await updateEvent(existingEventId, formData);
      } else {
        await createEvent(formData);
      }
      if (onSuccess) onSuccess();
    } catch (err) {
      console.error(err);
      alert('Failed to save event.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="event-form">
      <input name="name" value={formData.name} onChange={handleChange} placeholder="Event Name" required />
      <textarea name="description" value={formData.description} onChange={handleChange} placeholder="Description" />
      <input type="datetime-local" name="startDate" value={formData.startDate} onChange={handleChange} required />
      <input type="datetime-local" name="endDate" value={formData.endDate} onChange={handleChange} required />
      <input type="number" name="capacity" value={formData.capacity} onChange={handleChange} placeholder="Capacity" required />
      <input type="number" name="hallId" value={formData.hallId} onChange={handleChange} placeholder="Hall ID" required />
      <button type="submit">{existingEventId ? 'Update' : 'Create'} Event</button>
    </form>
  );
};

export default EventForm;
