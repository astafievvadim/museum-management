import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/api.js';
import { jsPDF } from 'jspdf';
import museumLogo from '../assets/museum.jpg';

const AdminPage = () => {
  const clearCookie = (name) => {
    document.cookie = `${name}=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
  };

  const entities = ['tickets', 'customers', 'events', 'cities', 'posts', 'authors'];

  const relatedEntitiesMap = {
    tickets: {
      customer: { endpoint: '/api/customers', labelKey: 'name', valueKey: 'id' },
      event: { endpoint: '/api/events', labelKey: 'name', valueKey: 'id' },
      ticketType: { endpoint: '/api/ticket-types', labelKey: 'name', valueKey: 'id' },
      comments: { endpoint: '/api/comments', labelKey: 'text', valueKey: 'id' },
      tickets: { endpoint: '/api/tickets', labelKey: 'id', valueKey: 'id' },
      address: { endpoint: '/api/addresses', labelKey: 'street', valueKey: 'id' },
    },
    posts: {
      authorId: { endpoint: '/api/authors', labelKey: 'name', valueKey: 'id' },
    },
  };

  const [data, setData] = useState({});
  const [error, setError] = useState(null);
  const [activeTab, setActiveTab] = useState('tickets');
  const [editingItem, setEditingItem] = useState(null);
  const [addingNew, setAddingNew] = useState(false);
  const [formData, setFormData] = useState({});
  const [relatedLists, setRelatedLists] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    fetchAllData();
    fetchRelatedLists();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeTab]);

  const fetchRelatedLists = async () => {
    const relatedEntities = relatedEntitiesMap[activeTab];
    if (!relatedEntities) {
      setRelatedLists({});
      return;
    }
    const newRelatedLists = {};
    try {
      for (const key in relatedEntities) {
        const { endpoint, labelKey, valueKey } = relatedEntities[key];
        const res = await api.get(endpoint);
        if (Array.isArray(res.data)) {
          if (activeTab === 'tickets' && key === 'customer') {
            // Special formatting for customers in tickets tab
            newRelatedLists[key] = res.data.map(item => ({
              label: `${item.firstName} ${item.lastName} (${item.email})`,
              value: item[valueKey],
              raw: item,
            }));
          } else {
            newRelatedLists[key] = res.data.map(item => ({
              label: item[labelKey],
              value: item[valueKey],
              raw: item,
            }));
          }
        } else {
          newRelatedLists[key] = [];
        }
      }
      setRelatedLists(newRelatedLists);
    } catch (error) {
      console.error('Failed fetching related lists:', error);
      alert('Failed to fetch related lists.');
    }
  };


  const handleApiError = (error) => {
    console.error('API Error:', error);
    alert('An error occurred while fetching data.');
  };

  const fetchAllData = async () => {
    try {
      const results = {};
      for (const entity of entities) {
        const response = await api.get(`/api/${entity}`);
        results[entity] = response.data;
      }
      setData(results);
    } catch (err) {
      handleApiError(err);
    }
  };

  const handleCancel = () => {
    setEditingItem(null);
    setAddingNew(false);
    setFormData({});
  };

  // --- ticket logic ---
  const handleTicketAdd = async () => {
    setAddingNew(true);
    setEditingItem(null);
    const today = new Date().toISOString().slice(0, 10);
    await fetchRelatedLists();

    setFormData({
      purchaseDate: today,
      event: '',
      customer: '',
      ticketType: '',
      employee: '', // add employee if you want
    });
  };

  const handleTicketEdit = async (item) => {
    setEditingItem(item);
    setAddingNew(false);
    await fetchRelatedLists();

    setFormData({
      purchaseDate: item.purchaseDate ? item.purchaseDate.substring(0, 10) : '',
      event: item.event?.id || '',
      customer: item.customer?.id || '',
      ticketType: item.ticketType?.id || '',
      employee: item.employee?.id || '',
    });
  };


  const handleTicketDelete = async (item) => {
    if (window.confirm(`Delete this ticket?`)) {
      try {
        await api.delete(`/api/tickets/${item.id}`);
        alert(`Deleted ticket with id ${item.id}`);
        fetchAllData();
      } catch (err) {
        handleApiError(err);
      }
    }
  };

  const handleTicketSave = async () => {
      try {
        // Validate required relations before sending
        if (!formData.event || !formData.customer || !formData.ticketType) {
          alert('Event, Customer, and Ticket Type are required');
          return;
        }
        
        var custId = api.get(`/api/customers/${formData.customer.id}`)
        var eventId = api.get(`/api/events/${formData.event.id}`)
        var ticketTypeId = api.get(`/api/ticket-types/${formData.ticketType.id}`)
        var employeeId = api.get(`/api/employees/${formData.employee.id}`)
        const payload = {
          purchaseDate: formData.purchaseDate,
          event: { eventId },
          customer: { custId },
          ticketType: { ticketTypeId },
          // Only add employee if it exists and is valid number
          employee: { employeeId }
        };
      
        if (editingItem) {
          await api.put(`/api/tickets/${editingItem.id}`, payload);
          alert('Ticket updated!');
        } else {
          await api.post('/api/tickets', payload);
          alert('Ticket created!');
        }
        fetchAllData();
        handleCancel();
      } catch (err) {
        handleApiError(err);
      }
    };


  const handlePrintTicket = async (ticket) => {
    try {
      if (!ticket.id) return alert('Ticket ID missing');
      const res = await api.get(`/api/tickets/${ticket.id}/print`);
      const printData = res.data;
      if (!printData) return alert('Failed to fetch ticket print data.');
      generateTicketPdf(ticket, printData);
    } catch (err) {
      console.error('Error printing ticket:', err);
      alert('Failed to print ticket.');
    }
  };

  const generateTicketPdf = (ticket, printData) => {
    const trimDate = (d) => (d ? d.substring(0, 16).replace('T', ' ') : '');
    const pdf = new jsPDF({ orientation: 'landscape', unit: 'in', format: [5.63, 2.37] });
    const logo = new Image();
    logo.src = museumLogo;
    logo.onload = () => {
      pdf.setFillColor('#f7ead9');
      pdf.rect(0, 0, 5.63, 2.37, 'F');
      pdf.addImage(logo, 'JPG', 0.1, 0.1, 0.8, 0.8);
      pdf.setFontSize(18).setFont('helvetica', 'bold');
      pdf.text('NotSoReal Museum', 1, 0.6);
      pdf.setFontSize(22);
      pdf.text(`${printData.customerName} ${printData.customerLastname}`, 0.5, 1.4);
      pdf.setFontSize(12);
      pdf.text(`${printData.eventName} ${trimDate(printData.eventDate)}`, 0.5, 1.8);
      pdf.setFontSize(10);
      pdf.text(`${ticket.ticketType} $${ticket.price} ${trimDate(printData.purchaseDate)}`, 0.5, 2.2);
      pdf.save(`ticket_${ticket.id}.pdf`);
      alert('Ticket downloaded as PDF!');
    };
    logo.onerror = () => alert('Failed to load museum logo image.');
  };
  // --- ticket logic ends ---

  // --- customer logic ---
  const handleCustomerEdit = async (item) => {
    try {
      // Fetch fresh customer data (enforces user restrictions)
      const res = await api.get(`/api/customers/${item.id}`);
      setEditingItem(res.data);
      setAddingNew(false);
      setFormData(res.data);
    } catch (err) {
      if (err.response?.status === 403) {
        alert('Access denied: You cannot edit this customer.');
      } else {
        handleApiError(err);
      }
    }
  };

  const handleCustomerAdd = async () => {
    setAddingNew(true);
    setEditingItem(null);
    await fetchRelatedLists();
    setFormData({});
  };

  const handleCustomerDelete = async (item) => {
    if (window.confirm(`Delete this customer?`)) {
      try {
        await api.delete(`/api/customers/${item.id}`);
        alert(`Deleted customer with id ${item.id}`);
        fetchAllData();
      } catch (err) {
        if (err.response?.status === 403) {
          alert('Access denied: You cannot delete this customer.');
        } else {
          handleApiError(err);
        }
      }
    }
  };

  const handleCustomerSave = async () => {
    try {
      if (editingItem) {
        // PUT requires ID and user must match
        const res = await api.put(`/api/customers/${editingItem.id}`, formData);
        alert('Customer updated!');
      } else {
        // POST to create a new customer
        const res = await api.post('/api/customers', formData);
        alert('Customer created!');
      }
      fetchAllData();
      handleCancel();
    } catch (err) {
      if (err.response?.status === 403) {
        alert('Access denied: You cannot modify this customer.');
      } else {
        handleApiError(err);
      }
    }
  };

  // --- customer logic ends ---

  // --- event logic ---
  const handleEventEdit = (item) => {
    setEditingItem(item);
    setAddingNew(false);
    setFormData(item);
  };

  const handleEventAdd = async () => {
    setAddingNew(true);
    setEditingItem(null);
    await fetchRelatedLists();
    setFormData({});
  };

  const handleEventDelete = async (item) => {
    if (window.confirm(`Delete this event?`)) {
      try {
        await api.delete(`/api/events/${item.id}`);
        alert(`Deleted event with id ${item.id}`);
        fetchAllData();
      } catch (err) {
        handleApiError(err);
      }
    }
  };

  const handleEventSave = async () => {
    try {
      if (editingItem) {
        await api.put(`/api/events/${editingItem.id}`, formData);
        alert('Event updated!');
      } else {
        await api.post('/api/events', formData);
        alert('Event created!');
      }
      fetchAllData();
      handleCancel();
    } catch (err) {
      handleApiError(err);
    }
  };
  // --- event logic ends ---

  // --- city logic ---
  const handleCityEdit = (item) => {
    setEditingItem(item);
    setAddingNew(false);
    setFormData(item);
  };

  const handleCityAdd = async () => {
    setAddingNew(true);
    setEditingItem(null);
    await fetchRelatedLists();
    setFormData({});
  };

  const handleCityDelete = async (item) => {
    if (window.confirm(`Delete this city?`)) {
      try {
        await api.delete(`/api/cities/${item.id}`);
        alert(`Deleted city with id ${item.id}`);
        fetchAllData();
      } catch (err) {
        handleApiError(err);
      }
    }
  };

  const handleCitySave = async () => {
    try {
      if (editingItem) {
        await api.put(`/api/cities/${editingItem.id}`, formData);
        alert('City updated!');
      } else {
        await api.post('/api/cities', formData);
        alert('City created!');
      }
      fetchAllData();
      handleCancel();
    } catch (err) {
      handleApiError(err);
    }
  };
  // --- city logic ends ---

  // --- post logic ---
  const handlePostEdit = (item) => {
    setEditingItem(item);
    setAddingNew(false);
    setFormData(item);
  };

  const handlePostAdd = async () => {
    setAddingNew(true);
    setEditingItem(null);
    await fetchRelatedLists();
    setFormData({});
  };

  const handlePostDelete = async (item) => {
    if (window.confirm(`Delete this post?`)) {
      try {
        await api.delete(`/api/posts/${item.id}`);
        alert(`Deleted post with id ${item.id}`);
        fetchAllData();
      } catch (err) {
        handleApiError(err);
      }
    }
  };

  const handlePostSave = async () => {
    try {
      if (editingItem) {
        await api.put(`/api/posts/${editingItem.id}`, formData);
        alert('Post updated!');
      } else {
        await api.post('/api/posts', formData);
        alert('Post created!');
      }
      fetchAllData();
      handleCancel();
    } catch (err) {
      handleApiError(err);
    }
  };
  // --- post logic ends ---

  // --- author logic ---
  const handleAuthorEdit = (item) => {
    setEditingItem(item);
    setAddingNew(false);
    setFormData(item);
  };

  const handleAuthorAdd = async () => {
    setAddingNew(true);
    setEditingItem(null);
    await fetchRelatedLists();
    setFormData({});
  };

  const handleAuthorDelete = async (item) => {
    if (window.confirm(`Delete this author?`)) {
      try {
        await api.delete(`/api/authors/${item.id}`);
        alert(`Deleted author with id ${item.id}`);
        fetchAllData();
      } catch (err) {
        handleApiError(err);
      }
    }
  };

  const handleAuthorSave = async () => {
    try {
      if (editingItem) {
        await api.put(`/api/authors/${editingItem.id}`, formData);
        alert('Author updated!');
      } else {
        await api.post('/api/authors', formData);
        alert('Author created!');
      }
      fetchAllData();
      handleCancel();
    } catch (err) {
      handleApiError(err);
    }
  };
  // --- author logic ends ---

  const handleAddClick = () => {
    switch (activeTab) {
      case 'tickets':
        return handleTicketAdd();
      case 'customers':
        return handleCustomerAdd();
      case 'events':
        return handleEventAdd();
      case 'cities':
        return handleCityAdd();
      case 'posts':
        return handlePostAdd();
      case 'authors':
        return handleAuthorAdd();
      default:
        return;
    }
  };

  const handleEditClick = (item) => {
    switch (activeTab) {
      case 'tickets':
        return handleTicketEdit(item);
      case 'customers':
        return handleCustomerEdit(item);
      case 'events':
        return handleEventEdit(item);
      case 'cities':
        return handleCityEdit(item);
      case 'posts':
        return handlePostEdit(item);
      case 'authors':
        return handleAuthorEdit(item);
      default:
        return;
    }
  };

  const handleDeleteClick = (item) => {
    switch (activeTab) {
      case 'tickets':
        return handleTicketDelete(item);
      case 'customers':
        return handleCustomerDelete(item);
      case 'events':
        return handleEventDelete(item);
      case 'cities':
        return handleCityDelete(item);
      case 'posts':
        return handlePostDelete(item);
      case 'authors':
        return handleAuthorDelete(item);
      default:
        return;
    }
  };

  // Form input rendering, improved for tickets with related selects:
  const renderFormInputs = () => {
    if (!formData) return null;

    // For tickets, render related selects for event, customer, ticketType

    if (activeTab === 'tickets') {
      return (
        <>
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Purchase Date: </label>
            <input
              type="date"
              value={formData.purchaseDate ? formData.purchaseDate.slice(0, 10) : ''}
              onChange={(e) => setFormData({ ...formData, purchaseDate: e.target.value })}
            />
          </div>
      
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Customer: </label>
            <select
              value={formData.customer || ''}
              onChange={(e) => setFormData({ ...formData, customer: e.target.value })}
            >
              <option value="">Select customer</option>
              {(relatedLists.customer || []).map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>
            
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Event: </label>
            <select
              value={formData.event || ''}
              onChange={(e) => setFormData({ ...formData, event: e.target.value })}
            >
              <option value="">Select event</option>
              {(relatedLists.event || []).map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>
            
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Ticket Type: </label>
            <select
              value={formData.ticketType || ''}
              onChange={(e) => setFormData({ ...formData, ticketType: e.target.value })}
            >
              <option value="">Select ticket type</option>
              {(relatedLists.ticketType || []).map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>
            
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Employee: </label>
            <select
              value={formData.employee || ''}
              onChange={(e) => setFormData({ ...formData, employee: e.target.value })}
            >
              <option value="">Select employee (optional)</option>
              {(relatedLists.employee || []).map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>
        </>
      );
    }

    if (activeTab === 'customers') {
    return (
      <>
        <div style={{ marginBottom: '0.5rem' }}>
          <label>First Name: </label>
          <input
            type="text"
            value={formData.firstName || ''}
            onChange={(e) => setFormData({ ...formData, firstName: e.target.value })}
            placeholder="Enter first name"
          />
        </div>

        <div style={{ marginBottom: '0.5rem' }}>
          <label>Last Name: </label>
          <input
            type="text"
            value={formData.lastName || ''}
            onChange={(e) => setFormData({ ...formData, lastName: e.target.value })}
            placeholder="Enter last name"
          />
        </div>

        <div style={{ marginBottom: '0.5rem' }}>
          <label>Email: </label>
          <input
            type="email"
            value={formData.email || ''}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
            placeholder="Enter email"
          />
        </div>

        <div style={{ marginBottom: '0.5rem' }}>
          <label>Birth Date: </label>
          <input
            type="date"
            value={formData.birthDate ? formData.birthDate.slice(0, 10) : ''}
            onChange={(e) => setFormData({ ...formData, birthDate: e.target.value })}
          />
        </div>

        <div style={{ marginBottom: '0.5rem' }}>
          <label>Telegram User ID: </label>
          <input
            type="text"
            value={formData.telegramUserId || ''}
            onChange={(e) => setFormData({ ...formData, telegramUserId: e.target.value })}
            placeholder="Enter Telegram User ID"
          />
        </div>

        <div style={{ marginBottom: '0.5rem' }}>
          <label>Telegram Username: </label>
          <input
            type="text"
            value={formData.telegramUsername || ''}
            onChange={(e) => setFormData({ ...formData, telegramUsername: e.target.value })}
            placeholder="Enter Telegram Username"
          />
        </div>
      </>
    );
    } 

    if (activeTab === 'posts') {
      return (
        <>
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Title: </label>
            <input
              type="text"
              value={formData.title || ''}
              onChange={(e) => setFormData({ ...formData, title: e.target.value })}
            />
          </div>

          <div style={{ marginBottom: '0.5rem' }}>
            <label>Content: </label>
            <textarea
              value={formData.content || ''}
              onChange={(e) => setFormData({ ...formData, content: e.target.value })}
              rows={5}
              cols={40}
            />
          </div>

          <div style={{ marginBottom: '0.5rem' }}>
            <label>Author: </label>
            <select
              value={formData.authorId || ''}
              onChange={(e) => setFormData({ ...formData, authorId: e.target.value })}
            >
              <option value="">Select author</option>
              {(relatedLists.authorId || []).map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>
        </>
      );
    }

    if (activeTab === 'events') {
      return (
        <>
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Name: </label>
            <input
              type="text"
              value={formData.name || ''}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              placeholder="Enter event name"
            />
          </div>
      
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Description: </label>
            <textarea
              value={formData.description || ''}
              onChange={(e) => setFormData({ ...formData, description: e.target.value })}
              placeholder="Enter description"
            />
          </div>
      
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Start Date: </label>
            <input
              type="datetime-local"
              value={formData.startDate ? formData.startDate.slice(0, 16) : ''}
              onChange={(e) => setFormData({ ...formData, startDate: e.target.value })}
            />
          </div>
      
          <div style={{ marginBottom: '0.5rem' }}>
            <label>End Date: </label>
            <input
              type="datetime-local"
              value={formData.endDate ? formData.endDate.slice(0, 16) : ''}
              onChange={(e) => setFormData({ ...formData, endDate: e.target.value })}
            />
          </div>
      
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Capacity: </label>
            <input
              type="number"
              value={formData.capacity || ''}
              onChange={(e) => setFormData({ ...formData, capacity: e.target.value })}
              placeholder="Enter capacity"
            />
          </div>
      
          <div style={{ marginBottom: '0.5rem' }}>
            <label>Hall: </label>
            <select
              value={formData.hallId || ''}
              onChange={(e) => setFormData({ ...formData, hallId: e.target.value })}
            >
              <option value="">Select hall</option>
              {(relatedLists.hall || []).map((opt) => (
                <option key={opt.value} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>
        </>
      );
    }

    // For other entities, render simple text inputs for each key
    return Object.keys(formData).map((key) => {
      if (typeof formData[key] === 'object') return null; // skip objects to avoid complexity
      return (
        <div key={key} style={{ marginBottom: '0.5rem' }}>
          <label>{key}: </label>
          <input
            type="text"
            value={formData[key] || ''}
            onChange={(e) => setFormData({ ...formData, [key]: e.target.value })}
          />
        </div>
      );
    });
  };

  const handleLogout = () => {
    clearCookie('access_token');
    navigate('/');
  };

  return (
    <div className="admin-page">
      <h1>Admin Panel</h1>
      <button onClick={handleLogout} style={{ marginBottom: '1rem' }}>
        Logout
      </button>
      <div className="tabs" style={{ marginBottom: '1rem' }}>
        {entities.map((entity) => (
          <button
            key={entity}
            onClick={() => {
              setActiveTab(entity);
              handleCancel();
            }}
            style={{
              marginRight: '0.5rem',
              fontWeight: activeTab === entity ? 'bold' : 'normal',
            }}
          >
            {entity.charAt(0).toUpperCase() + entity.slice(1)}
          </button>
        ))}
      </div>

      {!addingNew && !editingItem && (
        <>
          <button onClick={handleAddClick} style={{ marginBottom: '1rem' }}>
            Add {activeTab.slice(0, -1)}
          </button>
          <table border="1" cellPadding="4" style={{ width: '100%', borderCollapse: 'collapse' }}>
            <thead>
              <tr>
                {data[activeTab] && data[activeTab].length > 0
                  ? Object.keys(data[activeTab][0]).map((key) => (
                      <th key={key}>{key}</th>
                    ))
                  : null}
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {data[activeTab] && data[activeTab].length > 0 ? (
                data[activeTab].map((item) => (
                  <tr key={item.id || JSON.stringify(item)}>
                    {Object.keys(item).map((key) => {
                      let val = item[key];
                      if (val && typeof val === 'object') {
                        // Try to display relevant subfield
                        if (val.name) val = val.name;
                        else if (val.title) val = val.title;
                        else val = JSON.stringify(val);
                      }
                      return <td key={key}>{val}</td>;
                    })}
                    <td>
                      <button onClick={() => handleEditClick(item)}>Edit</button>
                      <button onClick={() => handleDeleteClick(item)}>Delete</button>
                      {activeTab === 'tickets' && (
                        <button onClick={() => handlePrintTicket(item)}>Print</button>
                      )}
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={100}>No data</td>
                </tr>
              )}
            </tbody>
          </table>
        </>
      )}

      {(addingNew || editingItem) && (
        <div style={{ marginTop: '1rem' }}>
          <h2>{addingNew ? `Add New ${activeTab.slice(0, -1)}` : `Edit ${activeTab.slice(0, -1)}`}</h2>
          <form
            onSubmit={(e) => {
              e.preventDefault();
              switch (activeTab) {
                case 'tickets':
                  return handleTicketSave();
                case 'customers':
                  return handleCustomerSave();
                case 'events':
                  return handleEventSave();
                case 'cities':
                  return handleCitySave();
                case 'posts':
                  return handlePostSave();
                case 'authors':
                  return handleAuthorSave();
                default:
                  return;
              }
            }}
          >
            {renderFormInputs()}

            <div style={{ marginTop: '1rem' }}>
              <button type="submit">Save</button>
              <button type="button" onClick={handleCancel} style={{ marginLeft: '1rem' }}>
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default AdminPage;
