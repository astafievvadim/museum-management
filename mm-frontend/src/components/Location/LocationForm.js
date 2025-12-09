import React, { useState } from 'react';
import { createLocation } from '../api/api'; // You need to implement this API call

const LocationForm = ({ onSuccess }) => {
  const [eventId, setEventId] = useState('');
  const [itemId, setItemId] = useState('');
  const [hallId, setHallId] = useState('');
  const [placeDate, setPlaceDate] = useState('');
  const [removeDate, setRemoveDate] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!eventId || !itemId || !hallId || !placeDate) {
      alert('Please fill in all required fields (event, item, hall, place date)');
      return;
    }

    const newLocation = {
      eventId: Number(eventId),
      itemId: Number(itemId),
      hallId: Number(hallId),
      placeDate,
      removeDate: removeDate || null,
    };

    try {
      await createLocation(newLocation);
      if (onSuccess) onSuccess();
      setEventId('');
      setItemId('');
      setHallId('');
      setPlaceDate('');
      setRemoveDate('');
      alert('Location created successfully!');
    } catch (err) {
      console.error(err);
      alert('Failed to create location.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="location-form">
      <input
        type="number"
        placeholder="Event ID"
        value={eventId}
        onChange={(e) => setEventId(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="Item ID"
        value={itemId}
        onChange={(e) => setItemId(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="Hall ID"
        value={hallId}
        onChange={(e) => setHallId(e.target.value)}
        required
      />
      <input
        type="datetime-local"
        placeholder="Place Date"
        value={placeDate}
        onChange={(e) => setPlaceDate(e.target.value)}
        required
      />
      <input
        type="datetime-local"
        placeholder="Remove Date (optional)"
        value={removeDate}
        onChange={(e) => setRemoveDate(e.target.value)}
      />
      <button type="submit">Add Location</button>
    </form>
  );
};

export default LocationForm;
