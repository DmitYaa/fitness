import React from "react"
import Users from "./Users"
import axios from "axios"
import SearchUser from "./SearchUser"

const url = "http://localhost:8080/admin"

class MainUsers extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            users: []
        }
        
        this.getUsers()

        this.getUsers = this.getUsers.bind(this)
        this.editUser = this.editUser.bind(this)
    }

    name = localStorage.getItem("name")
    role = localStorage.getItem("role")

    getUsers(searchString) {
        const token = localStorage.getItem("jwt")

        if (searchString !== undefined && searchString !== null && searchString !== "undefined") {
            axios.get(url + "/search_people", searchString, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            })
            .then((res) => {
                this.setState({users: res.data})
            })
            .catch((error) => {
                alert("Произошла неизвестная ошибка: " + error)
            })
        } else {
            axios.get(url + "/people", {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            })
            .then((res) => {
                this.setState({users: res.data})
            })
            .catch((error) => {
                alert("Произошла неизвестная ошибка: " + error)
            })
        } 
    }

    editUser(user) {
        const token = localStorage.getItem("jwt")

        axios.put(url + "/set_new_person_data", user, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        })
        .then((res) => {
            this.getUsers()
        })
        .catch((error) => {
            alert("Произошла неизвестная ошибка: " + error)
        })
    }

    render() {
        return (
            <main>
                <h1 className="title">Пользователи</h1>
                <SearchUser getUsers={this.getUsers}/>
                <Users users={this.state.users} onEdit={this.editUser} />
            </main>
        )
    }
}

export default MainUsers