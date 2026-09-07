import { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import Navigation from '../components/Navigation';
import { Table, Badge, Tabs, Tab, Alert } from 'react-bootstrap';

const StaffDashboard = () => {
    const user = JSON.parse(localStorage.getItem('user'));
    const [students, setStudents] = useState([]);
    const [incidents, setIncidents] = useState([]);
    const [placements, setPlacements] = useState([]);

    useEffect(() => {
        const fetchAll = async () => {
            try {
                // Fetch independent lists securely
                const sRes = await api.get('/student');
                setStudents(sRes.data || []);

                const iRes = await api.get('/incident');
                setIncidents(iRes.data || []);

                const pRes = await api.get('/placement');
                setPlacements(pRes.data || []);
            } catch (error) {
                console.error("Error loading staff dashboard", error);
            }
        };
        fetchAll();
    }, []);

    const getStudentPlacement = (studentId) => {
        const p = placements.find(pl => pl.student?.studentId === studentId);
        return p ? p.facility?.name : "Not Assigned";
    };

    return (
        <>
            <Navigation user={user} role="STAFF" />
            <div className="dashboard-container">
                <h2 className="mb-4 text-cput">Staff Dashboard</h2>
                <p>Welcome, {user.name}</p>

                <Tabs defaultActiveKey="students" className="mb-3">
                    <Tab eventKey="students" title="Student Overview">
                        <Table striped hover responsive>
                            <thead><tr><th>Number</th><th>Name</th><th>Email</th><th>Assigned Facility</th><th>Status</th></tr></thead>
                            <tbody>
                            {students.map(s => (
                                <tr key={s.studentId}>
                                    <td>{s.studentNumber}</td>
                                    <td>{s.firstName} {s.lastName}</td>
                                    <td>{s.email}</td>
                                    <td><Badge bg="primary">{getStudentPlacement(s.studentId)}</Badge></td>
                                    <td><Badge bg={s.status === 'ACTIVE' ? 'success' : 'danger'}>{s.status}</Badge></td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                    </Tab>
                    <Tab eventKey="incidents" title="Incident Reports">
                        <Table striped hover responsive>
                            <thead><tr><th>Date</th><th>Student</th><th>Description</th><th>Status</th></tr></thead>
                            <tbody>
                            {incidents.map(i => (
                                <tr key={i.incidentId}>
                                    <td>{i.date}</td>
                                    <td>{i.student?.firstName} {i.student?.lastName}</td>
                                    <td>{i.description}</td>
                                    <td><Badge bg={i.status === 'OPEN' ? 'danger' : 'success'}>{i.status}</Badge></td>
                                </tr>
                            ))}
                            {incidents.length === 0 && <tr><td colSpan="4" className="text-center">No incidents reported.</td></tr>}
                            </tbody>
                        </Table>
                    </Tab>
                </Tabs>
            </div>
        </>
    );
};

export default StaffDashboard;