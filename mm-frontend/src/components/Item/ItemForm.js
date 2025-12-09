import React, { useState, useEffect } from 'react';
import { createItem, updateItem, getAuthors, uploadFile } from '../../api/api';
import './Item.css';

function ItemForm({ existingItem, onSuccess, onCancel }) {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    year: '',
    type: 'PAINTING',
    authorId: '',
    file: null,
    fileMetadataId: null,
  });

  const [authors, setAuthors] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    async function fetchAuthors() {
      try {
        const data = await getAuthors();
        setAuthors(data);
      } catch (e) {
        alert('Ошибка загрузки авторов');
        console.error(e);
      }
    }
    fetchAuthors();
  }, []);

  useEffect(() => {
    if (existingItem) {
      setFormData({
        name: existingItem.name || '',
        description: existingItem.description || '',
        // Для year выводим просто год как строку (например "2023")
        year: existingItem.year ? String(existingItem.year) : '',
        type: existingItem.type || 'PAINTING',
        authorId: existingItem.author ? String(existingItem.author.id) : '',
        file: null,
        fileMetadataId: existingItem.fileMetadata ? existingItem.fileMetadata.id : null,
      });
    } else {
      setFormData({
        name: '',
        description: '',
        year: '',
        type: 'PAINTING',
        authorId: '',
        file: null,
        fileMetadataId: null,
      });
    }
  }, [existingItem]);

  const handleChange = (e) => {
    const { name, value, type, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'file' ? files[0] : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    // Преобразуем год в число или null
    const yearValue = formData.year ? Number(formData.year) : null;

    // Формируем payload без fileMetadata, т.к. файл загружается отдельно
    const payload = {
      name: formData.name,
      description: formData.description,
      year: yearValue,
      type: formData.type,
      author: formData.authorId ? { id: Number(formData.authorId) } : null,
      // fileMetadata убираем из payload, будет обновляться отдельно
    };

    try {
      let savedItem;

      if (existingItem) {
        if (formData.file) {
          // Если загружен новый файл, сначала обновляем предмет без fileMetadata (чтобы отвязать старый)
          savedItem = await updateItem(existingItem.id, payload);
        } else {
          // Если файл не меняется, можно отправить с fileMetadata
          if (formData.fileMetadataId) {
            savedItem = await updateItem(existingItem.id, {
              ...payload,
              fileMetadata: { id: formData.fileMetadataId },
            });
          } else {
            savedItem = await updateItem(existingItem.id, payload);
          }
        }
      } else {
        savedItem = await createItem(payload);
      }

      // Если загружен файл, загружаем и обновляем предмет с fileMetadata
      if (formData.file) {
        const updatedItem = await uploadFile(formData.file, savedItem.id);
        // После загрузки файла updatedItem содержит обновленный item с fileMetadata
        setFormData((prev) => ({
          ...prev,
          fileMetadataId: updatedItem.fileMetadata ? updatedItem.fileMetadata.id : null,
          file: null,
        }));
      }

      if (onSuccess) onSuccess();
    } catch (err) {
      alert('Ошибка при сохранении экспоната.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="item-card space-y-4 max-w-md p-6">
      <h2 className="text-2xl">{existingItem ? 'Редактировать экспонат' : 'Добавить экспонат'}</h2>

      <div>
        <label>Название:</label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          required
          className="border p-1 w-full"
          disabled={loading}
        />
      </div>

      <div>
        <label>Описание:</label>
        <textarea
          name="description"
          value={formData.description}
          onChange={handleChange}
          className="border p-1 w-full"
          disabled={loading}
        />
      </div>

      <div>
        <label>Год:</label>
        {/* меняем type date на number для ввода года */}
        <input
          type="number"
          name="year"
          value={formData.year}
          onChange={handleChange}
          className="border p-1 w-full"
          disabled={loading}
          min="1000"
          max={new Date().getFullYear()}
          placeholder="Год создания"
        />
      </div>

      <div>
        <label>Тип:</label>
        <select
          name="type"
          value={formData.type}
          onChange={handleChange}
          className="border p-1 w-full"
          disabled={loading}
        >
          <option value="PAINTING">Картина</option>
          <option value="SCULPTURE">Скульптура</option>
          <option value="JEWELRY">Украшение</option>
          <option value="OTHER">Другое</option>
        </select>
      </div>

      <div>
        <label>Автор:</label>
        <select
          name="authorId"
          value={formData.authorId}
          onChange={handleChange}
          className="border p-1 w-full"
          disabled={loading}
        >
          <option value="">Без автора</option>
          {authors.map((a) => (
            <option key={a.id} value={a.id}>
              {`${a.firstName} ${a.lastName}`}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>Файл изображения:</label>
        <input
          type="file"
          name="file"
          onChange={handleChange}
          className="border p-1 w-full"
          disabled={loading}
          accept="image/*"
        />
        {formData.fileMetadataId && !formData.file && (
          <p className="text-sm text-gray-600 mt-1">
            Файл уже загружен (оставьте поле пустым, чтобы не менять)
          </p>
        )}
      </div>

      <div className="flex space-x-4 mt-4">
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded"
          disabled={loading}
        >
          {loading ? (existingItem ? 'Сохранение...' : 'Добавление...') : existingItem ? 'Обновить' : 'Добавить'}
        </button>
        {onCancel && (
          <button
            type="button"
            onClick={onCancel}
            className="bg-gray-400 text-white px-4 py-2 rounded"
            disabled={loading}
          >
            Отмена
          </button>
        )}
      </div>
    </form>
  );
}

export default ItemForm;
