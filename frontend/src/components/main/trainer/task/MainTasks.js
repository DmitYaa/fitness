import React from "react"

class MainTasks extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            
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
                                <th>Описание</th>
                                <th>Название упражнения</th>
                                <th>Тип</th>
                                <th>Кол-во</th>
                            </tr>
                        </thead>
                        {/* <tbody>
                            {this.state.exercises.map((el) => (
                                <tr key={el.id} onClick={() => this.setExercise(el)}>
                                    <th>{el.name}</th>
                                    <th>{el.muscle}</th>
                                    <th>{el.description}</th>
                                </tr>
                            ))}
                        </tbody> */}
                    </table>
                </div>
            </main>
        )
    }
}

export default MainTasks