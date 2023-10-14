import { LuSearch } from "react-icons/lu"
import React from "react"

class SearchUser extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            search: ""
        }
    }

    render() {
        return (
            <form className="searhForm" ref={(el) => this.myForm = el}>
                <input placeholder="Поиск" onChange={(e) => this.setState({search: e.target.value})} />
                <button type="button" onClick={() => {
                    this.myForm.reset()
                    this.props.getUsers({searchString: this.state.search})
                    console.log("поиск = " + this.state.search + ", надо отправить запрос поиска юзеров по данному запросу")
                }}>
                    <LuSearch />
                </button>
            </form>
        )
    }
}

export default SearchUser