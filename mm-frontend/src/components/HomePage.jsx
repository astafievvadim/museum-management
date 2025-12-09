import React, { useEffect, useState } from 'react';
import axios from 'axios';
import ExhibitCard from './ExhibitCard';

const HomePage = () => {
  const [exhibits, setExhibits] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    axios.get('/api/exhibits') // your backend endpoint here
      .then(response => {
        setExhibits(response.data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading...</div>;

  return (
    <div>
      <h1>Welcome to the Museum</h1>
      <div className="exhibits-list">
        {exhibits.map(exhibit => (
          <ExhibitCard key={exhibit.id} exhibit={exhibit} />
        ))}
      </div>
    </div>
  );
};

export default HomePage;
