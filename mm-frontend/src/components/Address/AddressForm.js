import React, { useState, useEffect } from 'react';
import { 
  getAddressById, updateAddress, createAddress, 
  getCities, createCity, 
  getCountries, createCountry 
} from '../../api/api.js';
import { useNavigate, useParams } from 'react-router-dom';
import '../tab.css';

function AddressForm() {
  const [form, setForm] = useState({ street: '', postalCode: '', cityId: '', countryId: '' });
  const [cities, setCities] = useState([]);
  const [countries, setCountries] = useState([]);
  const [showNewCity, setShowNewCity] = useState(false);
  const [showNewCountry, setShowNewCountry] = useState(false);
  const [newCityName, setNewCityName] = useState('');
  const [newCountryName, setNewCountryName] = useState('');
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    getCities().then(setCities);
    getCountries().then(setCountries);
    if (id) {
      getAddressById(id).then(data => setForm(data));
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (id) {
      await updateAddress(id, form);
    } else {
      await createAddress(form);
    }
    navigate('/addresses');
  };

  // Handlers to create new City and Country
  const handleCreateCity = async () => {
    if (!newCityName.trim()) return alert('Enter city name');
    const created = await createCity({ name: newCityName.trim() });
    setCities(prev => [...prev, created]);
    setForm(prev => ({ ...prev, cityId: created.id }));
    setNewCityName('');
    setShowNewCity(false);
  };

  const handleCreateCountry = async () => {
    if (!newCountryName.trim()) return alert('Enter country name');
    const created = await createCountry({ name: newCountryName.trim() });
    setCountries(prev => [...prev, created]);
    setForm(prev => ({ ...prev, countryId: created.id }));
    setNewCountryName('');
    setShowNewCountry(false);
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2 className="text-xl font-bold mb-4">{id ? 'Edit' : 'New'} Address</h2>

      <div className="mb-2">
        <label className="block">Street:</label>
        <input 
          name="street" 
          value={form.street} 
          onChange={handleChange} 
          className="border p-1 w-full" 
          required 
        />
      </div>

      <div className="mb-2">
        <label className="block">Postal Code:</label>
        <input 
          name="postalCode" 
          value={form.postalCode} 
          onChange={handleChange} 
          className="border p-1 w-full" 
          required 
        />
      </div>

      <div className="mb-4">
        <label className="block">City:</label>
        <select 
          name="cityId" 
          value={form.cityId || ''} 
          onChange={handleChange} 
          className="border p-1 w-full" 
          required
        >
          <option value="" disabled>Select a city</option>
          {cities.map(c => (
            <option key={c.id} value={c.id}>{c.name}</option>
          ))}
        </select>
        <button 
          type="button" 
          className="text-blue-600 mt-1 underline" 
          onClick={() => setShowNewCity(!showNewCity)}
        >
          {showNewCity ? 'Cancel New City' : 'Add New City'}
        </button>
        {showNewCity && (
          <div className="mt-1">
            <input 
              type="text" 
              value={newCityName} 
              onChange={e => setNewCityName(e.target.value)} 
              placeholder="New city name" 
              className="border p-1 w-full mb-1"
            />
            <button type="button" onClick={handleCreateCity} className="bg-green-600 text-white px-2 py-1 rounded">
              Save City
            </button>
          </div>
        )}
      </div>

      <div className="mb-4">
        <label className="block">Country:</label>
        <select 
          name="countryId" 
          value={form.countryId || ''} 
          onChange={handleChange} 
          className="border p-1 w-full" 
          required
        >
          <option value="" disabled>Select a country</option>
          {countries.map(c => (
            <option key={c.id} value={c.id}>{c.name}</option>
          ))}
        </select>
        <button 
          type="button" 
          className="text-blue-600 mt-1 underline" 
          onClick={() => setShowNewCountry(!showNewCountry)}
        >
          {showNewCountry ? 'Cancel New Country' : 'Add New Country'}
        </button>
        {showNewCountry && (
          <div className="mt-1">
            <input 
              type="text" 
              value={newCountryName} 
              onChange={e => setNewCountryName(e.target.value)} 
              placeholder="New country name" 
              className="border p-1 w-full mb-1"
            />
            <button type="button" onClick={handleCreateCountry} className="bg-green-600 text-white px-2 py-1 rounded">
              Save Country
            </button>
          </div>
        )}
      </div>

      <button type="submit" className="bg-blue-600 text-white px-3 py-1 rounded">
        {id ? 'Update' : 'Create'}
      </button>
    </form>
  );
}

export default AddressForm;
