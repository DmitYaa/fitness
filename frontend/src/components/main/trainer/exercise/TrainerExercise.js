import React from "react"
import axios from "axios";

class TrainerExercise extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            image: ""
        }

        this.getImage()

        this.getImage = this.getImage.bind(this)
    }


    getImage() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get("http://localhost:8080/trainer/get_file", {
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
    

    render() {
        if (this.props.exercise != null && this.state.image != "") {
            return (
                <div>
                    <p>{this.props.exercise.name}</p>
                    <p>{this.props.exercise.muscle}</p>
                    <p>{this.props.exercise.description}</p>
                    <img alt="Изображение упражнения" src={`data:image/jpeg;charset=utf-8;base64,${this.state.image}`} width="360" height="480"/>
                    <p>{this.props.exercise.video}</p>
                </div>
            )
        } else {
            return (
                <p>Выберите упражнение!</p>
            )
        }
    }
}

export default TrainerExercise