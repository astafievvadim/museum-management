import React, { useEffect, useState } from 'react';
import { getAllCustomers, deleteCustomer } from '../../api/api';

const CustomerList = () => {
  const [customers, setCustomers] = useState([]);

  const fetchCustomers = async () => {
    try {
      const data = await getAllCustomers();
      setCustomers(data);
    } catch (err) {
      console.error(err);
      alert('Failed to fetch customers.');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this customer?')) return;
    try {
      await deleteCustomer(id);
      fetchCustomers();
    } catch (err) {
      console.error(err);
      alert('Failed to delete customer.');
    }
  };

  useEffect(() => {
    fetchCustomers();
  }, []);

  return (
    <div className="customer-list">
      <h2>Customers</h2>
      <ul>
        {customers.map(c => (
          <li key={c.id}>
            {c.firstName} {c.lastName} ({c.email})
            <button onClick={() => handleDelete(c.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default CustomerList;
