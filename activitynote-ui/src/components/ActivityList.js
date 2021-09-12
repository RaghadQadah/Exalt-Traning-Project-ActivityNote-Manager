import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {Link} from "react-router-dom";
import {ApiUrls} from '../AppConfig'

class ActivityList extends Component {

    constructor(props) {
        super(props);
        this.state = {activity: [], isLoading: true};

    }

    componentDidMount() {
        this.setState({isLoading: true});

        this.timer = setInterval(() => {
            fetch(ApiUrls.activityListUrl)
                .then(response => response.json())
                .then(data => this.setState({activity: data, isLoading: false}));
        }, 2000)
    }

    componentWillUnmount() {
        clearInterval(this.timer);
    }

    async remove(id) {
        await fetch(`${ApiUrls.activityListUrl}/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedActivity = [...this.state.activity].filter(i => i.id !== id);
            this.setState({activity: updatedActivity});
        });
    }


    render() {
        const {activity, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const activityList = activity.map(activity => {
            return <tr key={activity.id}>
                <td>{activity.id}</td>
                <td>{activity.note.description}</td>
                <td>{activity.startTime}</td>
                <td style={{whiteSpace: 'nowrap'}}>{activity.timestamp}</td>
                <td>{activity.note.id}</td>

                <ButtonGroup>
                    {/*<Button size="sm" color="grey" tag={Link} to={"/notifications/" + notifications.id}>Add Duration & remindBefore </Button>*/}
                    <Button size="sm" color="danger" onClick={() => this.remove(activity.id)}>Delete</Button>
                </ButtonGroup>


            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <h3>Activiy List</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>description</th>
                            <th>startTime</th>
                            <th>Timestamp</th>
                            <th>Note Id</th>
                        </tr>
                        </thead>
                        <tbody>
                        {activityList}
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

export default ActivityList;