import React from "react"
import ReactPlayer from 'react-player'
import axios from "axios"

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
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.put(url + "/edite_exercise", {
                id: this.props.exercise.id,
                name: this.state.name !== null ? this.state.name : this.props.exercise.name,
                muscle: this.state.muscle !== null ? this.state.muscle : this.props.exercise.muscle,
                description: this.state.description !== null ? this.state.description : this.props.exercise.description
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            }).then((res) => {
                if (this.state.image !== null) {
                    const formData = new FormData()
                    formData.append(
                        "image",
                        this.state.image
                    )
    
                    axios.put(url + "/image/" + this.props.exercise.id, formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                            'Authorization': 'Bearer ' + token
                        }
                    }).then((res) => {
                        this.props.refresh()
                        this.setState({image: null,
                            imageUrl: null})
                    }).catch((error) => {
                        console.error(error)
                    })
                }

                if (this.state.video !== null) {
                    const formData = new FormData()
                    formData.append(
                        "video",
                        this.state.video
                    )
    
                    axios.put(url + "/video/" + this.props.exercise.id, formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                            'Authorization': 'Bearer ' + token
                        }
                    }).then((res) => {
                        this.props.refresh()
                        this.setState({video: null,
                            videoUrl: null})
                    }).catch((error) => {
                        console.error(error)
                    })
                }

                this.props.refresh()

                this.setState({name: null,
                    muscle: null,
                    description: null})
            }).catch((error) => {
                console.error(error)
            })
        }
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