import { LuSearch } from "react-icons/lu"
import React from "react"

class SearchUser extends React.Component {
    render() {
        return (
            <form ref={(el) => this.myForm = el}>
                <input placeholder="Поиск" onChange={(e) => this.setState({search: e.target.value})} />
                <button type="button" className="aside-button" onClick={() => {
                    this.myForm.reset()
                    console.log("поиск = " + this.state.search + ", надо отправить запрос поиска юзеров по данному запросу")
                }}>
                    <LuSearch />
                </button>
            </form>
        )
    }
}

export default SearchUser