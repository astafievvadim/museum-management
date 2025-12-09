// CountryForm.jsx
import React, { useState, useEffect } from 'react';
import { createCountry, updateCountry, getCountryById } from '../../api/api';

const CountryForm = ({ selectedId, onSuccess, onCancel }) => {
  const [name, setName] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (selectedId) {
      setLoading(true);
      getCountryById(selectedId)
        .then(country => {
          if (country && country.name) {
            setName(country.name);
          } else {
            setName('');
          }
        })
        .catch(err => {
          console.error('Failed to fetch country:', err);
          setName('');
        })
        .finally(() => setLoading(false));
    } else {
      setName('');
    }
  }, [selectedId]);

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    const countryData = { name };

    const action = selectedId
      ? updateCountry(selectedId, countryData)
      : createCountry(countryData);

    action
      .then(() => {
        onSuccess();
        setName('');
      })
      .catch(err => {
        console.error('Submit failed:', err);
      })
      .finally(() => setLoading(false));
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>{selectedId ? 'Edit Country' : 'Add Country'}</h3>
      <input
        type="text"
        value={name}
        placeholder="Country Name"
        onChange={(e) => setName(e.target.value)}
        required
        disabled={loading}
      />
      <button type="submit" disabled={loading}>
        {loading ? 'Saving...' : 'Save'}
      </button>
      {onCancel && (
        <button
          type="button"
          onClick={onCancel}
          disabled={loading}
        >
          Cancel
        </button>
      )}
    </form>
  );
};

export default CountryForm;
