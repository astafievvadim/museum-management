const ExhibitCard = ({ exhibit }) => (
  <div className="exhibit-card">
    <h3>{exhibit.title}</h3>
    <img src={exhibit.imageUrl} alt={exhibit.title} />
    <p>{exhibit.description}</p>
  </div>
);

export default ExhibitCard;
