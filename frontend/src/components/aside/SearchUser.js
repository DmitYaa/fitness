import { MdSearch } from "react-icons/lu"
import React from "react"

class SearchUser extends React.Component {
    render() {
        return (
            <form ref={(el) => this.myForm = el}>
                <input placeholder="Поиск" onChange={(e) => this.setState({search: e.target.value})} />
                <button className="header-button" onClick={() => {
                    this.myForm.reset()
                    console.log("надо отправить запрос поиска юзеров по данному запросу")
                }}>
                    <MdSearch />
                </button>
            </form>
        )
    }
}

export default SearchUser