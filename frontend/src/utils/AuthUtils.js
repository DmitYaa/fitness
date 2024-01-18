import axios from "axios";

const url = "http://78.24.218.228:8080/auth";

function login(person, setState) {
    

    axios.post(url + "/login", {
        name: person.name,
        password: person.password
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((res) => {
        if (res.data.message === "Incorrect credentials!") {
          alert("Логин или пароль неверные!")
        } else {
          localStorage.setItem("jwt", res.data.jwt)
          setUserData(setState)
        }
      })
      .catch((error) => {
        alert("Логин или пароль неверные!")
        //console.error(error)
      })
}

function getUserData(setState) {
    const personData = {name: localStorage.getItem("name"), role: localStorage.getItem("role"), loggedIn: false}

    if ((personData.name === undefined && personData.name === null && personData.name === "undefined")
    || (personData.role === undefined && personData.role === null && personData.role === "undefined")) {
        setUserData(setState)
    } else {
        personData.loggedIn = true
        setState(personData)
    } 
}

function setUserData(setState) {
    const token = localStorage.getItem("jwt")
    if (token !== undefined && token !== null && token !== "undefined") {
        axios.get(url + "/get_person_data",{
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + token
            }
          })
          .then((res) => {
            localStorage.setItem("name", res.data.person_data.name)
            localStorage.setItem("role", res.data.person_data.role)

            if (setState !== undefined || setState !== null) {
              setState({name: localStorage.getItem("name"), role: localStorage.getItem("role"), loggedIn: true})
            }
          })
          .catch((error) => {
            console.error(error)
          })
    }
}

function registration(person) {
    axios.post(url + "/registration/user", {
        name: person.name,
        password: person.password
      }, {
        headers: {
          'Content-Type': 'application/json'
        }
      })
      .then((res) => {
        if (res.data.message === "error") {
          alert("Произошла ошибка регистрации, попробуйте ввести другой username")
        } else {
          localStorage.setItem("jwt", res.data.jwt)
          setUserData()
        }
      })
      .catch((error) => {
        console.error(error)
      })
}

function logout() {
    localStorage.setItem("jwt", undefined)
    localStorage.setItem("username", undefined)
    localStorage.setItem("role", undefined)

    window.location.reload()
    //localStorage.setItem("jwt", "eyJ0eXAiOiJKV1QiLCJhbGciOSiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIGRldGFpbHMiLCJpc3MiOiJmaXRuZXNzIiwiZXhwIjoxNjk2MTY0MDg5LCJpYXQiOjE2OTYxNjIyODksInVzZXJuYW1lIjoidGVzdCJ9.NRQcNhk7HqN71nqfR2Icv3f3nTbylXVBj2fixCY7608")
}

function checkToken(setState) {
    const token = localStorage.getItem("jwt")

    if (token !== undefined && token !== null && token !== "undefined") {
        axios.get(url + "/check_token", {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + token
            }
          })
          .then((res) => {
            console.log(res)
          })
          .catch((error) => {
            console.error(error)
          })
    }
}

function isLoggedIn(setState) {
    const token = localStorage.getItem("jwt")

    if (token !== undefined && token !== null && token !== "undefined") {
      axios.get(url + "/check", {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
          }
        })
        .then((res) => {
          setState({loggedIn: res.data.is_jwt_actual})
        })
        .catch((error) => {
          setState({loggedIn: false})
          console.log("we are here")
          console.log(error)
        })
    }
}

export {login, getUserData, setUserData, registration, logout, checkToken, isLoggedIn}