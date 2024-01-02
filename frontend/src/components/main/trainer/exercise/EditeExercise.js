import React from "react"
import ReactPlayer from 'react-player'

const url = "http://localhost:8080/trainer"
const imageReader = new FileReader()

class EditeExercise extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            name: null,
            muscle: null,
            description: null,
            image: null,
            imageUrl: null,
            video: null,
            videoUrl: null
        }
        
        imageReader.onloadend = () => {
            this.setState({imageUrl: imageReader.result})
        }

        this.postEditExercise = this.postEditExercise.bind(this)
    }

    postEditExercise() {
        console.log("тут надо добавить все изменения в упражнение! " + url)
    }

    render() {
        if (this.props.image === undefined || this.props.image === null || this.props.video === undefined || this.props.video === null) {
            return (
                <div>
                    <p>Упражнение загружается</p>
                </div>
            )
        } else {
            return (
                <div>
                    <form>
                        <div>
                            <b>Название:</b>
                            <p>{this.props.exercise.name}</p>
                            <input 
                                name="name" 
                                type="text" 
                                placeholder="введите новое название"
                                onChange={(e) => this.setState({name: e.target.value})}/>
                        </div>
                        <div>
                            <b>Мышцы:</b>
                            <p>{this.props.exercise.muscle}</p>
                            <textarea 
                                name="muscle" 
                                placeholder="введите новое значение мускул"
                                onChange={(e) => this.setState({muscle: e.target.value})}></textarea>
                        </div>
                        <div>
                            <b>Описание:</b>
                            <p>{this.props.exercise.description}</p>
                            <textarea 
                                name="description" 
                                placeholder="введите новое значение описания"
                                onChange={(e) => this.setState({description: e.target.value})}></textarea>
                        </div>
                        <div>
                            <b>Изображение:</b>
                            <input name="image" type="file" onChange={(e) => {
                                this.setState({image: e.target.files[0]})
                                imageReader.readAsDataURL(e.target.files[0])
                            }}/>
                            <img 
                                src={this.state.imageUrl !== null ? this.state.imageUrl : `data:image/jpeg;charset=utf-8;base64,${this.props.image}`}
                                width="80%"
                                height="80%"
                                alt="Изображение упражнения"/>
                        </div>
                        <div>
                            <b>Видео:</b>
                            <input name="video" type="file" onChange={(e) => {
                                this.setState({video: e.target.files[0]})
                                var url = URL.createObjectURL(e.target.files[0]);
                                this.setState({videoUrl: url})
                            }}/>
                            <ReactPlayer 
                                url={this.state.videoUrl !== null ? this.state.videoUrl : this.props.video}
                                width='80%'
                                height='80%'
                                controls = {true}/>
                        </div>
                        
                        <button type="button" onClick={() => this.postEditExercise()}>Изменить упражнение</button>
                    </form>
                </div>
            )
        }  
    }
}

export default EditeExercise