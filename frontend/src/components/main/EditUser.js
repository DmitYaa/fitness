import React from "react"
import Button from "../utils/Button"

class EditUser extends React.Component {
    userAdd = {}

    constructor(props) {
        super(props)
        this.state = {
            id: this.props.user.id,
            name: this.props.user.name,
            role: this.props.user.role
        }

        this.handleChange = this.handleChange.bind(this)
    }

    handleChange(event) {
        this.setState({role: event.target.value})
    }

    render() {
        return (
            <form ref={(el) => this.myForm = el}>
                <input value={this.state.name} placeholder="Никнейм" onChange={(e) => this.setState({name: e.target.value})} />
                
                <select value={this.state.role} onChange={this.handleChange}>
                        <option value="ROLE_ADMIN">Аминистратор</option>
                        <option value="ROLE_TRAINER">Тренер</option>
                        <option value="ROLE_USER">Пользователь</option>
                        <option value="ROLE_PREMIUM">Премиум пользователь</option>
                </select>

                <Button doIt={() => {
                    this.myForm.reset()
                    this.userAdd = {
                        id: this.state.id,
                        name: this.state.name,
                        role: this.state.role
                    }
                    if(this.props.user)
                        this.userAdd.id = this.props.user.id

                    this.props.closeEditForm()
                    this.props.onEdit(this.userAdd)
                }} name="Изменить"/>
            </form>
        )
    }
}

export default EditUser