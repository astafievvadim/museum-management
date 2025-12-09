import api from 'axios';

const BASE_URL = 'http://localhost:8080/api';

// --- ADDRESS ---

export const getAddresses = async () => {
  const res = await api.get(`${BASE_URL}/addresses`);
  return res.data;
};

export const getAddressById = async (id) => {
  const res = await api.get(`${BASE_URL}/addresses/${id}`);
  return res.data;
};

export const createAddress = async (address) => {
  const res = await api.post(`${BASE_URL}/addresses`, address);
  return res.data;
};

export const updateAddress = async (id, address) => {
  const res = await api.put(`${BASE_URL}/addresses/${id}`, address);
  return res.data;
};

export const deleteAddress = async (id) => {
  await api.delete(`${BASE_URL}/addresses/${id}`);
};

// --- AUTHOR ---

export const getAuthors = async () => {
  const res = await api.get(`${BASE_URL}/authors`);
  return res.data;
};

export const getAuthorById = async (id) => {
  const res = await api.get(`${BASE_URL}/authors/${id}`);
  return res.data;
};

export const createAuthor = async (author) => {
  const res = await api.post(`${BASE_URL}/authors`, author);
  return res.data;
};

export const updateAuthor = async (id, author) => {
  const res = await api.put(`${BASE_URL}/authors/${id}`, author);
  return res.data;
};

export const deleteAuthor = async (id) => {
  await api.delete(`${BASE_URL}/authors/${id}`);
};

// --- CITY ---

export const getCities = async () => {
  const res = await api.get(`${BASE_URL}/cities`);
  return res.data;
};

export const getCityById = async (id) => {
  const res = await api.get(`${BASE_URL}/cities/${id}`);
  return res.data;
};

export const createCity = async (city, countryId) => {
  const res = await api.post(`${BASE_URL}/cities?countryId=${countryId}`, city);
  return res.data;
};

export const updateCity = async (id, city) => {
  const res = await api.put(`${BASE_URL}/cities/${id}`, city);
  return res.data;
};

export const deleteCity = async (id) => {
  await api.delete(`${BASE_URL}/cities/${id}`);
};

// --- COUNTRY ---

export const getCountries = async () => {
  const res = await api.get(`${BASE_URL}/countries`);
  return res.data;
};

export const getCountryById = async (id) => {
  const res = await api.get(`${BASE_URL}/countries/${id}`);
  return res.data;
};

export const createCountry = async (country) => {
  const res = await api.post(`${BASE_URL}/countries`, country);
  return res.data;
};

export const updateCountry = async (id, country) => {
  const res = await api.put(`${BASE_URL}/countries/${id}`, country);
  return res.data;
};

export const deleteCountry = async (id) => {
  await api.delete(`${BASE_URL}/countries/${id}`);
};

// --- TICKET ---

export const getTickets = async () => {
  const res = await api.get(`${BASE_URL}/tickets`);
  return res.data;
};

export const getTicketById = async (id) => {
  const res = await api.get(`${BASE_URL}/tickets/${id}`);
  return res.data;
};

export const getTicketForPrint = async (id) => {
  const res = await api.get(`${BASE_URL}/tickets/${id}/print`);
  return res.data;
};

export const createTicket = async (ticket) => {
  const res = await api.post(`${BASE_URL}/tickets`, ticket);
  return res.data;
};

export const updateTicket = async (id, ticket) => {
  const res = await api.put(`${BASE_URL}/tickets/${id}`, ticket);
  return res.data;
};

export const deleteTicket = async (id) => {
  await api.delete(`${BASE_URL}/tickets/${id}`);
};

// --- CUSTOMER ---

export const getAllCustomers = async () => {
  const res = await api.get(`${BASE_URL}/customers`);
  return res.data;
};

export const getCustomerById = async (id) => {
  const res = await api.get(`${BASE_URL}/customers/${id}`);
  return res.data;
};

export const createCustomer = async (customer) => {
  const res = await api.post(`${BASE_URL}/customers`, customer);
  return res.data;
};

export const updateCustomer = async (id, customer) => {
  const res = await api.put(`${BASE_URL}/customers/${id}`, customer);
  return res.data;
};

export const deleteCustomer = async (id) => {
  const res = await api.delete(`${BASE_URL}/customers/${id}`);
  return res.data;
};

// --- EMPLOYEE

export const getAllEmployees = async () => {
  const res = await api.get(`${BASE_URL}/employees`);
  return res.data;
};

export const getEmployeeById = async (id) => {
  const res = await api.get(`${BASE_URL}/employees/${id}`);
  return res.data;
};

export const createEmployee = async (employee) => {
  const res = await api.post(`${BASE_URL}/employees`, employee);
  return res.data;
};

export const updateEmployee = async (id, employee) => {
  const res = await api.put(`${BASE_URL}/employees/${id}`, employee);
  return res.data;
};

export const deleteEmployee = async (id) => {
  const res = await api.delete(`${BASE_URL}/employees/${id}`);
  return res.data;
};


// --- EVENT 

export const getAllEvents = async () => {
  const res = await api.get(`${BASE_URL}/events`);
  return res.data;
};

export const getEventById = async (id) => {
  const res = await api.get(`${BASE_URL}/events/${id}`);
  return res.data;
};

