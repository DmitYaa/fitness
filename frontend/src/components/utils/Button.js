import React from "react"

class Button extends React.Component {

    render() {
        return (
            <button type="button" className="button" onClick={() => this.props.doIt()}>{this.props.name}</button>
        )
    }
}

export default Button