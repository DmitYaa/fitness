import React from "react"
import { BsPersonCircle } from "react-icons/bs"
import MenuList from "./MenuList"

class Menu extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            isOpen: false
        }

        this.setOpen = this.setOpen.bind(this)
    }

    setOpen(isOpen) {
        this.setState({isOpen: isOpen});
    }

    render() {

        return (
            <div>
                <button className="menu-button" onClick={() => this.setOpen(!this.state.isOpen)}>
                    <BsPersonCircle />
                </button>
                <nav className={`menu ${this.state.isOpen ? "active" : ""}`}>
                    <MenuList setMain={this.props.setMain}/>
                </nav>
            </div> 
        )
    }
}

export default Menu