export const createEvent = async (event) => {
  const res = await api.post(`${BASE_URL}/events`, event);
  return res.data;
};

export const updateEvent = async (id, event) => {
  const res = await api.put(`${BASE_URL}/events/${id}`, event);
  return res.data;
};

export const deleteEvent = async (id) => {
  const res = await api.delete(`${BASE_URL}/events/${id}`);
  return res.data;
};

// --- EXHIBIT

export const getAllExhibits = async () => {
  const res = await api.get(`${BASE_URL}/exhibits`);
  return res.data;
};

export const getExhibitById = async (id) => {
  const res = await api.get(`${BASE_URL}/exhibits/${id}`);
  return res.data;
};

export const createExhibit = async (exhibit) => {
  const res = await api.post(`${BASE_URL}/exhibits`, exhibit);
  return res.data;
};

export const updateExhibit = async (id, exhibit) => {
  const res = await api.put(`${BASE_URL}/exhibits/${id}`, exhibit);
  return res.data;
};

export const deleteExhibit = async (id) => {
  const res = await api.delete(`${BASE_URL}/exhibits/${id}`);
  return res.data;
};

// --- FILE ---

export async function uploadFile(file, itemId) {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('itemId', itemId);

  const response = await fetch(`${BASE_URL}/files/upload`, {
    method: 'POST',
    body: formData,
  });

  if (!response.ok) {
    throw new Error('File upload failed');
  }

  return await response.json(); // returns updated Item object
}

export const getFileUrl = (filename) => {
  return `${BASE_URL}/files/${filename}`;
};

export const downloadFile = (filename) => {
  const url = getFileUrl(filename);
  window.open(url, '_blank');
};

export const deleteFile = async (filename) => {
  const res = await api.delete(`${BASE_URL}/files/${filename}`);
  return res.data;
};

export const getFileDetails = async (filename) => {
  const res = await api.get(`${BASE_URL}/files/${filename}/details`);
  return res.data;
};

export async function replaceFile(file, itemId) {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('itemId', itemId);

  const response = await fetch(`${BASE_URL}/files/replace`, {
    method: 'PUT',
    body: formData,
  });

  if (!response.ok) {
    throw new Error('File replace failed');
  }

  return await response.json(); // returns FileResponse object
}


// --- HALL

export const fetchHalls = async () => {
  const res = await api.get(`${BASE_URL}/halls`);
  return res.data;
};

export const createHall = async (hall) => {
  const res = await api.post(`${BASE_URL}/halls`, hall);
  return res.data;
};

export const deleteHall = async (id) => {
  const res = await api.delete(`${BASE_URL}/halls/${id}`);
  return res.data;
};

// --- ITEM

export const fetchItems = async () => {
  try {
    const res = await api.get(`${BASE_URL}/items`);
    return res.data;
  } catch (e) {
    console.error(e);
    return [];
  }
};

export const fetchItem = async (id) => {
  try {
    const res = await api.get(`${BASE_URL}/items/${id}`);
    return res.data;
  } catch (e) {
    console.error(e);
    return null;
  }
};

export const createItem = async (item) => {
  const res = await api.post(`${BASE_URL}/items`, item);
  return res.data;
};

export const updateItem = async (id, item) => {
  console.log('Updating item:', id, item);
  const res = await api.put(`${BASE_URL}/items/${id}`, item);
  return res.data;
};

export const deleteItem = async (id) => {
  await api.delete(`${BASE_URL}/items/${id}`);
};


// --- PARTNER CONTRIBUTION

export const fetchPartnerContributions = async () => {
  const res = await api.get(`${BASE_URL}/partner-contributions`);
  return res.data;
};

export const getPartnerContributionById = async (id) => {
  const res = await api.get(`${BASE_URL}/partner-contributions/${id}`);
  return res.data;
};

export const createPartnerContribution = async (contribution) => {
  const res = await api.post(`${BASE_URL}/partner-contributions`, contribution);
  return res.data;
};

export const updatePartnerContribution = async (id, contribution) => {
  const res = await api.put(`${BASE_URL}/partner-contributions/${id}`, contribution);
  return res.data;
};

export const deletePartnerContribution = async (id) => {
  const res = await api.delete(`${BASE_URL}/partner-contributions/${id}`);
  return res.data;
};


// Additional endpoints for authors and fileMetadata (needed for dropdowns)

export const fetchAuthors = async () => {
  const res = await api.get(`${BASE_URL}/authors`);
  return res.data;
};

export const fetchFileMetadata= async (filename) => {
  const res = await api.get(`${BASE_URL}/files/${filename}/details`);
  return res.data;
};

export const getTicketTypes = async () => {
  const res = await api.get(`${BASE_URL}/ticket-types`);
  return res.data;
};

export const createTicketType = async (ticketType) => {
  const res = await api.post(`${BASE_URL}/ticket-types`, ticketType);
  return res.data;
};

export const updateTicketType = async (id, ticketType) => {
  const res = await api.put(`${BASE_URL}/ticket-types/${id}`, ticketType);
  return res.data;
};

export const deleteTicketType = async (id) => {
  await api.delete(`${BASE_URL}/ticket-types/${id}`);
};


export default api;
