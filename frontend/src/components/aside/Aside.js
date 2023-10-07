import React from "react"
import SearchUser from "./SearchUser"

class Aside extends React.Component {
    render() {
        if (this.props.main === "users") {
            return (
                <aside>
                    <SearchUser />
                </aside>
            )
        } else {
            return (
                <aside>
                    <label>Меню мейна</label>
                </aside>
            )
        }
    }
}

export default Aside