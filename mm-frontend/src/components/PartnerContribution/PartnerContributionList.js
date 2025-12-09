import React, { useEffect, useState } from 'react';
import { fetchPartnerContributions, deletePartnerContribution } from '../api/api'; // Implement API calls

const PartnerContributionList = () => {
  const [contributions, setContributions] = useState([]);

  const loadContributions = async () => {
    const data = await fetchPartnerContributions();
    setContributions(data);
  };

  useEffect(() => {
    loadContributions();
  }, []);

  const handleDelete = async (id) => {
    if (window.confirm('Delete this partner contribution?')) {
      await deletePartnerContribution(id);
      loadContributions();
    }
  };

  return (
    <div className="partner-contribution-list">
      <h2>Partner Contributions</h2>
      <ul>
        {contributions.map((contrib) => (
          <li key={contrib.id}>
            Partner ID: <strong>{contrib.partnerId}</strong>, Gallery ID: <strong>{contrib.galleryId}</strong>, Event ID: <strong>{contrib.eventId}</strong>
            <br />
            Start Date: {new Date(contrib.startDate).toLocaleString()} &nbsp;
            End Date: {contrib.endDate ? new Date(contrib.endDate).toLocaleString() : 'N/A'}
            <button onClick={() => handleDelete(contrib.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PartnerContributionList;
