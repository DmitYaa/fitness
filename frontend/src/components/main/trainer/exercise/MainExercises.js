import React from "react"
import Button from "../../../utils/Button"
import axios from "axios";

const url = "http://localhost:8080/trainer";

class MainExercises extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
        }

        this.test = this.test.bind(this)
    }

    test() {
        const token = localStorage.getItem("jwt")
        if (token !== undefined && token !== null && token !== "undefined") {
            axios.get(url + "/exercises", {
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
              })
              .then((res) => {
                console.log(res.data)
              })
              .catch((error) => {
                console.error(error)
              })
        }
    }


    render() {
        return (
            <main>
                <h1 className="title">Упражнения</h1>
                
                <Button name={"Get trainer"} doIt={this.test}/>


                <div className="Exercises_list">
                    {/*левая часть с таблицей упражнений*/}
                </div>
                <div>
                    {/*правая часть с выбранным упражнением*/}

                    {/*кнопка открытия упражнения как оно выглядит у пользователя!*/}
                </div>
            </main>
        )
    }
}

export default MainExercises