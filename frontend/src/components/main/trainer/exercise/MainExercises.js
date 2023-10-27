import React from "react"
import Button from "../../../utils/Button"
import axios from "axios";
import TrainerExercise from "./TrainerExercise"

const url = "http://localhost:8080/trainer";

class MainExercises extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            exercises: [],
            exercise: null
        }

        this.getExercises()

        this.getExercises = this.getExercises.bind(this)
        this.setExercise = this.setExercise.bind(this)
    }

    getExercises() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get(url + "/exercises", {
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
              })
              .then((res) => {
                this.setState({exercises: res.data})
              })
              .catch((error) => {
                console.error(error)
              })
        }
    }

    setExercise(exercise) {
        this.setState({exercise: exercise})
    }

    render() {
        return (
            <main>
                <h1 className="title">Упражнения</h1>
                

                <div className="exercises_list">
                    <table>
                        <thead>
                            <tr>
                                <th>Название</th>
                                <th>Мышцы</th>
                                <th>Описание</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.exercises.map((el) => (
                                <tr key={el.id} onClick={() => this.setExercise(el)}>
                                    <th>{el.name}</th>
                                    <th>{el.muscle}</th>
                                    <th>{el.description}</th>
                                </tr>
                            ))}
                        </tbody>
                    
                    </table>
                    
                </div>
                <div className="exercise_panel">
                    <TrainerExercise exercise={this.state.exercise} />
                    <Button name={"Get Exercises"} doIt={() => console.log(this.state.exercise)}/>
                    {/*правая часть с выбранным упражнением*/}
                </div>
            </main>
        )
    }
}

export default MainExercises