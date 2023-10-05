import React from "react"
import Button from "../utils/Button"

class MenuElement extends React.Component {
    
    render() {
        return (
            <li className="menu_item">
                <Button name={this.props.buttonName} doIt={this.props.buttonFunction}/>
            </li>
        )
    }
}

export default MenuElement