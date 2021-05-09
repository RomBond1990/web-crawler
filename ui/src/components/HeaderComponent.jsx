import React, {Component} from 'react';
import {NavLink} from "react-router-dom";

class HeaderComponent extends Component {


    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <div><a href="/" className="navbar-brand">All Rooms</a></div>
                        <div><a href="/add-room/_add" className="navbar-brand">Add Room</a></div>

                    </nav>
                </header>
            </div>
        );
    }
}

export default HeaderComponent;
