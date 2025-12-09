import React, { useState, useEffect } from 'react';
import { createCustomer, updateCustomer, getCustomerById } from '../../api/api';

const CustomerForm = ({ existingCustomerId, onSuccess }) => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    birthdate: '',
    email: '',
    telegramId: '',
    telegramUserId: '',
    telegramUsername: '',
    addressId: '',
  });

  useEffect(() => {
    if (existingCustomerId) {
      getCustomerById(existingCustomerId).then(customer => {
        setFormData({
          ...customer,
          birthdate: customer.birthdate ? customer.birthdate.substring(0, 10) : '',
        });
      });
    }
  }, [existingCustomerId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (existingCustomerId) {
        await updateCustomer(existingCustomerId, formData);
      } else {
        await createCustomer(formData);
      }
      if (onSuccess) onSuccess();
    } catch (err) {
      console.error(err);
      alert('Failed to submit customer.');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="customer-form">
      <input name="firstName" value={formData.firstName} onChange={handleChange} placeholder="First Name" required />
      <input name="lastName" value={formData.lastName} onChange={handleChange} placeholder="Last Name" required />
      <input type="date" name="birthdate" value={formData.birthdate} onChange={handleChange} placeholder="Birthdate" />
      <input type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Email" />
      <input name="telegramId" value={formData.telegramId} onChange={handleChange} placeholder="Telegram ID" />
      <input name="telegramUserId" value={formData.telegramUserId} onChange={handleChange} placeholder="Telegram User ID" />
      <input name="telegramUsername" value={formData.telegramUsername} onChange={handleChange} placeholder="Telegram Username" />
      <input name="addressId" value={formData.addressId} onChange={handleChange} placeholder="Address ID" />
      <button type="submit">{existingCustomerId ? 'Update' : 'Create'} Customer</button>
    </form>
  );
};

export default CustomerForm;
