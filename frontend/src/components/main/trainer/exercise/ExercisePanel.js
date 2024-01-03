import React from "react"
import NewExercise from "./NewExercise"
import EditeExercise from "./EditeExercise"


class ExercisePanel extends React.Component {
    render() {
        if (this.props.exercise === null || this.props.exercise === undefined) {
            return (
                <div className="exercise_panel">
                    <NewExercise 
                        refresh={this.props.refresh}/>
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
                </div>
            )
        }        
    }
}

export default ExercisePanel