import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import {ApiUrls} from '../AppConfig'

class AddLocation extends Component {

    emptyLocation = {
        id: '',
        name: '',
        longitude: '',
        latitude: '',

    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyLocation
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const location = await (await fetch(`${ApiUrls.locationUrl}/${this.props.match.params.id}`)).json();
            this.setState({item: location});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;

        await fetch(ApiUrls.locationUrl, {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
       this.props.history.push('/');
    }

    render() {

        const {item} = this.state;

        return <div>
            <AppNavbar/>
            <Container>
                Add Location
                <Form onSubmit={this.handleSubmit}>

                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={this.handleChange} autoComplete="name"/>
                    </FormGroup>

                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/">Cancel</Button>
                    </FormGroup>
                </Form>

            </Container>
        </div>
    }
}

export default withRouter(AddLocation);