import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

function Sidebar() {
  return (
    <div className="sidebar">
      <h2 className="sidebar-title">Admin Panel</h2>
      <nav className="sidebar-nav">
        <Link to="/dashboard" className="sidebar-link">Dashboard</Link>

        {/* Core Entities */}
        <Link to="/dashboard/addresses" className="sidebar-link">Addresses</Link>
        <Link to="/dashboard/authors" className="sidebar-link">Authors</Link>
        <Link to="/dashboard/cities" className="sidebar-link">Cities</Link>
        <Link to="/dashboard/countries" className="sidebar-link">Countries</Link>

        {/* Tickets */}
        <Link to="/dashboard/tickets" className="sidebar-link">Tickets</Link>
        <Link to="/dashboard/ticket-types" className="sidebar-link">Ticket Types</Link>

        {/* Users */}
        <Link to="/dashboard/customers" className="sidebar-link">Customers</Link>
        <Link to="/dashboard/employees" className="sidebar-link">Employees</Link>

         {/* Events & Exhibits  */}
        <Link to="/dashboard/events" className="sidebar-link">Events</Link>
        <Link to="/dashboard/exhibits" className="sidebar-link">Exhibits</Link>

        {/* Halls and Items */}
        <Link to="/dashboard/halls" className="sidebar-link">Halls</Link>
        <Link to="/dashboard/items" className="sidebar-link">Items</Link>

        {/* Files */}
        <Link to="/dashboard/files" className="sidebar-link">Files</Link>
      </nav>
    </div>
  );
}

export default Sidebar;