import React from "react"
import Login from "./Login"
import { LuDumbbell } from "react-icons/lu"


class Header extends React.Component {
    render() {
        return (
            <header className="header">
                <div>
                    <button className="header-button" onClick={() => this.props.setMain("home")}>
                        <LuDumbbell />
                    </button>
                    
                    <Login setMain={this.props.setMain}/>
                </div>
            </header>
        )
    }
}

export default Header