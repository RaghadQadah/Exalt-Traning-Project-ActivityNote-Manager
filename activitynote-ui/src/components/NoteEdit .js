import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';
import {ApiUrls} from '../AppConfig'

class NoteEdit extends Component {

    emptyNote = {
        name: 'Vacation',
        description: 'test',
        period: 15 * 60 * 1000,
        activityDuration: 2000,
        eventName: '',
        activityremindBefore: 15 * 60 * 1000,
        condition: {weatherMode: 'WARM', location: {name: 'Ramallah'}, day: 'THURSDAY'},
        noteChecker: {
            type: 'DefaultNoteChecker',
            event: {
                eventName: ''
            },
        }
    };

    weatherModes = ['WARM', 'WINDY', 'SUNNY', 'RAINY', 'CLOUDY']
    daysNames = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY']
    selectNoteChecker = ""
    // ids={[1:"DefaultNoteChecker"]
    //  ,[2,"CustomNoteChecker"]};


    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyNote
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleDropdownChange = this.handleDropdownChange.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const note = await (await fetch(`${ApiUrls.noteUrl}/${this.props.match.params.id}`)).json();
            this.setState({item: note});
        }
    }


    handleChange(event) {

        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        let obj = item;

        let tokens = name.split('.')
        for (let i = 0; i < tokens.length; i++) {
            let token = tokens[i]
            if (i == tokens.length - 1) {
                obj[token] = value;
            } else {
                obj = obj[token]
            }
        }
        item[name] = value;
        this.setState({item});

    }

    handleDropdownChange(e) {
        this.setState({selectNoteChecker: e.target.value});
        this.setState(state => (state.item.noteChecker.type = e.target.value, state));

    }


    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch(ApiUrls.noteUrl, {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        this.props.history.push('/notes');
    }


    render() {

        const {item} = this.state;
        console.log('Render', item)
        const title = <h2>{item.id ? 'Edit Note' : 'Add Note'}</h2>;


        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>

                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               onChange={this.handleChange} autoComplete="description"/>
                    </FormGroup>

                    <FormGroup>
                        <Label for="condition.day">Day</Label>
                        <Input type="select" name="condition.day" id="condition.day" onChange={this.handleChange}
                               value={item.condition.day || ''}>
                            {this.daysNames.map(item => <option>{item}</option>)}
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        <Label for="period">Period in ms</Label>
                        <Input type="text" name="period" id="period" value={item.period || ''}
                               onChange={this.handleChange} autoComplete={item.period}/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="condition.weatherMode">Weather Mode</Label>
                        <Input type="select" name="condition.weatherMode" id="condition.weatherMode"
                               onChange={this.handleChange}
                               value={item.condition.weatherMode || ''}>
                            {this.weatherModes.map(item => <option>{item}</option>)}
                        </Input>
                    </FormGroup>

                    <FormGroup>
                        <Label for="condition.location.name">Location Name</Label>
                        <Input type="text" name="condition.location.name" id="condition.location.name"
                               value={item.condition.location.name || ''}
                               onChange={this.handleChange} autoComplete={item.condition.location.name}/>
                    </FormGroup>


                    <FormGroup>
                        <Label for="activityDuration">Activity Duration</Label>
                        <Input type="text" name="activityDuration" id="activityDuration"
                               value={item.activityDuration || ''}
                               onChange={this.handleChange} autoComplete={item.activityDuration}/>
                    </FormGroup>


                    <FormGroup>
                        <Label for="activityremindBefore">Activity remindBefore</Label>
                        <Input type="text" name="activityremindBefore" id="activityremindBefore"
                               value={item.activityremindBefore || ''}
                               onChange={this.handleChange} autoComplete={item.activityremindBefore}/>

                        <Label>Note Checker</Label>
                    </FormGroup>
                    <FormGroup>

                        <div>
                            <div>
                                <FormGroup>
                                    <select value={this.state.selectNoteChecker} onChange={this.handleDropdownChange}>
                                        <option value="DefaultNoteChecker">Default Note Checker</option>
                                        <option value="CustomNoteChecker">Custom Note Checker</option>
                                    </select>


                                </FormGroup>
                            </div>

                            <div>


                                {(() => {
                                    if (this.state.selectNoteChecker == 'CustomNoteChecker') {

                                        return (

                                            <FormGroup>
                                                <FormGroup>
                                                    <Label for="noteChecker.event.eventName">Event Name</Label>
                                                    <Input type="text" name="noteChecker.event.eventName"
                                                           id="noteChecker.event.eventName"
                                                           value={item.noteChecker.event.eventName || ''}
                                                           onChange={this.handleChange}
                                                           autoComplete={item.noteChecker.event.eventName}/>

                                                </FormGroup>


                                            </FormGroup>


                                        )
                                    }

                                    return null;
                                })()}


                            </div>
                        </div>

                    </FormGroup>
                    <br/>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/notes">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(NoteEdit)