import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {Link} from "react-router-dom";
import {ApiUrls} from '../AppConfig'

class notificationList extends Component {

    constructor(props) {
        super(props);
        this.state = {notifications: [], isLoading: true};

    }

    componentDidMount() {
        this.setState({isLoading: true});

        this.timer = setInterval(() => {
            fetch(ApiUrls.notificationsUrl)
                .then(response => response.json())
                .then(data => this.setState({notifications: data, isLoading: false}));
        }, 2000)
    }

    componentWillUnmount() {
        clearInterval(this.timer);
    }

    async remove(id) {
        await fetch(`${ApiUrls.notificationsUrl}/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }

        }).then(() => {
            let updatedNotifications = [...this.state.notifications].filter(i => i.id !== id);
            this.setState({notifications: updatedNotifications});
        });
    }


    render() {
        const {notifications, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const notificationList = notifications.map(notification => {
            return <tr key={notification.id}>
                <td>{notification.activity.note.description}</td>
                <td>{notification.notificationText}</td>


                <ButtonGroup>

                    <Button size="sm" color="danger" onClick={() => this.remove(notification.id)}>Delete</Button>
                </ButtonGroup>


            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <h3>Notification List</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th>Note Description</th>
                            <th>Notification</th>

                        </tr>
                        </thead>
                        <tbody>
                        {notificationList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/">Back</Button>
                    </div>
                </Container>
            </div>
        );
    }
}

export default notificationList;