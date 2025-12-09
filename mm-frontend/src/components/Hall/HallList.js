import React, { useEffect, useState } from 'react';
import { fetchHalls, deleteHall } from '../../api/api';

const HallList = () => {
  const [halls, setHalls] = useState([]);

  const loadHalls = async () => {
    const data = await fetchHalls();
    setHalls(data);
  };

  useEffect(() => {
    loadHalls();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Delete this hall?')) {
      await deleteHall(id);
      loadHalls();
    }
  };

  return (
    <div className="hall-list">
      <h2>Halls</h2>
      <ul>
        {halls.map((hall) => (
          <li key={hall.id}>
            <strong>{hall.name}</strong> (Gallery ID: {hall.gallery?.id ?? 'N/A'})
            <button onClick={() => handleDelete(hall.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default HallList;
