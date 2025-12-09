import React, { useEffect, useState } from 'react';
import { getTickets, deleteTicket, getTicketForPrint } from '../../api/api';
import museumLogo from '../../assets/museum.jpg';
import TicketForm from './TicketForm';
import './Ticket.css';

function TicketList() {
  const [tickets, setTickets] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingTicket, setEditingTicket] = useState(null);

  useEffect(() => {
    fetchTickets();
  }, []);

  const fetchTickets = async () => {
    try {
      const data = await getTickets();
      setTickets(data);
    } catch (err) {
      console.error('Error fetching tickets:', err);
      alert('Не удалось загрузить билеты.');
    }
  };

  const handleDelete = async (id) => {
    try {
      await deleteTicket(id);
      fetchTickets();
    } catch (err) {
      alert('Не удалось удалить билет.');
    }
  };

const handlePrintTicket = async (ticket) => {
  try {
    if (!ticket.id) return alert('Отсутствует ID билета');
    const printData = await getTicketForPrint(ticket.id);
    if (!printData) return alert('Не удалось получить данные для билета.');

    generateTicketImage(printData, (dataURL) => {
      const link = document.createElement('a');
      link.href = dataURL;
      link.download = `ticket_${ticket.id}.png`;
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    });

  } catch (err) {
    console.error('Ошибка при сохранении билета:', err);
    alert('Не удалось сохранить изображение билета.');
  }
};


  const generateTicketImage = (printData, callback) => {
    const trimDate = (d) => (d ? d.substring(0, 16).replace('T', ' ') : '');
    const canvas = document.createElement('canvas');
    canvas.width = 900;
    canvas.height = 380;
    const ctx = canvas.getContext('2d');

    ctx.fillStyle = '#f6ebd9';
    ctx.fillRect(0, 0, canvas.width, canvas.height);

    const logo = new Image();
    logo.src = museumLogo;

    logo.onload = () => {
      ctx.drawImage(logo, 25, 25, 100, 100);

      ctx.fillStyle = '#4c352d';
      ctx.font = 'bold 42px sans-serif';
      ctx.fillText('NotSoReal Museum', 150, 70);

      ctx.fillStyle = '#aa6442';
      ctx.font = 'bold 50px sans-serif';
      ctx.fillText(`${printData.customerName} ${printData.customerLastname}`, 150, 130);

      ctx.fillStyle = '#4c352d';
      ctx.font = '26px sans-serif';
      ctx.fillText(`${printData.eventName}`, 150, 190);
      ctx.fillText(`${trimDate(printData.eventDate)}`, 600, 190);
      ctx.fillText(`${printData.ticketType}`, 150, 240);
      ctx.fillText(`$${printData.price}`, 600, 240);
      ctx.fillText(`${trimDate(printData.purchaseDate)}`, 150, 290);

      ctx.fillStyle = '#dda04d';
      ctx.fillRect(canvas.width - 70, 0, 70, canvas.height);

      ctx.save();
      ctx.translate(canvas.width - 35, canvas.height / 2);
      ctx.rotate(-Math.PI / 2);
      ctx.fillStyle = '#4c352d';
      ctx.font = 'bold 60px sans-serif';
      ctx.textAlign = 'center';
      ctx.fillText('КОНТРОЛЬ', 0, 20);
      ctx.restore();

      const dataURL = canvas.toDataURL('image/png');
      callback(dataURL);
    };
  };

  const handleAddClick = () => {
    setEditingTicket(null);
    setShowForm(true);
  };

  const handleEditClick = (ticket) => {
    setEditingTicket(ticket);
    setShowForm(true);
  };

  const handleFormSuccess = () => {
    setShowForm(false);
    setEditingTicket(null);
    fetchTickets();
  };

  const handleFormCancel = () => {
    setShowForm(false);
    setEditingTicket(null);
  };

  return (
    <div className="p-4">
      <h2 className="text-2xl mb-4">Билеты</h2>

      {!showForm && (
        <>
          <button
            onClick={handleAddClick}
            className="mb-4 bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
          >
            Добавить билет
          </button>

<table className="ticket-table w-full border-collapse">
  <thead>
    <tr className="bg-gray-200 text-left">
      <th className="border px-4 py-2">ID</th>
      <th className="border px-4 py-2">Дата покупки</th>
      <th className="border px-4 py-2">Покупатель</th>
      <th className="border px-4 py-2">Мероприятие</th>
      <th className="border px-4 py-2">Тип билета</th>
      <th className="border px-4 py-2">Сотрудник</th>
      <th className="border px-4 py-2">Действителен</th>
      <th className="border px-4 py-2">Действия</th>
    </tr>
  </thead>
  <tbody>
    {tickets.map((ticket) => (
      <tr key={ticket.id} className="border-b hover:bg-gray-50">
        <td className="border px-4 py-2 font-semibold">{ticket.id}</td>
        <td className="border px-4 py-2">{new Date(ticket.purchaseDate).toLocaleDateString()}</td>
        <td className="border px-4 py-2">{ticket.customer ? `${ticket.customer.firstName} ${ticket.customer.lastName || ''}` : 'Н/Д'}</td>
        <td className="border px-4 py-2">{ticket.event ? ticket.event.name : 'Н/Д'}</td>
        <td className="border px-4 py-2">{ticket.ticketType ? ticket.ticketType.name : 'Н/Д'}</td>
        <td className="border px-4 py-2">{ticket.employee ? `${ticket.employee.firstName} ${ticket.employee.lastName || ''}` : 'Н/Д'}</td>
        <td className="border px-4 py-2">{ticket.valid ? 'Да' : 'Нет'}</td>
        <td className="border px-4 py-2 space-x-2">
          <button onClick={() => handleEditClick(ticket)} className="bg-yellow-400 px-2 py-1 rounded hover:bg-yellow-500">Редактировать</button>
          <button onClick={() => handleDelete(ticket.id)} className="bg-red-600 px-2 py-1 rounded text-white hover:bg-red-700">Удалить</button>
          <button onClick={() => handlePrintTicket(ticket)} className="bg-blue-600 px-2 py-1 rounded text-white hover:bg-blue-700">Печать</button>
        </td>
      </tr>
    ))}
  </tbody>
</table>
        </>
      )}

      {showForm && (
        <TicketForm
          existingTicket={editingTicket}
          onSuccess={handleFormSuccess}
          onCancel={handleFormCancel}
        />
      )}
    </div>
  );
}

export default TicketList;
