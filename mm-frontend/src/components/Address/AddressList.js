import React, { useState, useEffect } from 'react';
import { getAddresses, deleteAddress } from '../../api/api.js';
import { Link } from 'react-router-dom';
import '../tab.css';

function AddressList() {
  const [addresses, setAddresses] = useState([]);

  const loadAddresses = async () => {
    const data = await getAddresses();
    setAddresses(data);
  };

  useEffect(() => {
    loadAddresses();
  }, []);

  const handleDelete = async (id) => {
    await deleteAddress(id);
    loadAddresses();
  };

  return (
    <div>
      <h2 className="text-xl font-bold mb-4">Addresses</h2>
      <Link to="/dashboard/addresses/new" className="bg-green-500 text-white px-3 py-1 rounded mb-4 inline-block">+ New Address</Link>
      <ul>
        {addresses.map(addr => (
          <li key={addr.id} className="border p-2 mb-2 flex justify-between items-center">
            <span>{addr.street}, {addr.postalCode}</span>
            <div>
              <Link to={`/addresses/${addr.id}/edit`} className="mr-2 text-blue-600">Edit</Link>
              <button onClick={() => handleDelete(addr.id)} className="text-red-600">Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default AddressList;
