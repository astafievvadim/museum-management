import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { getAuthorById, createAuthor, updateAuthor, getCountries, fetchItems } from '../../api/api';
import '../tab.css';

function AuthorForm() {
  const [form, setForm] = useState({
    name: '',
    lastname: '',
    birthDate: '',
    deathDate: '',
    bio: '',
    countryId: '',
    itemIds: [],  // list of selected item IDs
  });

  const [countries, setCountries] = useState([]);
  const [items, setItems] = useState([]);

  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    // Load countries and items
    getCountries().then(setCountries);
    fetchItems().then(setItems);

    if (id) {
      getAuthorById(id).then(data => {
        setForm({
          name: data.name || '',
          lastname: data.lastname || '',
          birthDate: data.birthDate ? data.birthDate.split('T')[0] : '',
          deathDate: data.deathDate ? data.deathDate.split('T')[0] : '',
          bio: data.bio || '',
          countryId: data.countryId || '',
          itemIds: data.itemIds || [],
        });
      });
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleItemsChange = (e) => {
    // multiple select returns array of selected options values
    const options = e.target.options;
    const selected = [];
    for (let i = 0; i < options.length; i++) {
      if (options[i].selected) selected.push(Number(options[i].value));
    }
    setForm(prev => ({ ...prev, itemIds: selected }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (id) {
      await updateAuthor(id, form);
    } else {
      await createAuthor(form);
    }
    navigate('/authors');
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2 className="text-xl font-bold mb-4">{id ? 'Edit' : 'New'} Author</h2>

      <div className="mb-2">
        <label className="block">First Name:</label>
        <input name="name" value={form.name} onChange={handleChange} className="border p-1 w-full" required />
      </div>

      <div className="mb-2">
        <label className="block">Last Name:</label>
        <input name="lastname" value={form.lastname} onChange={handleChange} className="border p-1 w-full" required />
      </div>

      <div className="mb-2">
        <label className="block">Birth Date:</label>
        <input type="date" name="birthDate" value={form.birthDate} onChange={handleChange} className="border p-1 w-full" />
      </div>

      <div className="mb-2">
        <label className="block">Death Date:</label>
        <input type="date" name="deathDate" value={form.deathDate} onChange={handleChange} className="border p-1 w-full" />
      </div>

      <div className="mb-2">
        <label className="block">Bio:</label>
        <textarea name="bio" value={form.bio} onChange={handleChange} className="border p-1 w-full" rows="3" />
      </div>

      {/* Country dropdown */}
      <div className="mb-2">
        <label className="block">Country:</label>
        <select name="countryId" value={form.countryId} onChange={handleChange} className="border p-1 w-full" required>
          <option value="">Select country...</option>
          {countries.map(c => (
            <option key={c.id} value={c.id}>{c.name}</option>
          ))}
        </select>
        {/* Optional: Add new country link or button */}
      </div>

      {/* Items multiselect */}
      <div className="mb-2">
        <label className="block">Items:</label>
        <select
          multiple
          name="itemIds"
          value={form.itemIds}
          onChange={handleItemsChange}
          className="border p-1 w-full"
          size={5}
        >
          {items.map(item => (
            <option key={item.id} value={item.id}>{item.title}</option>
          ))}
        </select>
        {/* Optional: Add new item link or button */}
      </div>

      <button type="submit" className="bg-blue-600 text-white px-3 py-1 rounded">{id ? 'Update' : 'Create'}</button>
    </form>
  );
}

export default AuthorForm;
