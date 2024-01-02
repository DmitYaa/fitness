import React from "react"
import NewExercise from "./NewExercise"
import Button from "../../../utils/Button"
import EditeExercise from "./EditeExercise"


class ExercisePanel extends React.Component {
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
    }

    render() {
        if (this.props.exercise === null || this.props.exercise === undefined) {
            return (
                <div className="exercise_panel">
                    <NewExercise refresh={this.props.refresh}/>
                </div>
            )
        } else {
            return (
                <div className="exercise_panel">
                    <EditeExercise 
                        exercise={this.props.exercise} 
                        image={this.props.image} 
                        video={this.props.video} 
                        refresh={this.props.refresh}/>
                    {/* <TrainerExercise exercise={this.props.exercise} image={this.props.image} video={this.props.video}/> */}
                    <Button name={"Просмотр упражнения"} doIt={() => console.log(this.props.exercise)}/>
                </div>
            )
        }        
    }
}

export default ExercisePanel