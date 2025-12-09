import React, { useEffect, useState } from 'react';
import { deleteGallery, fetchGalleries } from '../api/api';

const GalleryList = () => {
  const [galleries, setGalleries] = useState([]);

  const loadGalleries = async () => {
    const data = await fetchGalleries();
    setGalleries(data);
  };

  useEffect(() => {
    loadGalleries();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Delete this gallery?')) {
      await deleteGallery(id);
      loadGalleries();
    }
  };

  return (
    <div className="gallery-list">
      <h2>Galleries</h2>
      <ul>
        {galleries.map((gallery) => (
          <li key={gallery.id}>
            <strong>{gallery.name}</strong> (Address ID: {gallery.address?.id ?? 'N/A'})
            <button onClick={() => handleDelete(gallery.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default GalleryList;
