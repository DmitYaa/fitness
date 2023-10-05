import React from "react"
import Users from "./Users"
import axios from "axios"

const baseUrl = "https://reqres.in/api/users?page=1"

class Main extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            users: []
        }

        axios.get(baseUrl).then((res) => {
            this.setState({users: res.data.data})
        })

        this.addUser = this.addUser.bind(this)
        this.deleteUser = this.deleteUser.bind(this)
        this.editUser = this.editUser.bind(this)
    }

    name = localStorage.getItem("name")
    role = localStorage.getItem("role")


    addUser(user) {
        const id = this.state.users.length + 1
        console.log(user)
        this.setState({users: [...this.state.users, {id, ...user}]})
    }

    editUser(user) {
        let allUsers = this.state.users
        allUsers[user.id - 1] = user
        this.setState({users: []}, () => {
            this.setState({users: [...allUsers]})
        })
    }

    deleteUser(id) {
        this.setState({
            users: this.state.users.filter((el) => el.id !== id)
        })
    }

    render() {

        if (this.props.main === "home") {
            return (
                <main>
                    <label className="title">{this.props.title}</label>
                </main>
            )
        } else if (this.props.main === "users") {
            return (
                <main>
                    <label className="title">{this.props.title}</label>
                    <Users users={this.state.users} onDelete={this.deleteUser} onEdit={this.editUser} />
                </main>
            )
        }
    }
}

export default Main