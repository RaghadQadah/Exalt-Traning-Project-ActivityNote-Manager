import React, {Component} from 'react';
import './App.css';
import Home from './components/Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import NoteEdit from './components/NoteEdit ';
import NoteList from "./components/NoteList ";
import ActivityList from "./components/ActivityList";
import AddLocation from "./components/AddLocation";
import notificationList from "./components/notifcationList";

class App extends Component {
    render() {
        return (
            <Router>
                <div className="container">

                    <Switch>
                        <Route path='/' exact={true} component={Home}/>
                        <Route path='/notes' exact={true} component={NoteList}/>
                        <Route path='/activity' exact={true} component={ActivityList}/>
                        <Route path='/notes/:id' component={NoteEdit}/>
                        <Route path='/location' component={AddLocation}/>
                        <Route path='/notifications' component={notificationList}/>
                    </Switch>
                </div>
            </Router>
        )
    }
}

export default App;