import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axiosConfig';
import { Container, Card, Form, Button, Alert, Tab, Tabs } from 'react-bootstrap';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [role, setRole] = useState('STUDENT');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');

        const apiRole = role === 'ADMIN' ? 'STAFF' : role;

        try {
            const response = await api.post('/auth/login', {
                username,
                password,
                role: apiRole
            });

            const user = response.data;
            localStorage.setItem('user', JSON.stringify(user));
            localStorage.setItem('role', role);

            // Routing Logic
            if (role === 'STUDENT') {
                navigate('/student-dashboard');
            } else if (role === 'ADMIN') {
                if (user.role === 'ADMIN') {
                    navigate('/admin-dashboard');
                } else {
                    setError('Access Denied: You are not an Admin.');
                }
            } else {
                navigate('/staff-dashboard');
            }
        } catch (err) {
            setError('Invalid credentials. Please check your details.');
        }
    };

    return (
        <Container className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
            <Card style={{ width: '400px' }} className="p-4">
                <div className="text-center mb-4">
                    <h3 className="text-cput fw-bold">Login</h3>
                    <p className="text-muted">Clinical Placement System</p>
                </div>

                {error && <Alert variant="danger">{error}</Alert>}

                <Tabs activeKey={role} onSelect={(k) => setRole(k)} className="mb-3" fill>
                    <Tab eventKey="STUDENT" title="Student" />
                    <Tab eventKey="STAFF" title="Staff" />
                    <Tab eventKey="ADMIN" title="Admin" />
                </Tabs>

                <Form onSubmit={handleLogin}>
                    <Form.Group className="mb-3">
                        <Form.Label>{role === 'STUDENT' ? 'Student Number' : 'Email'}</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder={role === 'STUDENT' ? "Enter student no" : "Enter email"}
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Form.Group className="mb-4">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Enter password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </Form.Group>

                    <Button className="w-100 btn-cput" type="submit">
                        Login
                    </Button>
                </Form>

                {role === 'STUDENT' && (
                    <div className="text-center mt-3">
                        <small>New here? <a href="/register">Register Student</a></small>
                    </div>
                )}
            </Card>
        </Container>
    );
};

export default Login;