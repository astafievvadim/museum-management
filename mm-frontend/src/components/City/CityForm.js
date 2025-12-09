import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getCityById, createCity, updateCity } from '../../api/api';

function CityForm() {
  const [form, setForm] = useState({
    name: '',
    postalCode: '',
    countryId: ''
  });

  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      getCityById(id).then(data => {
        setForm(data);
      });
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (id) {
      await updateCity(id, form);
    } else {
      await createCity({
        name: form.name,
        postalCode: form.postalCode
      }, form.countryId);
    }
    navigate('/cities');
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2 className="text-xl font-bold mb-4">{id ? 'Edit' : 'New'} City</h2>

      <div className="mb-2">
        <label className="block">City Name:</label>
        <input name="name" value={form.name} onChange={handleChange} className="border p-1 w-full" required />
      </div>

      <div className="mb-2">
        <label className="block">Postal Code:</label>
        <input name="postalCode" value={form.postalCode} onChange={handleChange} className="border p-1 w-full" required />
      </div>

      <div className="mb-2">
        <label className="block">Country ID:</label>
        <input type="number" name="countryId" value={form.countryId} onChange={handleChange} className="border p-1 w-full" required={!id} disabled={!!id} />
        {id && <small className="text-gray-500">Cannot change Country for existing City.</small>}
      </div>

      <button type="submit" className="bg-blue-600 text-white px-3 py-1 rounded">{id ? 'Update' : 'Create'}</button>
    </form>
  );
}

export default CityForm;
