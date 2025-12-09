import React, { useEffect, useState } from 'react';
import { fetchItems, deleteItem, downloadFile, getFileUrl } from '../../api/api';
import ItemForm from './ItemForm';
import './Item.css';
import ModelViewer from './ModelViewer';

function ItemList() {
  const [items, setItems] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingItem, setEditingItem] = useState(null);

  // Для модального просмотра 3D
  const [show3DModal, setShow3DModal] = useState(false);
  const [modelUrl, setModelUrl] = useState('');

  useEffect(() => {
    _fetchItems();
  }, []);

  const _fetchItems = async () => {
    try {
      const data = await fetchItems();
      setItems(data);
    } catch (err) {
      console.error('Ошибка загрузки экспонатов:', err);
      alert('Не удалось загрузить экспонаты.');
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteItem(id);
      _fetchItems();
    } catch (err) {
      alert('Не удалось удалить экспонат.');
    }
  };

  const handleAddClick = () => {
    setEditingItem(null);
    setShowForm(true);
  };

  const handleEditClick = (item) => {
    setEditingItem(item);
    setShowForm(true);
  };

  const handleFormSuccess = () => {
    setShowForm(false);
    setEditingItem(null);
    _fetchItems();
  };

  const handleFormCancel = () => {
    setShowForm(false);
    setEditingItem(null);
  };

const handleView3DClick = (item) => {
  if (item.fileMetadata && item.fileMetadata.filename) {
    // Use getFileUrl to get the direct URL for embedding:
    setModelUrl(getFileUrl(item.fileMetadata.filename));
    setShow3DModal(true);

    // If you wanted to download/open in a new tab, you'd call:
    // downloadFile(item.fileMetadata.filename);
  } else {
    alert('3D-модель не загружена для этого экспоната.');
  }
};


  // Закрыть модальное окно
  const close3DModal = () => {
    setShow3DModal(false);
    setModelUrl('');
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl mb-4">Экспонаты</h2>

      {!showForm && (
        <>
          <button
            onClick={handleAddClick}
            className="mb-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
          >
            Добавить экспонат
          </button>

          <table className="item-table w-full border-collapse">
            <thead>
  <tr className="bg-gray-200 text-left">
    <th className="border px-4 py-2">ID</th>
    <th className="border px-4 py-2">Название</th>
    <th className="border px-4 py-2">Описание</th>
    <th className="border px-4 py-2">Год</th>
    <th className="border px-4 py-2">Тип</th>
    <th className="border px-4 py-2">Автор</th>
    <th className="border px-4 py-2">Действия</th>
    <th className="border px-4 py-2">3D Модель</th>
  </tr>
</thead>
            <tbody>
              {items.map((item) => (
                <tr key={item.id} className="border-b hover:bg-gray-50">
                  <td className="border px-4 py-2 font-semibold">{item.id}</td>
                  <td className="border px-4 py-2">{item.name}</td>
                  <td className="border px-4 py-2">{item.description || '—'}</td>
                  <td className="border px-4 py-2">{item.year ? new Date(item.year).getFullYear() : 'Н/Д'}</td>
                  <td className="border px-4 py-2">{item.type}</td>
                  <td className="border px-4 py-2">{item.author ? `${item.author.firstName} ${item.author.lastName}` : 'Без автора'}</td>
                  <td className="border px-4 py-2 space-x-2">
                    <button onClick={() => handleEditClick(item)} className="bg-yellow-400 px-2 py-1 rounded hover:bg-yellow-500">
                      Редактировать
                    </button>
                    <button onClick={() => handleDelete(item.id)} className="bg-red-600 px-2 py-1 rounded text-white hover:bg-red-700">
                      Удалить
                    </button>
                  </td>
                  <td className="border px-4 py-2">
                    <button
                      onClick={() => handleView3DClick(item)}
                      className="bg-blue-600 text-white px-2 py-1 rounded hover:bg-blue-700"
                    >
                      Смотреть 3D
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </>
      )}

      {showForm && (
        <ItemForm
          existingItem={editingItem}
          onSuccess={handleFormSuccess}
          onCancel={handleFormCancel}
        />
      )}

      {/* Модальное окно для просмотра 3D */}

{show3DModal && (
  <div
    className="fixed inset-0 bg-black bg-opacity-60 flex justify-center items-center z-50"
    onClick={close3DModal}
  >
    <div
      className="bg-white p-4 rounded max-w-3xl w-full max-h-full overflow-auto relative"
      onClick={(e) => e.stopPropagation()}
    >
      <button
        onClick={close3DModal}
        className="absolute top-2 right-2 text-gray-700 font-bold text-xl"
        aria-label="Закрыть"
      >
        ×
      </button>

      <ModelViewer modelUrl={modelUrl} />
    </div>
  </div>
)}
    </div>
  );
}

export default ItemList;
