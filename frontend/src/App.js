import React from "react"
import Header from "./components/header/Header"
import Main from "./components/main/Main"
import AddUser from "./components/AddUser"


class App extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            title: "Главная",
            main: "home"
        }

        this.setMain = this.setMain.bind(this)
    }

    setMain(newMain) {
        if (newMain === "users") {
            this.setState({title: "Список пользователей", main: newMain})
        } else if (newMain === "home") {
            this.setState({title: "Главная", main: newMain})
        }
    }

    render() {
        return (
            <div>
                <Header setMain={this.setMain}/>
                <Main main={this.state.main} title={this.state.title} />
                <aside>
                    <AddUser onAdd={this.addUser} />
                </aside>
            </div>
        )
    }
}

export default App