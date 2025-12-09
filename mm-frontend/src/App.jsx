import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import Dashboard from './components/AdminPanel/Dashboard';

import CityList from './components/City/CityList';
import AuthorList from './components/Author/AuthorList';
import AddressList from './components/Address/AddressList';
import TicketList from './components/Ticket/TicketList';
import CountryList from './components/Country/CountryList';
import TicketTypeList from './components/TicketType/TicketTypeList';
import CustomerList from './components/Customer/CustomerList';
import EmployeeList from './components/Employee/EmployeeList';
import EventList from './components/Event/EventList';
import ExhibitList from './components/Exhibit/ExhibitList';
import HallList from './components/Hall/HallList';
import ItemList from './components/Item/ItemList';
import FileList from './components/Files/FileList';
import AddressForm from './components/Address/AddressForm';
import AuthorForm from './components/Author/AuthorForm';
import CityForm from './components/City/CityForm';
import CountryForm from './components/Country/CountryForm';
import TicketForm from './components/Ticket/TicketForm';
import TicketTypeForm from './components/TicketType/TicketTypeForm';
import CustomerForm from './components/Customer/CustomerForm';
import EmployeeForm from './components/Employee/EmployeeForm';
import EventForm from './components/Event/EventForm';
import ExhibitForm from './components/Exhibit/ExhibitForm';
import HallForm from './components/Hall/HallForm';
import ItemForm from './components/Item/ItemForm';
import FileUploadForm from './components/Files/FileUploadForm';

import MuseumApp from './components/MuseumApp';

function App() {
  return (
    <Router>
      <Routes>

        {/* Redirect root to dashboard */}
        <Route path="/" element={<Navigate to="/dashboard" />} />

        {/* Separate route for Museum frontend */}
        <Route path="/museum" element={<MuseumApp />} />

        {/* Admin dashboard routes */}
        <Route path="/dashboard" element={<Dashboard />}>

          {/* Dashboard welcome */}
          <Route index element={<div>Welcome to the Museum Admin Panel!</div>} />

          {/* Entities */}
          <Route path="/dashboard/cities" element={<CityList />} />
          <Route path="/dashboard/cities/new" element={<CityForm />} />
          <Route path="/dashboard/authors" element={<AuthorList />} />
          <Route path="/dashboard/authors/new" element={<AuthorForm />} />
          <Route path="/dashboard/addresses" element={<AddressList />} />
          <Route path="/dashboard/addresses/new" element={<AddressForm />} />
          <Route path="/dashboard/countries" element={<CountryList />} />
          <Route path="/dashboard/countries/new" element={<CountryForm />} />
          <Route path="/dashboard/tickets" element={<TicketList />} />
          <Route path="/dashboard/tickets/new" element={<TicketForm />} />
          <Route path="/dashboard/ticket-types" element={<TicketTypeList />} />
          <Route path="/dashboard/ticket-types/new" element={<TicketTypeForm />} />
          <Route path="/dashboard/customers" element={<CustomerList />} />
          <Route path="/dashboard/customers/new" element={<CustomerForm />} />
          <Route path="/dashboard/employees" element={<EmployeeList />} />
          <Route path="/dashboard/employees/new" element={<EmployeeForm />} />
          <Route path="/dashboard/events" element={<EventList />} />
          <Route path="/dashboard/events/new" element={<EventForm />} />
          <Route path="/dashboard/exhibits" element={<ExhibitList />} />
          <Route path="/dashboard/exhibits/new" element={<ExhibitForm />} />
          <Route path="/dashboard/halls" element={<HallList />} />
          <Route path="/dashboard/halls/new" element={<HallForm />} />
          <Route path="/dashboard/items" element={<ItemList />} />
          <Route path="/dashboard/items/new" element={<ItemForm />} />
          <Route path="/dashboard/files" element={<FileList />} />
          <Route path="/dashboard/files/new" element={<FileUploadForm />} />

        </Route>
      </Routes>
    </Router>
  );
}

export default App;
