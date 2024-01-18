import React from "react"
import axios from "axios"
import Button from "../../../utils/Button"
import ExercisePanel from "./ExercisePanel"

const url = "http://78.24.218.228:8080/trainer/exercise";

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
        this.getImage = this.getImage.bind(this)
        this.getVideo = this.getVideo.bind(this)
    }

    getExercises() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get(url + "/all", {
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
        this.getImage(exercise.id);
        this.getVideo(exercise.id);
    }

    getImage(id) {
        const token = localStorage.getItem("jwt")
            if (token !== undefined && token !== null && token !== "undefined") {
                axios.get(url + "/image/" + id, {
                    headers: {
                        'Authorization': 'Bearer ' + token
                    }, responseType: "arraybuffer"
                })
                    .then((res) => {
                        const base64 = btoa(
                            new Uint8Array(res.data).reduce(
                            (data, byte) => data + String.fromCharCode(byte),
                            ''
                            )
                        )
                        this.setState({image: base64})
                    })
                    .catch((error) => {
                        console.error(error)
                        window.location.reload()
                    })
            }
    }

    getVideo(id) {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get(url + "/video/" + id, {
                headers: {
                    'Authorization': 'Bearer ' + token
                }, responseType: "arraybuffer"
            })
                .then((res) => {
                    const myUrl = (window.URL || window.webkitURL).createObjectURL( new Blob([res.data]) ); 
                    this.setState({video: myUrl})
                })
                .catch((error) => {
                    console.error(error)
                    window.location.reload()
                })
        }
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

                    <Button name={"Добавить упражнение"} doIt={() => this.setState({exercise: null})}/>
                </div>
                <ExercisePanel exercise={this.state.exercise} image={this.state.image} video={this.state.video} refresh={this.getExercises}/>
                
            </main>
        )
    }
}

export default MainExercises