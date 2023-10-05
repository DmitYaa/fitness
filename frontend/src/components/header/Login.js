import React from "react"
import Menu from "./Menu"
import {login, getUserData, registration, isLoggedIn} from "../../utils/AuthUtils"

class Login extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            loggedIn: false,
            name: "",
            password: "",
            role: ""
        }

        this.setAllData = this.setAllData.bind(this)
        this.setLoggedIn = this.setLoggedIn.bind(this)
        this.getUsername = this.getUsername.bind(this)
        isLoggedIn(this.setLoggedIn)
    }

    setAllData(data) {
      if (data !== undefined && data !== null) {
        this.setState({name: data.name, role: data.role, loggedIn: data.loggedIn})
      }
    }

    setLoggedIn(data) {
      if (data !== undefined && data !== null) {
        this.setState({loggedIn: data.loggedIn})
      }
    }

    getUsername() {
      const name = localStorage.getItem("name")
      if (name !== undefined && name !== null && name !== "undefined") {
        return name
      } else {
        getUserData(this.setAllData)
      } 
    }

    render() {
        if (this.state.loggedIn) {
            return (
                <div className="login_form">
                  <Menu role={this.state.role} setMain={this.props.setMain}/>
                </div>
            )
        } else {
            return (
                <div className="login_form">
                    <form >
                        <input placeholder="Имя" onChange={(e) => this.setState({name: e.target.value})} />
                        <input type="password" placeholder="Пароль" onChange={(e) => this.setState({password: e.target.value})} />
                        <button type="button" onClick={() => {
                            login({name: this.state.name, password: this.state.password}, this.setAllData)
                        }}>Войти</button>
                        <button type="button" onClick={() => {
                            registration({name: this.state.name, password: this.state.password})
                        }}>Регистрация</button>
                    </form>
                </div>
            )
        }
    }
}

export default Login