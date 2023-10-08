import React from "react"
import Users from "./Users"
import axios from "axios"

const url = "http://localhost:8080/admin"

class Main extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            users: []
        }

        this.getUsers()

        this.addUser = this.addUser.bind(this)
        this.getUsers = this.getUsers.bind(this)
        this.editUser = this.editUser.bind(this)
    }

    name = localStorage.getItem("name")
    role = localStorage.getItem("role")

    getUsers() {
        const token = localStorage.getItem("jwt")

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


    addUser(user) {
        const id = this.state.users.length + 1
        this.setState({users: [...this.state.users, {id, ...user}]})
    }

    editUser(user) {
        let allUsers = this.state.users
        allUsers[user.id - 1] = user
        this.setState({users: []}, () => {
            this.setState({users: [...allUsers]})
        })
    }

    render() {
        if (this.props.main === "home") {
            return (
                <main>
                    <h1 className="title">{this.props.title}</h1>
                    <p>Твое фитнесс приложение!</p>
                    <p>Приложение «Фитнес»  на iPhone помогает в достижении фитнес-целей. 
                        Можно отслеживать свои достижения, просматривать завершенные тренировки и отправлять данные 
                        о своей активности другим пользователям. А premium подписка открывает доступ к каталогу 
                        различных тренировок и медитаций.</p>
                </main>
            )
        } else if (this.props.main === "users") {
        
            return (
                <main>
                    <label className="title">{this.props.title}</label>
                    <Users users={this.state.users} onEdit={this.editUser} />
                </main>
            )
        }
    }
}

export default Main