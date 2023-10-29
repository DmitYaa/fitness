import React from "react"
import ReactPlayer from 'react-player'

class TrainerExercise extends React.Component {
    /*constructor(props) {
        super(props)
        this.state = {
            image: "",
            video: ""
        }

        this.getImage(1)
        this.getVideo()
        
        this.getImage = this.getImage.bind(this)
        this.getVideo = this.getVideo.bind(this)
    }*/


    /*getImage(id) {
        const token = localStorage.getItem("jwt")
            if (token !== undefined && token !== null && token !== "undefined") {
                axios.get("http://localhost:8080/trainer/get_image/" + id, {
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

    getVideo() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get("http://localhost:8080/trainer/video", {
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
    }*/
    

    render() {
        if (this.props.exercise !== null) {
            if (this.props.image !== undefined && this.props.image !== null && this.props.video !== undefined && this.props.video !== null) {
                return (
                    <div>
                        <p><b>Название:</b> {this.props.exercise.name}</p>
                        <p><b>Мышцы:</b> {this.props.exercise.muscle}</p>
                        <p><b>Описание:</b> {this.props.exercise.description}</p>
                        <p>Изображение:</p>
                        <img 
                            src={`data:image/jpeg;charset=utf-8;base64,${this.props.image}`}
                            width="360"
                            height="480"
                            alt="Изображение упражнения"/>
                        <p>Видео:</p>
                        <ReactPlayer 
                            url={this.props.video}
                            width='80%'
                            height='80%'
                            controls = {true}/>
                    </div>
                )
            } else if (this.props.image !== undefined && this.props.image !== null) {
                return (
                    <div>
                        <p><b>Название:</b> {this.props.exercise.name}</p>
                        <p><b>Мышцы:</b> {this.props.exercise.muscle}</p>
                        <p><b>Описание:</b> {this.props.exercise.description}</p>
                        <p>Изображение:</p>
                        <img 
                            src={`data:image/jpeg;charset=utf-8;base64,${this.props.image}`}
                            width="360"
                            height="480"
                            alt="Изображение упражнения"/>
                        <p>Видео: отсутствует</p>
                    </div>
                )
            } else if (this.props.video !== undefined && this.props.video !== null) {
                return (
                    <div>
                        <p><b>Название:</b> {this.props.exercise.name}</p>
                        <p><b>Мышцы:</b> {this.props.exercise.muscle}</p>
                        <p><b>Описание:</b> {this.props.exercise.description}</p>
                        <p>Изображение: отсутствует</p>
                        <p>Видео:</p>
                        <ReactPlayer 
                            url={this.props.video}
                            width='80%'
                            height='80%'
                            controls = {true}/>
                    </div>
                )
            } else {
                return (
                    <div>
                        <p><b>Название:</b> {this.props.exercise.name}</p>
                        <p><b>Мышцы:</b> {this.props.exercise.muscle}</p>
                        <p><b>Описание:</b> {this.props.exercise.description}</p>
                        <p>Изображение: отсутствует</p>
                        <p>Видео: отсутствует</p>
                    </div>
                )
            }
        } else {
            return (
                <p>Выберите упражнение!</p>
            )
        }
    }
}

export default TrainerExercise