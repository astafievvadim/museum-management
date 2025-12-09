import React, { useState } from 'react';
import { deleteFile, downloadFile, getFileDetails } from '../../api/api';
import ThreeDViewer from './ThreeDViewer';

const FileListItem = ({ filename, onDelete }) => {
  const [details, setDetails] = useState(null);

  const fetchDetails = async () => {
    const data = await getFileDetails(filename);
    setDetails(data);
  };

  return (
    <div className="file-list-item">
      <p>{filename}</p>
      <button onClick={() => downloadFile(filename)}>Download</button>
      <button onClick={() => onDelete(filename)}>Delete</button>
      <button onClick={fetchDetails}>Details</button>

      {details && (
        <div>
          <p>Type: {details.contentType}</p>
          <p>Size: {details.size} bytes</p>
          {details.contentType.includes('model') && (
            <ThreeDViewer fileUrl={`${process.env.REACT_APP_API_URL}/files/${filename}`} />
          )}
        </div>
      )}
    </div>
  );
};

export default FileListItem;
