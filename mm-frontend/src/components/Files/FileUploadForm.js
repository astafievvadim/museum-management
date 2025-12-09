import React, { useState } from 'react';
import { uploadFile } from '../../api/api';

const FileUploadForm = ({ onSuccess }) => {
  const [file, setFile] = useState(null);
  const [itemId, setItemId] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!file || !itemId) {
      alert('Please select a file and enter an item ID.');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('itemId', itemId);

    try {
      await uploadFile(formData);
      if (onSuccess) onSuccess();
      alert('File uploaded!');
    } catch (err) {
      console.error(err);
      alert('File upload failed.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="file-upload-form">
      <input type="file" onChange={(e) => setFile(e.target.files[0])} />
      <input type="number" placeholder="Item ID" value={itemId} onChange={(e) => setItemId(e.target.value)} />
      <button type="submit">Upload File</button>
    </form>
  );
};

export default FileUploadForm;
