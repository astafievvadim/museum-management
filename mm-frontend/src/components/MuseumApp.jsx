import React, { useEffect, useState } from 'react';
import { getAllExhibits, getAllEvents, getAuthors } from '../api/api';

export default function MuseumApp() {
  const [exhibits, setExhibits] = useState([]);
  const [events, setEvents] = useState([]);
  const [authors, setAuthors] = useState([]);
  const [selectedExhibit, setSelectedExhibit] = useState(null);

  useEffect(() => {
    async function fetchData() {
      try {
        const [exhibitsData, eventsData, authorsData] = await Promise.all([
          getAllExhibits(),
          getAllEvents(),
          getAuthors(),
        ]);
        setExhibits(exhibitsData);
        setEvents(eventsData);
        setAuthors(authorsData);
      } catch (error) {
        console.error('Failed to load data', error);
      }
    }
    fetchData();
  }, []);

  return (
    <div className="museum-container">
      <h1>Welcome to the Museum</h1>

      <section>
        <h2>Exhibits</h2>
        <ul className="exhibit-list">
          {exhibits.map((exhibit) => (
            <li key={exhibit.id} onClick={() => setSelectedExhibit(exhibit)}>
              {exhibit.name}
            </li>
          ))}
        </ul>

        {selectedExhibit && (
          <div className="exhibit-details">
            <h3>{selectedExhibit.name}</h3>
            <p>{selectedExhibit.description}</p>

            {/* Placeholder for 3D model viewer */}
            <div className="model-viewer">
              <p>3D Model Viewer for: {selectedExhibit.name}</p>
              {/* Example: you can integrate something like <model-viewer> tag or Three.js here */}
            </div>

            <button onClick={() => setSelectedExhibit(null)}>Close</button>
          </div>
        )}
      </section>

      <section>
        <h2>Upcoming Events</h2>
        <ul>
          {events.map((event) => (
            <li key={event.id}>
              <strong>{event.name}</strong> — {new Date(event.date).toLocaleDateString()}
              <p>{event.description}</p>
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Authors</h2>
        <ul>
          {authors.map((author) => (
            <li key={author.id}>{author.name}</li>
          ))}
        </ul>
      </section>
    </div>
  );
}
