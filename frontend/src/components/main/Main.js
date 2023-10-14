import React from "react"
import MainUsers from "./MainUsers"

class Main extends React.Component {

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
                <MainUsers />
            )
        }
    }
}

export default Main