import React from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from './Sidebar';
import './Dashboard.css';

function Dashboard() {
  return (
    <div className="dashboard">
      <Sidebar />
      <main className="dashboard-main">
        {/* Here nested routes with tabs (EntityForm, EntityList) will render */}
        <Outlet />
      </main>
    </div>
  );
}

export default Dashboard;
