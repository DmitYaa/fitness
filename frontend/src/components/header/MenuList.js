import React from "react"
import MenuElement from "./MenuElement"
import {logout} from "../../utils/AuthUtils"

class MenuList extends React.Component {

    role = localStorage.getItem("role")
    
    render() {
        if (this.role === "ROLE_ADMIN") {
            return (
                <ul className="menu_list">
                    <MenuElement buttonName="Список пользователей" buttonFunction={() => {this.props.setMain("users")}}/>
                    <MenuElement buttonName="Выход" buttonFunction={logout}/>
                </ul>
            )
        } else if (this.role === "ROLE_TRAINER") {
            return (
                <ul className="menu_list">
                    <MenuElement buttonName="Программы" buttonFunction={() => {console.log("Нажали на Программы")}}/>
                    <MenuElement buttonName="Тренеровки" buttonFunction={() => {console.log("Нажали на Тренеровки")}}/>
                    <MenuElement buttonName="Упражнения" buttonFunction={() => {this.props.setMain("exercises")}}/>
                    <MenuElement buttonName="Выход" buttonFunction={logout}/>
                </ul>
            )
        } else if (this.role === "ROLE_USER") {
            return (
                <ul className="menu_list">
                    <MenuElement buttonName="Профиль" buttonFunction={() => {console.log("Нажали на Профиль")}}/>
                    <MenuElement buttonName="Выход" buttonFunction={logout}/>
                </ul> 
            )
        } else if (this.role === "ROLE_PREMIUM") {
            return (
                <ul className="menu_list">
                    <MenuElement buttonName="Профиль" buttonFunction={() => {console.log("Нажали на Профиль")}}/>
                    <MenuElement buttonName="Выход" buttonFunction={logout}/>
                </ul>
            )
            
        }
    }
}

export default MenuList