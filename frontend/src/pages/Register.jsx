import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';
import { Container, Card, Form, Button, Alert } from 'react-bootstrap';

const Register = () => {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        studentNumber: '', firstName: '', lastName: '',
        email: '', password: '', yearLevel: 1, status: 'ACTIVE'
    });
    const [error, setError] = useState('');

    const handleChange = (e) => setFormData({...formData, [e.target.name]: e.target.value});

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/student', formData);
            alert("Registration successful! Please login.");
            navigate('/');
        } catch (err) {
            setError(err.response?.data || "Registration failed.");
        }
    };

    return (
        <Container className="d-flex justify-content-center align-items-center mt-5">
            <Card style={{ width: '500px' }} className="p-4">
                <h3 className="text-cput text-center mb-3">Student Registration</h3>
                {error && <Alert variant="danger">{error}</Alert>}
                <Form onSubmit={handleSubmit}>
                    <Form.Group className="mb-2"><Form.Control name="studentNumber" placeholder="Student Number" onChange={handleChange} required /></Form.Group>
                    <Form.Group className="mb-2"><Form.Control name="firstName" placeholder="First Name" onChange={handleChange} required /></Form.Group>
                    <Form.Group className="mb-2"><Form.Control name="lastName" placeholder="Last Name" onChange={handleChange} required /></Form.Group>
                    <Form.Group className="mb-2"><Form.Control name="email" type="email" placeholder="Email Address" onChange={handleChange} required /></Form.Group>
                    <Form.Group className="mb-2"><Form.Control name="password" type="password" placeholder="Password" onChange={handleChange} required /></Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Year Level</Form.Label>
                        <Form.Select name="yearLevel" onChange={handleChange}>
                            <option value="1">1st Year</option>
                            <option value="2">2nd Year</option>
                            <option value="3">3rd Year</option>
                            <option value="4">4th Year</option>
                        </Form.Select>
                    </Form.Group>
                    <Button className="w-100 btn-cput" type="submit">Register</Button>
                </Form>
            </Card>
        </Container>
    );
};

export default Register;