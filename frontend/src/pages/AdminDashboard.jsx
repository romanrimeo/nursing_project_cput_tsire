import { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import Navigation from '../components/Navigation';
import { Table, Button, Tab, Tabs, Badge, Modal, Form, Alert, Card, Row, Col } from 'react-bootstrap';

const AdminDashboard = () => {
    const user = JSON.parse(localStorage.getItem('user'));

    // Data State
    const [students, setStudents] = useState([]);
    const [staff, setStaff] = useState([]);
    const [facilities, setFacilities] = useState([]);
    const [incidents, setIncidents] = useState([]);
    const [placements, setPlacements] = useState([]);

    // UI State
    const [msg, setMsg] = useState("");
    const [showStaffModal, setShowStaffModal] = useState(false);
    const [showStudentModal, setShowStudentModal] = useState(false);
    const [showAssignModal, setShowAssignModal] = useState(false);

    // Forms
    const [staffForm, setStaffForm] = useState({ name: '', email: '', password: '', role: 'CLINICAL_STAFF', yearLevelAssigned: 0 });
    const [studentForm, setStudentForm] = useState({ studentNumber: '', firstName: '', lastName: '', email: '', password: '', yearLevel: 1, status: 'ACTIVE' });
    const [assignForm, setAssignForm] = useState({ studentId: '', facilityId: '', startDate: '', endDate: '' });

    useEffect(() => {
        fetchAllData();
    }, []);

    const fetchAllData = () => {
        api.get('/student').then(res => setStudents(res.data)).catch(console.error);
        api.get('/staff').then(res => setStaff(res.data)).catch(console.error);
        // Ensure we hit the /all endpoint for facilities
        api.get('/facility/all').then(res => setFacilities(res.data)).catch(console.error);
        api.get('/incident').then(res => setIncidents(res.data)).catch(console.error);
        api.get('/placement').then(res => setPlacements(res.data)).catch(console.error);
    };

    const handleDeleteStaff = async (id) => {
        if(confirm("Delete this staff member?")) {
            try {
                await api.delete(`/staff/${id}`);
                setMsg("Staff deleted.");
                fetchAllData();
            } catch (err) { alert("Delete failed: " + err.message); }
        }
    };

    const handleDeleteStudent = async (studentNumber) => {
        if(confirm("Delete this student?")) {
            try {
                await api.delete(`/student/${studentNumber}`);
                setMsg("Student deleted.");
                fetchAllData();
            } catch (err) { alert("Delete failed."); }
        }
    };

    const handleCreateStudent = async () => {
        try {
            await api.post('/student', studentForm);
            setShowStudentModal(false);
            setMsg("Student added.");
            fetchAllData();
        } catch (err) { alert("Failed to add student."); }
    };

    const handleCreateStaff = async () => {
        try {
            await api.post('/staff', staffForm);
            setShowStaffModal(false);
            setMsg("Staff added.");
            fetchAllData();
        } catch (err) { alert("Failed to add staff."); }
    };

    const handleAssignStudent = async () => {
        try {
            await api.post('/placement', {
                student: { studentId: assignForm.studentId },
                facility: { facilityId: assignForm.facilityId },
                staff: { staffId: user.staffId || user.id },
                startDate: assignForm.startDate,
                endDate: assignForm.endDate,
                status: 'PLACED',
                studentPreference: true
            });
            setShowAssignModal(false);
            setMsg("Student assigned!");
            fetchAllData();
        } catch (err) {
            console.error(err);
            alert("Assignment failed. Ensure all fields are selected.");
        }
    };

    const handleIncidentUpdate = async (incident, newStatus) => {
        try {
            await api.put('/incident', { ...incident, status: newStatus });
            setMsg("Incident updated.");
            fetchAllData();
        } catch (e) { alert("Update failed"); }
    };

    const getAssignedFacility = (studentId) => {
        const p = placements.find(pl => pl.student?.studentId === studentId);
        return p ? p.facility?.name : "Unassigned";
    };

    return (
        <>
            <Navigation user={user} role="ADMIN" />
            <div className="dashboard-container">
                <div className="d-flex justify-content-between align-items-center mb-4">
                    <h2 className="text-cput fw-bold">Admin Console</h2>
                    <div>
                        <Button className="btn-cput me-2" size="sm" onClick={() => setShowStudentModal(true)}>+ Student</Button>
                        <Button className="btn-cput me-2" size="sm" onClick={() => setShowStaffModal(true)}>+ Staff</Button>
                        <Button className="btn-cput" size="sm" onClick={() => setShowAssignModal(true)}>Assign Student</Button>
                    </div>
                </div>

                {msg && <Alert variant="success" onClose={() => setMsg("")} dismissible>{msg}</Alert>}

                <Tabs defaultActiveKey="students" className="mb-3">
                    <Tab eventKey="students" title="Students">
                        <Table striped hover responsive>
                            <thead><tr><th>Number</th><th>Name</th><th>Email</th><th>Assigned</th><th>Action</th></tr></thead>
                            <tbody>
                            {students.map(s => (
                                <tr key={s.studentId}>
                                    <td>{s.studentNumber}</td>
                                    <td>{s.firstName} {s.lastName}</td>
                                    <td>{s.email}</td>
                                    <td><Badge bg="primary">{getAssignedFacility(s.studentId)}</Badge></td>
                                    <td><Button variant="danger" size="sm" onClick={() => handleDeleteStudent(s.studentNumber)}>Delete</Button></td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                    </Tab>

                    <Tab eventKey="staff" title="Staff">
                        <Table striped hover responsive>
                            <thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Action</th></tr></thead>
                            <tbody>
                            {staff.map(s => (
                                <tr key={s.staffId}>
                                    <td>{s.name}</td>
                                    <td>{s.email}</td>
                                    <td><Badge bg="info">{s.role}</Badge></td>
                                    <td><Button variant="danger" size="sm" onClick={() => handleDeleteStaff(s.staffId)}>Delete</Button></td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                    </Tab>

                    <Tab eventKey="incidents" title="Incidents">
                        <Table striped hover responsive>
                            <thead><tr><th>Date</th><th>Student</th><th>Description</th><th>Status</th><th>Update</th></tr></thead>
                            <tbody>
                            {incidents.map(i => (
                                <tr key={i.incidentId}>
                                    <td>{i.date}</td>
                                    <td>{i.student?.firstName} {i.student?.lastName}</td>
                                    <td>{i.description}</td>
                                    <td><Badge bg={i.status==='OPEN'?'danger':'success'}>{i.status}</Badge></td>
                                    <td>
                                        <Form.Select size="sm" value={i.status} onChange={(e) => handleIncidentUpdate(i, e.target.value)}>
                                            <option value="OPEN">Open</option>
                                            <option value="PENDING">Pending</option>
                                            <option value="RESOLVED">Resolved</option>
                                        </Form.Select>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                    </Tab>
                </Tabs>
            </div>

            {/* Modals */}
            <Modal show={showStudentModal} onHide={() => setShowStudentModal(false)}>
                <Modal.Header closeButton><Modal.Title>Add Student</Modal.Title></Modal.Header>
                <Modal.Body>
                    <Form>
                        <Row>
                            <Col><Form.Control className="mb-2" placeholder="Student No" onChange={e => setStudentForm({...studentForm, studentNumber: e.target.value})} /></Col>
                            <Col><Form.Select className="mb-2" onChange={e => setStudentForm({...studentForm, yearLevel: e.target.value})}><option value="1">Year 1</option><option value="2">Year 2</option><option value="3">Year 3</option><option value="4">Year 4</option></Form.Select></Col>
                        </Row>
                        <Form.Control className="mb-2" placeholder="First Name" onChange={e => setStudentForm({...studentForm, firstName: e.target.value})} />
                        <Form.Control className="mb-2" placeholder="Last Name" onChange={e => setStudentForm({...studentForm, lastName: e.target.value})} />
                        <Form.Control className="mb-2" placeholder="Email" onChange={e => setStudentForm({...studentForm, email: e.target.value})} />
                        <Form.Control className="mb-2" type="password" placeholder="Password" onChange={e => setStudentForm({...studentForm, password: e.target.value})} />
                    </Form>
                </Modal.Body>
                <Modal.Footer><Button className="btn-cput" onClick={handleCreateStudent}>Add</Button></Modal.Footer>
            </Modal>

            <Modal show={showStaffModal} onHide={() => setShowStaffModal(false)}>
                <Modal.Header closeButton><Modal.Title>Add Staff</Modal.Title></Modal.Header>
                <Modal.Body>
                    <Form.Control className="mb-2" placeholder="Name" onChange={e => setStaffForm({...staffForm, name: e.target.value})} />
                    <Form.Control className="mb-2" placeholder="Email" onChange={e => setStaffForm({...staffForm, email: e.target.value})} />
                    <Form.Control className="mb-2" type="password" placeholder="Password" onChange={e => setStaffForm({...staffForm, password: e.target.value})} />
                    <Form.Select className="mb-2" onChange={e => setStaffForm({...staffForm, role: e.target.value})}>
                        <option value="CLINICAL_STAFF">Clinical Staff</option>
                        <option value="HOD">HOD</option>
                        <option value="ADMIN">Admin</option>
                    </Form.Select>
                </Modal.Body>
                <Modal.Footer><Button className="btn-cput" onClick={handleCreateStaff}>Add</Button></Modal.Footer>
            </Modal>

            <Modal show={showAssignModal} onHide={() => setShowAssignModal(false)}>
                <Modal.Header closeButton><Modal.Title>Assign Student</Modal.Title></Modal.Header>
                <Modal.Body>
                    <Form.Select className="mb-2" onChange={e => setAssignForm({...assignForm, studentId: e.target.value})}>
                        <option>Select Student</option>
                        {students.map(s => <option key={s.studentId} value={s.studentId}>{s.firstName} {s.lastName}</option>)}
                    </Form.Select>
                    <Form.Select className="mb-2" onChange={e => setAssignForm({...assignForm, facilityId: e.target.value})}>
                        <option>Select Facility</option>
                        {facilities.length > 0 ?
                            facilities.map(f => <option key={f.facilityId} value={f.facilityId}>{f.name}</option>)
                            : <option disabled>No Facilities Available</option>
                        }
                    </Form.Select>
                    <Row>
                        <Col><Form.Control type="date" onChange={e => setAssignForm({...assignForm, startDate: e.target.value})} /></Col>
                        <Col><Form.Control type="date" onChange={e => setAssignForm({...assignForm, endDate: e.target.value})} /></Col>
                    </Row>
                </Modal.Body>
                <Modal.Footer><Button className="btn-cput" onClick={handleAssignStudent}>Assign</Button></Modal.Footer>
            </Modal>
        </>
    );
};

export default AdminDashboard;