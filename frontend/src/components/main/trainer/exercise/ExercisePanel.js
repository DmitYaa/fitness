import React from "react"
import axios from "axios"
import TrainerExercise from "./TrainerExercise"
import Button from "../../../utils/Button"

const url = "http://localhost:8080/trainer"
const fileReader = new FileReader()

class ExercisePanel extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            name: props.name,
            muscle: props.muscle,
            description: props.description,
            image: props.image,
            imageUrl: props.imageUrl,
            video: props.video
        }

        fileReader.onloadend = () => {
            this.setState({imageUrl: fileReader.result})
        }

        this.postExercise = this.postExercise.bind(this)
    }

    postExercise() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.post(url + "/new_exercise", {
                name: this.state.name,
                muscle: this.state.muscle,
                description: this.state.description
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            }).then((res) => {
                const formData = new FormData();
                formData.append(
                    "image",
                    this.state.image
                )

                axios.put(url + "/image/" + res.data, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Authorization': 'Bearer ' + token
                    }
                }).then((res) => {
                    console.log(res)
                }).catch((error) => {
                    console.error(error)
                })
            }).catch((error) => {
                console.error(error)
            })
        }
    }

    render() {
        if (this.props.exercise === null || this.props.exercise === undefined) {
            return (
                <div className="exercise_panel">
                    <h2 className="title">Новое упражнение:</h2>
                    <form>
                        <p>
                            <b>Название:</b>
                            <input name="name" type="text" onChange={(e) => this.setState({name: e.target.value})}/>
                        </p>
                        <p>
                            <b>Мышцы:</b>
                            <textarea name="muscle" onChange={(e) => this.setState({muscle: e.target.value})}></textarea>
                        </p>
                        <p>
                            <b>Описание:</b>
                            <textarea name="description" onChange={(e) => this.setState({description: e.target.value})}></textarea>
                        </p>
                        <p>
                            <b>Изображение:</b>
                            <input name="image" type="file" onChange={(e) => {
                                this.setState({image: e.target.files[0]})
                                fileReader.readAsDataURL(e.target.files[0])
                            }}/>
                            <img 
                                src={this.state.imageUrl ? this.state.imageUrl : "logo512.png"}
                                width="360"
                                height="480"
                                alt="Изображение упражнения"
                            />
                        </p>
                        <p>
                            <b>Видео:</b>
                            <input name="video" type="file" onChange={(e) => console.log(e.target)}/>
                        </p>
                        
                        <button type="button" onClick={() => this.postExercise()}>Добавить упражнение</button>
                    </form>
                </div>
            )
        } else {
            return (
                <div className="exercise_panel">
                    <TrainerExercise exercise={this.props.exercise} image={this.props.image} video={this.props.video}/>
                    <Button name={"Просмотр упражнения"} doIt={() => console.log(this.props.exercise)}/>
                </div>
            )
        }        
    }
}

export default ExercisePanel