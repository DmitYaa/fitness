import React from "react"
import AddUser from "../AddUser"
import { IoCloseCircleSharp, IoHammerSharp } from 'react-icons/io5'

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
                <IoCloseCircleSharp className="delete-icon" onClick={() => this.props.onDelete(this.user.id)} />
                <IoHammerSharp className="edit-icon" onClick={() => this.setState({
                    editForm: !this.state.editForm
                })} />
                <h3>{this.user.first_name} {this.user.last_name}</h3>
                <img src={this.user.avatar} alt="avatar"/>
                <p>{this.user.email}</p>

                {this.state.editForm && <AddUser user={this.user} onAdd={this.props.onEdit} />}
            </div>
        )
    }
}

export default User