import React, {Component} from 'react';
import {NavLink} from "react-router-dom";

class HeaderComponent extends Component {


    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <NavLink to="/" className="navbar-brand">Scanner</NavLink>
                    </nav>
                </header>
            </div>
        );
    }
}

export default HeaderComponent;
