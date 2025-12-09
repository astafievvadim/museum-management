import React, { useState } from 'react';
import { createHall } from '../../api/api';

const HallForm = ({ onSuccess }) => {
  const [name, setName] = useState('');
  const [galleryId, setGalleryId] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!name || !galleryId) {
      alert('Please fill in all fields');
      return;
    }

    const newHall = { name, galleryId: Number(galleryId) };

    try {
      await createHall(newHall);
      if (onSuccess) onSuccess();
      setName('');
      setGalleryId('');
      alert('Hall created successfully!');
    } catch (err) {
      console.error(err);
      alert('Failed to create hall.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="hall-form">
      <input
        type="text"
        placeholder="Hall Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <input
        type="number"
        placeholder="Gallery ID"
        value={galleryId}
        onChange={(e) => setGalleryId(e.target.value)}
      />
      <button type="submit">Add Hall</button>
    </form>
  );
};

export default HallForm;
