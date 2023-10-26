import React from "react"
import Login from "./Login"
import { LuDumbbell } from "react-icons/lu"


class Header extends React.Component {
    render() {
        return (
            <header className="header">
                <button className="header-button" onClick={() => this.props.setMain("home")}>
                    <LuDumbbell />
                </button>
                    
                <Login setMain={this.props.setMain}/>
            </header>
        )
    }
}

export default Header