import React from "react"
import ReactPlayer from 'react-player'

class TrainerExercise extends React.Component {

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