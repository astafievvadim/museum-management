import React, { useState, useEffect } from 'react';
import { getExhibitById, createExhibit, updateExhibit } from '../../api/api';

const ExhibitForm = ({ existingExhibitId, onSuccess }) => {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    creationDate: '',
    endDate: '',
    hallId: '',
    itemIds: '',
  });

  useEffect(() => {
    if (existingExhibitId) {
      getExhibitById(existingExhibitId).then(exhibit => {
        setFormData({
          ...exhibit,
          creationDate: exhibit.creationDate ? exhibit.creationDate.substring(0, 10) : '',
          endDate: exhibit.endDate ? exhibit.endDate.substring(0, 10) : '',
          itemIds: exhibit.itemIds ? exhibit.itemIds.join(',') : '',
        });
      });
    }
  }, [existingExhibitId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const preparedData = {
      ...formData,
      itemIds: formData.itemIds.split(',').map(id => parseInt(id.trim())),
    };
    try {
      if (existingExhibitId) {
        await updateExhibit(existingExhibitId, preparedData);
      } else {
        await createExhibit(preparedData);
      }
      if (onSuccess) onSuccess();
    } catch (err) {
      console.error(err);
      alert('Failed to save exhibit.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="exhibit-form">
      <input name="name" value={formData.name} onChange={handleChange} placeholder="Exhibit Name" required />
      <textarea name="description" value={formData.description} onChange={handleChange} placeholder="Description" />
      <input type="date" name="creationDate" value={formData.creationDate} onChange={handleChange} />
      <input type="date" name="endDate" value={formData.endDate} onChange={handleChange} />
      <input type="number" name="hallId" value={formData.hallId} onChange={handleChange} placeholder="Hall ID" required />
      <input name="itemIds" value={formData.itemIds} onChange={handleChange} placeholder="Comma-separated Item IDs" />
      <button type="submit">{existingExhibitId ? 'Update' : 'Create'} Exhibit</button>
    </form>
  );
};

export default ExhibitForm;
