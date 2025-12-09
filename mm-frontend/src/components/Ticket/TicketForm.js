import React, { useState, useEffect } from 'react';
import {
  createTicket,
  updateTicket,
  getAllCustomers,
  createCustomer,
  getAllEvents,
  getTicketTypes,
  createTicketType,
  getAllEmployees,
} from '../../api/api';
import './Ticket.css';

function TicketForm({ existingTicket, onSuccess, onCancel }) {
  const [formData, setFormData] = useState({
    purchaseDate: '',
    customerId: '',
    eventId: '',
    ticketTypeId: '',
    employeeId: '',
    valid: true,
  });

  // Lists for dropdowns
  const [customers, setCustomers] = useState([]);
  const [events, setEvents] = useState([]);
  const [ticketTypes, setTicketTypes] = useState([]);
  const [employees, setEmployees] = useState([]);

  // Controls for showing add new entity forms
  const [addingCustomer, setAddingCustomer] = useState(false);

  // New entity form states
  const [newCustomerName, setNewCustomerName] = useState('');
  const [newEventName, setNewEventName] = useState('');
  const [newTicketTypeName, setNewTicketTypeName] = useState('');
  const [newEmployeeName, setNewEmployeeName] = useState('');

  useEffect(() => {
    // Load all dropdown lists on mount
    async function fetchData() {
      try {
        const [custs, evnts, tTypes, emps] = await Promise.all([
          getAllCustomers(),
          getAllEvents(),
          getTicketTypes(),
          getAllEmployees(),
        ]);
        setCustomers(custs);
        setEvents(evnts);
        setTicketTypes(tTypes);
        setEmployees(emps);
      } catch (e) {
        alert('Ошибка загрузки данных');
        console.error(e);
      }
    }
    fetchData();
  }, []);

  useEffect(() => {
    if (existingTicket) {
      setFormData({
        purchaseDate: existingTicket.purchaseDate || '',
        customerId: existingTicket.customer?.id || '',
        eventId: existingTicket.event?.id || '',
        ticketTypeId: existingTicket.ticketType?.id || '',
        employeeId: existingTicket.employee?.id || '',
        valid: existingTicket.valid ?? true,
      });
    } else {
      setFormData({
        purchaseDate: '',
        customerId: '',
        eventId: '',
        ticketTypeId: '',
        employeeId: '',
        valid: true,
      });
    }
  }, [existingTicket]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({ ...prev, [name]: type === 'checkbox' ? checked : value }));
  };

  // Add new entity handlers
  // Customer add form states updated for firstname, lastname, email
  const [newCustomerFirstName, setNewCustomerFirstName] = useState('');
  const [newCustomerLastName, setNewCustomerLastName] = useState('');
  const [newCustomerEmail, setNewCustomerEmail] = useState('');

  // Employee add form states updated for firstname, lastname, email
  const [newEmployeeFirstName, setNewEmployeeFirstName] = useState('');
  const [newEmployeeLastName, setNewEmployeeLastName] = useState('');
  const [newEmployeeEmail, setNewEmployeeEmail] = useState('');

  // Modify addNewCustomer to use firstname, lastname, email
  const addNewCustomer = async () => {
    if (!newCustomerFirstName.trim() || !newCustomerLastName.trim() || !newCustomerEmail.trim()) {
      return alert('Требуются имя, фамилия и email клиента');
    }
    try {
      const created = await createCustomer({
        firstName: newCustomerFirstName.trim(),  // <- corrected key
        lastName: newCustomerLastName.trim(),    // <- corrected key
        email: newCustomerEmail.trim(),
      });
      setCustomers((prev) => [...prev, created]);
      setFormData((prev) => ({ ...prev, customerId: created.id }));
      setNewCustomerFirstName('');
      setNewCustomerLastName('');
      setNewCustomerEmail('');
      setAddingCustomer(false);
    } catch (e) {
      alert('Не удалось создать клиента');
      console.error(e);
    }
  };

  const setTodayDate = () => {
    const today = new Date();
    const formatted = today.toISOString().split('T')[0];
    setFormData((prev) => ({ ...prev, purchaseDate: formatted }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    console.error(formData);

    const payload = {
      purchaseDate: formData.purchaseDate,
      valid: formData.valid,
      customerId: Number(formData.customerId),
      eventId: Number(formData.eventId),
      ticketTypeId: Number(formData.ticketTypeId),
      employeeId: Number(formData.employeeId),
      isValid: true,
    };

    try {
      if (existingTicket) {
        await updateTicket(existingTicket.id, payload);
      } else {
        await createTicket(payload);
      }
      if (onSuccess) onSuccess();
    } catch (error) {
      alert('Ошибка при отправке билета.');
      console.error(error);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="item-card space-y-4 max-w-md p-6">
      <h2 className="text-2xl">{existingTicket ? 'Редактировать билет' : 'Создать билет'}</h2>

      <div>
        <label>Дата покупки:</label>
        <div className="flex space-x-2 items-center">
          <input
            type="date"
            name="purchaseDate"
            value={formData.purchaseDate}
            onChange={handleChange}
            className="border p-1 flex-grow"
            required
          />
          <button
            type="button"
            onClick={setTodayDate}
            className="bg-gray-600 text-white px-2 py-1 rounded text-sm"
          >
            Сегодня
          </button>
        </div>
      </div>

      {/* Customer */}
      <div>
        <label>Клиент:</label>
        <div className="flex space-x-2 items-center">
          {/* Customer dropdown */}
          <select
            name="customerId"
            value={formData.customerId}
            onChange={handleChange}
            className="border p-1 flex-grow"
            required
          >
            <option value="">Выберите клиента</option>
            {customers.map((c) => (
              <option key={c.id} value={c.id}>
                {`${c.firstName} ${c.lastName} (${c.email})`}
              </option>
            ))}
          </select>
          <button
            type="button"
            onClick={() => setAddingCustomer((v) => !v)}
            className="bg-green-600 text-white px-2 py-1 rounded text-sm"
          >
            {addingCustomer ? 'Отмена' : 'Добавить нового'}
          </button>
        </div>
        {addingCustomer && (
          <div className="mt-2 grid grid-cols-1 gap-2">
            <input
              type="text"
              placeholder="Имя"
              value={newCustomerFirstName}
              onChange={(e) => setNewCustomerFirstName(e.target.value)}
              className="border p-1"
            />
            <input
              type="text"
              placeholder="Фамилия"
              value={newCustomerLastName}
              onChange={(e) => setNewCustomerLastName(e.target.value)}
              className="border p-1"
            />
            <input
              type="email"
              placeholder="Email"
              value={newCustomerEmail}
              onChange={(e) => setNewCustomerEmail(e.target.value)}
              className="border p-1"
            />
            <button
              type="button"
              onClick={addNewCustomer}
              className="bg-blue-600 text-white px-2 py-1 rounded"
            >
              Сохранить
            </button>
          </div>
        )}
      </div>

      {/* Event */}
      <div>
        <label>Событие:</label>
        <select
          name="eventId"
          value={formData.eventId}
          onChange={handleChange}
          className="border p-1 flex-grow"
          required
        >
          <option value="">Выберите событие</option>
          {events.map((e) => (
            <option key={e.id} value={e.id}>
              {e.name || `#${e.id}`}
            </option>
          ))}
        </select>
      </div>

      {/* Ticket Type */}
      <div>
        <label>Тип билета:</label>
        <select
          name="ticketTypeId"
          value={formData.ticketTypeId}
          onChange={handleChange}
          className="border p-1 flex-grow"
          required
        >
          <option value="">Выберите тип билета</option>
          {ticketTypes.map((t) => (
            <option key={t.id} value={t.id}>
              {t.name || `#${t.id}`}
            </option>
          ))}
        </select>
      </div>

      {/* Employee */}
      <div>
        <label>Сотрудник:</label>
        <select
          name="employeeId"
          value={formData.employeeId}
          onChange={handleChange}
          className="border p-1 flex-grow"
        >
          <option value="">Выберите сотрудника</option>
          {employees.map((emp) => (
            <option key={emp.id} value={emp.id}>
              {`${emp.firstName} ${emp.lastName} (${emp.email})`}
            </option>
          ))}
        </select>
      </div>

      <div className="flex items-center space-x-4 mt-4">
        <label>
          <input
            type="checkbox"
            name="valid"
            checked={formData.valid}
            onChange={handleChange}
          />
          Валиден
        </label>
      </div>

      <div className="flex space-x-4 mt-4">
        <button
          type="submit"
          className="bg-blue-600 text-white px-4 py-2 rounded"
        >
          {existingTicket ? 'Обновить билет' : 'Создать билет'}
        </button>
        {onCancel && (
          <button
            type="button"
            onClick={onCancel}
            className="bg-gray-400 text-white px-4 py-2 rounded"
          >
            Отмена
          </button>
        )}
      </div>
    </form>
  );
}

export default TicketForm;
