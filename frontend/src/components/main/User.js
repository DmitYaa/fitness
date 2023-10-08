import React from "react"
import { IoHammerSharp } from 'react-icons/io5'
import EditUser from "./EditUser"

class User extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            editForm: false
        }
    }

    user = this.props.user

    render() {
        return (
            <div className="user" >
                <IoHammerSharp className="edit-icon" onClick={() => this.setState({
                    editForm: !this.state.editForm
                })} />
                <h3>id: {this.user.id}</h3>
                <h3>name: {this.user.name}</h3>
                <h3>role: {this.user.role}</h3>

                {this.state.editForm && <EditUser user={this.user} onAdd={this.props.onEdit} />}
            </div>
        )
    }
}

export default User