import React, {Component} from 'react';
import '../App.css';
import AppNavbar from './AppNavbar';
import {Link} from 'react-router-dom';
import {Button, ButtonGroup, Container} from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid maxwidth="lg">

                    <div><Button color="link"><Link to="/notes">Manage Notes </Link></Button></div>
                    <div><Button color="link"><Link to="/activity">View Activity List </Link></Button></div>
                    <div><Button color="link"><Link to="/notifications">View notifications</Link></Button></div>
                    <div><Button color="link"><Link to="/location">Add Location</Link></Button></div>


                </Container>
            </div>
        );
    }
}

export default Home;