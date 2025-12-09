import React, { useState } from 'react';
import { createGallery } from '../api/api';

const GalleryForm = ({ onSuccess }) => {
  const [name, setName] = useState('');
  const [addressId, setAddressId] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!name || !addressId) {
      alert('Please fill in all fields.');
      return;
    }

    const newGallery = { name, addressId: Number(addressId) };

    try {
      await createGallery(newGallery);
      if (onSuccess) onSuccess();
      setName('');
      setAddressId('');
      alert('Gallery created successfully!');
    } catch (err) {
      console.error(err);
      alert('Failed to create gallery.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="gallery-form">
      <input
        type="text"
        placeholder="Gallery Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />
      <input
        type="number"
        placeholder="Address ID"
        value={addressId}
        onChange={(e) => setAddressId(e.target.value)}
      />
      <button type="submit">Add Gallery</button>
    </form>
  );
};

export default GalleryForm;
