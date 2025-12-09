import React, { useState } from 'react';
import { createPartnerContribution } from '../api/api'; // Implement API call accordingly

const PartnerContributionForm = ({ onSuccess }) => {
  const [partnerId, setPartnerId] = useState('');
  const [galleryId, setGalleryId] = useState('');
  const [eventId, setEventId] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!partnerId || !galleryId || !eventId || !startDate) {
      alert('Please fill in all required fields (partner, gallery, event, start date)');
      return;
    }

    const newContribution = {
      partnerId: Number(partnerId),
      galleryId: Number(galleryId),
      eventId: Number(eventId),
      startDate,
      endDate: endDate || null,
    };

    try {
      await createPartnerContribution(newContribution);
      if (onSuccess) onSuccess();
      setPartnerId('');
      setGalleryId('');
      setEventId('');
      setStartDate('');
      setEndDate('');
      alert('Partner Contribution created successfully!');
    } catch (err) {
      console.error(err);
      alert('Failed to create partner contribution.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="partner-contribution-form">
      <input
        type="number"
        placeholder="Partner ID"
        value={partnerId}
        onChange={(e) => setPartnerId(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="Gallery ID"
        value={galleryId}
        onChange={(e) => setGalleryId(e.target.value)}
        required
      />
      <input
        type="number"
        placeholder="Event ID"
        value={eventId}
        onChange={(e) => setEventId(e.target.value)}
        required
      />
      <input
        type="datetime-local"
        placeholder="Start Date"
        value={startDate}
        onChange={(e) => setStartDate(e.target.value)}
        required
      />
      <input
        type="datetime-local"
        placeholder="End Date (optional)"
        value={endDate}
        onChange={(e) => setEndDate(e.target.value)}
      />
      <button type="submit">Add Contribution</button>
    </form>
  );
};

export default PartnerContributionForm;
