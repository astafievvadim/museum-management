import React, { useState, useEffect } from 'react';
import { createTicketType, updateTicketType } from '../../api/api';

const TicketTypeForm = ({ existingType, onSuccess, onCancel }) => {
  const [name, setName] = useState('');
  const [price, setPrice] = useState('');

  useEffect(() => {
    if (existingType) {
      setName(existingType.name);
      setPrice(existingType.price);
    }
  }, [existingType]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const ticketType = { name, price };
      if (existingType) {
        await updateTicketType(existingType.id, ticketType);
      } else {
        await createTicketType(ticketType);
      }
      onSuccess();
    } catch (error) {
      console.error('Failed to save ticket type', error);
    }
  };

  return (
    <div>
      <h3>{existingType ? 'Edit' : 'Add'} Ticket Type</h3>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Name: </label>
          <input value={name} onChange={(e) => setName(e.target.value)} required />
        </div>
        <div>
          <label>Price: </label>
          <input
            type="number"
            step="0.01"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            required
          />
        </div>
        <button type="submit">{existingType ? 'Update' : 'Create'}</button>
        <button type="button" onClick={onCancel}>Cancel</button>
      </form>
    </div>
  );
};

export default TicketTypeForm;
