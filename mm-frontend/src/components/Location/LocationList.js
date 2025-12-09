import React, { useEffect, useState } from 'react';
import { fetchLocations, deleteLocation } from '../api/api'; // Implement these

const LocationList = () => {
  const [locations, setLocations] = useState([]);

  const loadLocations = async () => {
    const data = await fetchLocations();
    setLocations(data);
  };

  useEffect(() => {
    loadLocations();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Delete this location?')) {
      await deleteLocation(id);
      loadLocations();
    }
  };

  return (
    <div className="location-list">
      <h2>Locations</h2>
      <ul>
        {locations.map((loc) => (
          <li key={loc.id}>
            Event ID: <strong>{loc.eventId}</strong>, Item ID: <strong>{loc.itemId}</strong>, Hall ID: <strong>{loc.hallId}</strong>
            <br />
            Placed: {new Date(loc.placeDate).toLocaleString()} &nbsp;
            Removed: {loc.removeDate ? new Date(loc.removeDate).toLocaleString() : 'N/A'}
            <button onClick={() => handleDelete(loc.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default LocationList;
