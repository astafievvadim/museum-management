import React, { useState, useEffect } from 'react';
import { createEmployee, updateEmployee, getEmployeeById } from '../../api/api';

const EmployeeForm = ({ existingEmployeeId, onSuccess }) => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    birthdate: '',
    email: '',
    role: 'ROLE_TICKET_AGENT', // default role
  });

  useEffect(() => {
    if (existingEmployeeId) {
      getEmployeeById(existingEmployeeId).then(employee => {
        setFormData({
          ...employee,
          birthdate: employee.birthdate ? employee.birthdate.substring(0, 10) : '',
        });
      });
    }
  }, [existingEmployeeId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (existingEmployeeId) {
        await updateEmployee(existingEmployeeId, formData);
      } else {
        await createEmployee(formData);
      }
      if (onSuccess) onSuccess();
    } catch (err) {
      console.error(err);
      alert('Failed to submit employee.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="employee-form">
      <input name="firstName" value={formData.firstName} onChange={handleChange} placeholder="First Name" required />
      <input name="lastName" value={formData.lastName} onChange={handleChange} placeholder="Last Name" required />
      <input type="date" name="birthdate" value={formData.birthdate} onChange={handleChange} placeholder="Birthdate" />
      <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Email" />
      <select name="role" value={formData.role} onChange={handleChange}>
        <option value="ROLE_ADMIN">Admin</option>
        <option value="ROLE_MANAGER">Manager</option>
        <option value="ROLE_TICKET_AGENT">Ticket Agent</option>
      </select>
      <button type="submit">{existingEmployeeId ? 'Update' : 'Create'} Employee</button>
    </form>
  );
};

export default EmployeeForm;
