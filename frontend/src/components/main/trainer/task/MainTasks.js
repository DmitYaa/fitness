import React from "react"
import axios from "axios"
import Button from "../../../utils/Button"
import TaskPanel from "./TaskPanel";

const url = "http://78.24.218.228:8080/trainer/task";

class MainTasks extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            tasks: [],
            task: null
        }

        this.getTasks()

        this.setTask = this.setTask.bind(this)
        this.getTasks = this.getTasks.bind(this)
    }

    setTask(newTask) {
        this.setState({task: newTask})
    }

    getTasks() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get(url + "/all", {
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
              })
              .then((res) => {
                this.setState({tasks: res.data})
              })
              .catch((error) => {
                console.error(error)
              })
        }
    }

    render() {
        return (
            <main>
                <h1 className="title">Задания</h1>
                <div className="exercises_list">
                    <table>
                        <thead>
                            <tr>
                                <th>Название</th>
                                <th>Описание</th>
                                <th>Название упражнения</th>
                                <th>Тип</th>
                                <th>Кол-во</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.tasks.map((el) => (
                                <tr key={el.id} onClick={() => this.setTask(el)}>
                                    <th>{el.name}</th>
                                    <th>{el.description}</th>
                                    <th>{el.exercise.name}</th>
                                    <th>{el.typeCount === 0 ? "кол-во повторений" : (el.typeCount === 1 ? "секунд нужно заниматься" : "минут нужно заниматься")}</th>
                                    <th>{el.count}</th>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <Button name={"Добавить Задание"} doIt={() => this.setState({task: null})}/>
                </div>
                <TaskPanel task={this.state.task}/>
            </main>
        )
    }
}

export default MainTasks