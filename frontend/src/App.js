import React from "react"
import Header from "./components/header/Header"
import Main from "./components/main/Main"


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
        } else if (newMain === "exercises") {
            this.setState({title: "Упражнения", main: newMain})
        }
    }

    render() {
        return (
            <div className="myBody">
                <Header setMain={this.setMain}/>
                <Main main={this.state.main} title={this.state.title} />
            </div>
        )
    }
}

export default App