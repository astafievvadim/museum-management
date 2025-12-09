import React, { useEffect, useState } from 'react';
import { getTicketTypes, deleteTicketType } from '../../api/api';
import TicketTypeForm from './TicketTypeForm';

const TicketTypeList = () => {
  const [ticketTypes, setTicketTypes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editingType, setEditingType] = useState(null);
  const [showForm, setShowForm] = useState(false);

  const fetchTicketTypes = async () => {
    setLoading(true);
    try {
      const data = await getTicketTypes();
      setTicketTypes(data);
    } catch (error) {
      console.error('Failed to fetch ticket types', error);
    }
    setLoading(false);
  };

  useEffect(() => {
    fetchTicketTypes();
  }, []);

  const handleDelete = async (id) => {
    try {
      await deleteTicketType(id);
      fetchTicketTypes();
    } catch (error) {
      console.error('Failed to delete ticket type', error);
    }
  };

  const handleAddClick = () => {
    setEditingType(null);
    setShowForm(true);
  };

  const handleEditClick = (type) => {
    setEditingType(type);
    setShowForm(true);
  };

  const handleFormSuccess = () => {
    setShowForm(false);
    fetchTicketTypes();
  };

  const handleFormCancel = () => {
    setShowForm(false);
    setEditingType(null);
  };

  if (loading) return <p>Loading ticket types...</p>;

  return (
    <div>
      <h2>Ticket Types</h2>

      {!showForm && (
        <>
          <button onClick={handleAddClick}>Add Ticket Type</button>
          <table border="1" cellPadding="5" cellSpacing="0">
            <thead>
              <tr>
                <th>Name</th>
                <th>Price</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {ticketTypes.map((tt) => (
                <tr key={tt.id}>
                  <td>{tt.name}</td>
                  <td>{tt.price}</td>
                  <td>
                    <button onClick={() => handleEditClick(tt)}>Edit</button>
                    <button onClick={() => handleDelete(tt.id)}>Delete</button>
                  </td>
                </tr>
              ))}
              {ticketTypes.length === 0 && (
                <tr>
                  <td colSpan="3">No ticket types found.</td>
                </tr>
              )}
            </tbody>
          </table>
        </>
      )}

      {showForm && (
        <TicketTypeForm
          existingType={editingType}
          onSuccess={handleFormSuccess}
          onCancel={handleFormCancel}
        />
      )}
    </div>
  );
};

export default TicketTypeList;
