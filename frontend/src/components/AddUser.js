import React from "react"
import Button from "./utils/Button"

class AddUser extends React.Component {
    userAdd = {}

    constructor(props) {
        super(props)
        this.state = {
            first_name: "",
            last_name: "",
            email: "",
        }
    }

    render() {
        return (
            <form ref={(el) => this.myForm = el}>
                <input placeholder="Имя" onChange={(e) => this.setState({first_name: e.target.value})} />
                <input placeholder="Фамилия" onChange={(e) => this.setState({last_name: e.target.value})} />
                <input placeholder="Почта" onChange={(e) => this.setState({email: e.target.value})} />
                
                <Button doIt={() => {
                    this.myForm.reset()
                    this.userAdd = {
                        first_name: this.state.first_name,
                        last_name: this.state.last_name,
                        email: this.state.email,
                    }
                    if(this.props.user)
                        this.userAdd.id = this.props.user.id
                    this.props.onAdd(this.userAdd)
                }} name="Добавить"/>
            </form>
        )
    }
}

export default AddUser