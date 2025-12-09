// CountryList.jsx
import React, { useState, useEffect } from 'react';
import { getCountries, deleteCountry } from '../../api/api';
import CountryForm from './CountryForm';

const CountryList = () => {
  const [countries, setCountries] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [deletingId, setDeletingId] = useState(null);

  const loadCountries = () => {
    setLoading(true);
    getCountries()
      .then(data => {
        console.log('Fetched countries:', data); // <-- Add this log
        if (Array.isArray(data)) {
          setCountries(data);
        } else {
          console.warn('Expected array but got:', data);
          setCountries([]);
        }
      })
      .catch(err => {
        console.error('Error loading countries:', err);
        setCountries([]);
      })
      .finally(() => setLoading(false));
  };


  useEffect(() => {
    loadCountries();
  }, []);

  const handleDelete = (id) => {
      setDeletingId(id);
      deleteCountry(id)
        .then(() => loadCountries())
        .catch(err => console.error('Delete failed', err))
        .finally(() => setDeletingId(null));
    
  };

  return (
    <div>
      <h2>Countries</h2>
      <CountryForm
        selectedId={editingId}
        onSuccess={() => {
          loadCountries();
          setEditingId(null);
        }}
        onCancel={() => setEditingId(null)}
      />
      {loading ? (
        <p>Loading countries...</p>
      ) : (
        <ul>
          {Array.isArray(countries) && countries.length > 0 ? (
            countries.map(country => (
              <li key={country.id}>
                {country.name}{' '}
                <button onClick={() => setEditingId(country.id)}>Edit</button>{' '}
                <button
                  onClick={() => handleDelete(country.id)}
                  disabled={deletingId === country.id}
                >
                  {deletingId === country.id ? 'Deleting...' : 'Delete'}
                </button>
              </li>
            ))
          ) : (
            <li>No countries found</li>
          )}
        </ul>
      )}
    </div>
  );
};

export default CountryList;
