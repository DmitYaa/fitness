import React from "react"
import Button from "../../../utils/Button"

class TaskPanel extends React.Component {
    render() {
        if (this.props.task === null || this.props.task === undefined) {
            return (
                <div className="exercise_panel">
                    <h1>Панель нового задания</h1>
                </div>
            )
        } else {
            return (
                <div className="exercise_panel">
                    <div>
                        <b>Название:</b>
                        <p>{this.props.task.name}</p>
                    </div>
                    <div>
                        <b>Описание:</b>
                        <p>{this.props.task.description}</p>
                    </div>
                    <div>
                        <b>Название упражнения:</b>
                        <p>{this.props.task.exercise.name}</p>
                        <Button name={"Перейти к упражнению"} doIt={() => console.log("организовать переход к упражнению!!!!")}/>
                    </div>
                    <div>
                        <b>Тип:</b>
                        <p>{this.props.task.typeCount === 0 ? "кол-во повторений" : (this.props.task.typeCount === 1 ? "секунд нужно заниматься" : "минут нужно заниматься")}</p>
                    </div>
                    <div>
                        <b>Кол-во:</b>
                        <p>{this.props.task.count}</p>
                    </div>
                </div>
            )
        }        
    }
}

export default TaskPanel