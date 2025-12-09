import React, { useEffect, useState } from 'react';
import { getCities, deleteCity } from '../../api/api';
import { Link } from 'react-router-dom';

function CityList() {
  const [cities, setCities] = useState([]);

  const loadCities = async () => {
    const data = await getCities();
    setCities(data);
  };

  useEffect(() => {
    loadCities();
  }, []);

  const handleDelete = async (id) => {
    await deleteCity(id);
    loadCities();
  };

  return (
    <div>
      <h2 className="text-xl font-bold mb-4">Cities</h2>
      <Link to="/dashboard/cities/new" className="bg-green-500 text-white px-3 py-1 rounded mb-4 inline-block">+ New City</Link>
      <ul>
        {cities.map((city, index) => (
          <li key={index} className="border p-2 mb-2 flex justify-between items-center">
            <span>{city.name} ({city.postalCode})</span>
            <div>
              <Link to={`/cities/${city.id}/edit`} className="mr-2 text-blue-600">Edit</Link>
              <button onClick={() => handleDelete(city.id)} className="text-red-600">Delete</button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CityList;
