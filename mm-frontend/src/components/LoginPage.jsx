import React, { useState } from 'react';
import api from '../api/api.js';
import { useNavigate } from 'react-router-dom';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [code, setCode] = useState('');
  const [error, setError] = useState('');
  const [step, setStep] = useState(1);
  const navigate = useNavigate();

  const handleSendEmail = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await api.post('/api/auth/login', { email });  // send email in body
      setStep(2);
    } catch (err) {
      setError('Failed to send code. Please check the email and try again.');
    }
  };

  const handleVerifyCode = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await api.post('/api/auth/verify', { email, code });  // send email + code in body
      // Cookie JSESSIONID should be set automatically by browser now
      navigate('/admin');  // go to admin page
    } catch (err) {
      setError('Invalid code or email. Please try again.');
    }
  };

  return (
    <div>
      <h2>Admin Login</h2>

      {step === 1 && (
        <form onSubmit={handleSendEmail}>
          <input
            type="email"
            placeholder="Email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
          />
          <button type="submit">Send Code</button>
        </form>
      )}

      {step === 2 && (
        <form onSubmit={handleVerifyCode}>
          <input
            type="email"
            value={email}
            readOnly
          />
          <input
            type="text"
            placeholder="Verification Code"
            value={code}
            onChange={e => setCode(e.target.value)}
            required
          />
          <button type="submit">Verify & Login</button>
        </form>
      )}

      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

export default LoginPage;
