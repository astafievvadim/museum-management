import React, { useEffect, useState } from 'react';
import { getAllEmployees, deleteEmployee } from '../../api/api';

const EmployeeList = () => {
  const [employees, setEmployees] = useState([]);

  const fetchEmployees = async () => {
    try {
      const data = await getAllEmployees();
      setEmployees(data);
    } catch (err) {
      console.error(err);
      alert('Failed to fetch employees.');
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this employee?')) return;
    try {
      await deleteEmployee(id);
      fetchEmployees();
    } catch (err) {
      console.error(err);
      alert('Failed to delete employee.');
    }
  };

  useEffect(() => {
    fetchEmployees();
  }, []);

  return (
    <div className="employee-list">
      <h2>Employees</h2>
      <ul>
        {employees.map(e => (
          <li key={e.id}>
            {e.firstName} {e.lastName} ({e.role})
            <button onClick={() => handleDelete(e.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default EmployeeList;
