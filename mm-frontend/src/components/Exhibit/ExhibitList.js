import React, { useEffect, useState } from 'react';
import { getAllExhibits, deleteExhibit } from '../../api/api';

const ExhibitList = () => {
  const [exhibits, setExhibits] = useState([]);

  const fetchExhibits = async () => {
    try {
      const data = await getAllExhibits();
      setExhibits(data);
    } catch (err) {
      console.error(err);
      alert('Failed to fetch exhibits.');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this exhibit?')) return;
    try {
      await deleteExhibit(id);
      fetchExhibits();
    } catch (err) {
      console.error(err);
      alert('Failed to delete exhibit.');
    }
  };

  useEffect(() => {
    fetchExhibits();
  }, []);

  return (
    <div className="exhibit-list">
      <h2>Exhibits</h2>
      <ul>
        {exhibits.map(exhibit => (
          <li key={exhibit.id}>
            <strong>{exhibit.name}</strong> (Created: {exhibit.creationDate}, Ends: {exhibit.endDate})
            <button onClick={() => handleDelete(exhibit.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ExhibitList;
