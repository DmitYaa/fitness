import React from "react"
import ReactPlayer from 'react-player'
import axios from "axios"

const url = "http://localhost:8080/trainer/exercise"
const imageReader = new FileReader()

class NewExercise extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            name: props.name,
            muscle: props.muscle,
            description: props.description,
            image: props.image,
            imageUrl: props.imageUrl,
            video: props.video,
            videoUrl: props.videoUrl
        }
        
        imageReader.onloadend = () => {
            this.setState({imageUrl: imageReader.result})
        }

        this.postExercise = this.postExercise.bind(this)
    }


    postExercise() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.post(url + "/new", {
                name: this.state.name,
                muscle: this.state.muscle,
                description: this.state.description
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + token
                }
            }).then((res) => {
                this.props.refresh()
                if (this.state.image !== null && this.state.image !== undefined) {
                    const formData = new FormData()
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
                        this.props.refresh()
                    }).catch((error) => {
                        console.error(error)
                    })
                }
                
                if (this.state.video !== null && this.state.video !== undefined) {
                    const formData = new FormData()
                    formData.append(
                        "video",
                        this.state.video
                    )
    
                    axios.put(url + "/video/" + res.data, formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data',
                            'Authorization': 'Bearer ' + token
                        }
                    }).then((res) => {
                        this.props.refresh()
                    }).catch((error) => {
                        console.error(error)
                    })
                }
            }).catch((error) => {
                console.error(error)
            })
        }
    }

    render() {
        return (
            <div>
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
                        <div>
                            <b>Изображение:</b>
                            <input name="image" type="file" onChange={(e) => {
                                this.setState({image: e.target.files[0]})
                                imageReader.readAsDataURL(e.target.files[0])
                            }}/>
                            <img 
                                src={this.state.imageUrl ? this.state.imageUrl : "no-image.png"}
                                width='80%'
                                height='80%'
                                alt="Изображение упражнения"
                            />
                        </div>
                        <div>
                            <b>Видео:</b>
                            <input name="video" type="file" onChange={(e) => {
                                this.setState({video: e.target.files[0]})
                                var url = URL.createObjectURL(e.target.files[0]);
                                this.setState({videoUrl: url})
                            }}/>
                            <ReactPlayer 
                                url={this.state.videoUrl ? this.state.videoUrl : "no-video.mp4"}
                                width='80%'
                                height='80%'
                                controls = {true}/>
                        </div>
                        
                        <button type="button" onClick={() => this.postExercise()}>Добавить упражнение</button>
                    </form>
            </div>
        )
    }
}

export default NewExercise