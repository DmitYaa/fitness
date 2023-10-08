import React from "react"
import Button from "../utils/Button"

class EditUser extends React.Component {
    userAdd = {}

    constructor(props) {
        super(props)
        this.state = {
            name: "",
            role: ""
        }
    }

    render() {
        return (
            <form ref={(el) => this.myForm = el}>
                <input placeholder="Никнейм" onChange={(e) => this.setState({name: e.target.value})} />
                <input placeholder="роль" onChange={(e) => this.setState({role: e.target.value})} />
                
                <Button doIt={() => {
                    this.myForm.reset()
                    this.userAdd = {
                        name: this.state.name,
                        role: this.state.role
                    }
                    if(this.props.user)
                        this.userAdd.id = this.props.user.id
                    this.props.onAdd(this.userAdd)
                }} name="Изменить"/>
            </form>
        )
    }
}

export default EditUser