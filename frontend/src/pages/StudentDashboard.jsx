import { useEffect, useState } from 'react';
import api from '../api/axiosConfig';
import Navigation from '../components/Navigation';
import { Container, Card, Row, Col, ListGroup, Button, Modal, Form, Alert, Badge, Tab, Tabs } from 'react-bootstrap';

const StudentDashboard = () => {
    const [user, setUser] = useState(JSON.parse(localStorage.getItem('user')));

    // Data States
    const [placement, setPlacement] = useState(null);
    const [incidents, setIncidents] = useState([]);
    const [facilities, setFacilities] = useState([]);
    const [preferences, setPreferences] = useState([]);

    // UI States
    const [showEditProfile, setShowEditProfile] = useState(false);
    const [showPrefModal, setShowPrefModal] = useState(false);
    const [showIncidentModal, setShowIncidentModal] = useState(false);
    const [msg, setMsg] = useState("");

    // Forms
    const [profileForm, setProfileForm] = useState({ ...user });
    const [incidentForm, setIncidentForm] = useState({ date: '', description: '', discipline: 'Nursing' });
    const [selectedFacility, setSelectedFacility] = useState("");

    useEffect(() => {
        if (user) fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const iRes = await api.get(`/incident/student/${user.studentId}`);
            setIncidents(iRes.data || []);

            const pRes = await api.get(`/placement/student/${user.studentId}`);
            if (pRes.data && pRes.data.length > 0) setPlacement(pRes.data[pRes.data.length - 1]);

            // Fetch Facilities
            const fRes = await api.get('/facility/all');
            setFacilities(fRes.data || []);

            const prefRes = await api.get(`/preferences/${user.studentNumber}`);
            setPreferences(prefRes.data || []);
        } catch (error) {
            console.error("Error loading student data", error);
        }
    };

    const handleUpdateProfile = async () => {
        try {
            const res = await api.put('/student', profileForm);
            setUser(res.data);
            localStorage.setItem('user', JSON.stringify(res.data));
            setShowEditProfile(false);
            setMsg("Profile updated successfully!");
        } catch (err) { alert("Failed to update profile."); }
    };

    const handleReportIncident = async () => {
        if (!incidentForm.date || !incidentForm.description) {
            alert("Please fill in Date and Description");
            return;
        }
        try {

            await api.post('/incident', {
                student: { studentId: user.studentId },
                reportedByStaff: null,
                facility: null,
                date: incidentForm.date,
                description: incidentForm.description,
                discipline: incidentForm.discipline,
                status: 'OPEN'
            });
            setShowIncidentModal(false);
            setMsg("Incident reported successfully.");
            fetchData();
        } catch (err) {
            console.error(err);
            alert("Failed to report. " + (err.response?.data?.message || err.message));
        }
    };

    const handleAddPreference = async () => {
        if (!selectedFacility) return;
        try {
            const currentIds = preferences.map(p => p.facilityId);
            const newIds = [...currentIds, Number(selectedFacility)];

            await api.post('/preferences', {
                studentNumber: user.studentNumber,
                facilityIds: newIds
            });

            setShowPrefModal(false);
            setMsg("Preference added!");
            fetchData();
        } catch (err) { alert("Error saving preference."); }
    };

    return (
        <>
            <Navigation user={user} role="STUDENT" />
            <div className="dashboard-container">
                {msg && <Alert variant="success" onClose={() => setMsg("")} dismissible>{msg}</Alert>}

                <Row>
                    <Col md={4}>
                        <Card className="mb-4">
                            <Card.Header className="bg-cput text-white d-flex justify-content-between align-items-center">
                                <span>My Profile</span>
                                <Button variant="light" size="sm" onClick={() => setShowEditProfile(true)}>Edit</Button>
                            </Card.Header>
                            <Card.Body>
                                <p><strong>Student #:</strong> {user.studentNumber}</p>
                                <p><strong>Name:</strong> {user.firstName} {user.lastName}</p>
                                <p><strong>Email:</strong> {user.email}</p>
                                <p><strong>Year Level:</strong> {user.yearLevel}</p>
                                <div className="mt-3 p-3 bg-light border rounded text-center">
                                    <small className="text-muted text-uppercase fw-bold">Assigned Placement</small>
                                    <h5 className="mt-2 text-primary">
                                        {placement ? placement.facility?.name : "Not Placed Yet"}
                                    </h5>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>

                    <Col md={8}>
                        <Tabs defaultActiveKey="incidents" className="mb-3">
                            <Tab eventKey="incidents" title="Incident Reports">
                                <Card>
                                    <Card.Header className="d-flex justify-content-between align-items-center">
                                        <span className="fw-bold">My History</span>
                                        <Button variant="danger" size="sm" onClick={() => setShowIncidentModal(true)}>+ Report Issue</Button>
                                    </Card.Header>
                                    <Card.Body>
                                        <ListGroup variant="flush">
                                            {incidents.map(i => (
                                                <ListGroup.Item key={i.incidentId} className="d-flex justify-content-between align-items-center">
                                                    <div><span className="fw-bold">{i.date}</span>: {i.description}</div>
                                                    <Badge bg={i.status === 'RESOLVED' ? 'success' : 'warning'}>{i.status}</Badge>
                                                </ListGroup.Item>
                                            ))}
                                            {incidents.length === 0 && <p className="text-muted text-center py-3">No reports filed.</p>}
                                        </ListGroup>
                                    </Card.Body>
                                </Card>
                            </Tab>

                            <Tab eventKey="preferences" title="Placement Preferences">
                                <Card>
                                    <Card.Header className="bg-white fw-bold d-flex justify-content-between align-items-center">
                                        <span>My Wishlist</span>
                                        <Button variant="outline-primary" size="sm" onClick={() => setShowPrefModal(true)}>+ Add Facility</Button>
                                    </Card.Header>
                                    <Card.Body>
                                        <ListGroup variant="flush">
                                            {preferences.map((p, idx) => (
                                                <ListGroup.Item key={p.preferenceId}>
                                                    <span className="fw-bold text-secondary me-2">Rank {idx + 1}:</span> {p.facilityName}
                                                </ListGroup.Item>
                                            ))}
                                            {preferences.length === 0 && <p className="text-muted text-center py-3">No preferences yet.</p>}
                                        </ListGroup>
                                    </Card.Body>
                                </Card>
                            </Tab>
                        </Tabs>
                    </Col>
                </Row>
            </div>

            {/* MODALS */}
            <Modal show={showEditProfile} onHide={() => setShowEditProfile(false)} centered>
                <Modal.Header closeButton><Modal.Title>Edit Profile</Modal.Title></Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-2"><Form.Label>First Name</Form.Label><Form.Control type="text" value={profileForm.firstName} onChange={e => setProfileForm({...profileForm, firstName: e.target.value})} /></Form.Group>
                        <Form.Group className="mb-2"><Form.Label>Last Name</Form.Label><Form.Control type="text" value={profileForm.lastName} onChange={e => setProfileForm({...profileForm, lastName: e.target.value})} /></Form.Group>
                        <Form.Group className="mb-2"><Form.Label>Email</Form.Label><Form.Control type="email" value={profileForm.email} onChange={e => setProfileForm({...profileForm, email: e.target.value})} /></Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer><Button className="btn-cput" onClick={handleUpdateProfile}>Save</Button></Modal.Footer>
            </Modal>

            <Modal show={showIncidentModal} onHide={() => setShowIncidentModal(false)} centered>
                <Modal.Header closeButton><Modal.Title>Log Incident</Modal.Title></Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group className="mb-3"><Form.Label>Date</Form.Label><Form.Control type="date" onChange={e => setIncidentForm({...incidentForm, date: e.target.value})} /></Form.Group>
                        <Form.Group className="mb-3"><Form.Label>Discipline</Form.Label><Form.Control type="text" value={incidentForm.discipline} onChange={e => setIncidentForm({...incidentForm, discipline: e.target.value})} /></Form.Group>
                        <Form.Group className="mb-3"><Form.Label>Description</Form.Label><Form.Control as="textarea" rows={3} onChange={e => setIncidentForm({...incidentForm, description: e.target.value})} /></Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer><Button variant="danger" onClick={handleReportIncident}>Submit</Button></Modal.Footer>
            </Modal>

            <Modal show={showPrefModal} onHide={() => setShowPrefModal(false)} centered>
                <Modal.Header closeButton><Modal.Title>Select Facility</Modal.Title></Modal.Header>
                <Modal.Body>
                    {facilities.length > 0 ? (
                        <Form.Select onChange={e => setSelectedFacility(e.target.value)}>
                            <option value="">-- Choose Facility --</option>
                            {facilities.map(f => <option key={f.facilityId} value={f.facilityId}>{f.name} ({f.type})</option>)}
                        </Form.Select>
                    ) : <p className="text-danger">No facilities loaded. Please contact Admin.</p>}
                </Modal.Body>
                <Modal.Footer><Button className="btn-cput" onClick={handleAddPreference}>Save</Button></Modal.Footer>
            </Modal>
        </>
    );
};

export default StudentDashboard;