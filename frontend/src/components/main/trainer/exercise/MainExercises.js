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
        this.getImage = this.getImage.bind(this)
        this.getVideo = this.getVideo.bind(this)
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
        this.getImage(exercise.id);
        this.getVideo(exercise.id);
    }

    getImage(id) {
        const token = localStorage.getItem("jwt")
            if (token !== undefined && token !== null && token !== "undefined") {
                axios.get(url + "/get_image/" + id, {
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
                    })
            }
    }

    getVideo(id) {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get(url + "/get_video/" + id, {
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
                    
                </div>
                <div className="exercise_panel">
                    <TrainerExercise exercise={this.state.exercise} image={this.state.image} video={this.state.video}/>
                    <Button name={"Get Exercises"} doIt={() => console.log(this.state.exercise)}/>
                    {/*правая часть с выбранным упражнением*/}
                </div>
            </main>
        )
    }
}

export default MainExercises