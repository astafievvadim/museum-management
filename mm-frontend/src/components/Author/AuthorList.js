import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAuthors } from '../../api/api';
import '../tab.css';

function AuthorList() {
  const [authors, setAuthors] = useState([]);

  useEffect(() => {
    getAuthors().then(setAuthors);
  }, []);

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Authors</h2>
      <Link to="/dashboard/authors/new" className="bg-green-600 text-white px-3 py-1 rounded mb-4 inline-block">Add Author</Link>

      <table className="min-w-full border-collapse border border-gray-300">
        <thead>
          <tr>
            <th className="border border-gray-300 p-2">Name</th>
            <th className="border border-gray-300 p-2">Country</th>
            <th className="border border-gray-300 p-2">Items Count</th>
            <th className="border border-gray-300 p-2">Actions</th>
          </tr>
        </thead>
        <tbody>
          {authors.map(a => (
            <tr key={a.id}>
              <td className="border border-gray-300 p-2">{a.name} {a.lastname}</td>
              <td className="border border-gray-300 p-2">{a.countryName || 'N/A'}</td>
              <td className="border border-gray-300 p-2">{a.itemIds ? a.itemIds.length : 0}</td>
              <td className="border border-gray-300 p-2">
                <Link to={`/authors/${a.id}`} className="text-blue-600 hover:underline mr-2">Edit</Link>
                {/* Add Delete button if needed */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AuthorList;
