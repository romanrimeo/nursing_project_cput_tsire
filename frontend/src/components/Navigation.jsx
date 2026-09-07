import { Navbar, Container, Nav, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const Navigation = ({ user, role }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.clear();
        navigate('/');
    };

    return (
        <Navbar bg="white" expand="lg" className="shadow-sm mb-4">
            <Container>
                <Navbar.Brand className="text-cput">
                    CPUT Nursing Placement
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav" className="justify-content-end">
                    <Nav className="align-items-center">
                        {user && (
                            <>
                                <Navbar.Text className="me-3">
                                    Signed in as: <strong>{user.firstName || user.name}</strong> ({role})
                                </Navbar.Text>
                                <Button variant="outline-danger" size="sm" onClick={handleLogout}>
                                    Logout
                                </Button>
                            </>
                        )}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Navigation;