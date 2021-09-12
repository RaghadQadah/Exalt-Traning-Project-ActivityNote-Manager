import React, {Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {Link} from 'react-router-dom';
import {ApiUrls} from '../AppConfig'

class NoteList extends Component {

    constructor(props) {
        super(props);
        this.state = {notes: [], isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});
        fetch(ApiUrls.notesListUrl)
            .then(response => response.json())
            .then(data => this.setState({notes: data, isLoading: false}))
            .catch(console.log);
    }

    async remove(id) {
        await fetch(`${ApiUrls.noteUrl}/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedNote = [...this.state.notes].filter(i => i.id !== id);
            this.setState({notes: updatedNote});
        });
    }

    render() {
        const {notes, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const noteList = notes.map(note => {
            return <tr key={note.id}>
                <td>{note.id}</td>
                <td style={{whiteSpace: 'nowrap'}}>{note.name}</td>
                <td>{note.description}</td>
                <td>{note.condition.day}</td>
                <td>{note.period}</td>
                <td>{note.lastTimeProcessing}</td>
                <td>{note.condition.weatherMode}</td>
                <td>{note.condition.location.name}</td>
                <td>{note.activityDuration}</td>
                <td>{note.activityremindBefore}</td>

                <td>
                    <ButtonGroup>
                        <Button size="sm" color="grey" tag={Link} to={"/notes/" + note.id}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(note.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/notes/new">Add Note</Button>
                    </div>
                    <h3>Note List</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Day</th>
                            <th>Period in ms</th>
                            <th>Last Time Processing</th>
                            <th>Condtion WeatherMode</th>
                            <th>Condtion Location Name</th>
                            <th>activityDuration</th>
                            <th>activityremindBefore</th>

                        </tr>
                        </thead>
                        <tbody>
                        {noteList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default NoteList;