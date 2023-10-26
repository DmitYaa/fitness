import React from "react"

class Exercise extends React.Component {
    render() {
        return (
            <div>
                <div> {/* div верхней половины */}
                    <div>
                        <div> {/* кнопка назад */}</div>
                        <div> {/* кнопка пропустить */}</div>
                    </div>
                    <div> {/* название упражнения */}</div>

                    <div>
                        <div> {/* прогресс бар */}
                            <div> {/* видео упражнения */}</div>
                        </div>
                    </div>
                </div>

                <div> {/* div нижней половины */}
                    <div>
                        <div> {/* Необходимое кол-во повторений в подходе */}</div>
                        <div> {/* Необходимое кол-во подходов */}</div>
                    </div>
                    <div>
                        <div> {/* Место для ввода кол-ва повторений */}</div>
                        <div> {/* Место для ввода кол-ва килограмм */}</div>
                        <div> {/* Кнопка ввода */}</div>
                    </div>
                    <div>
                        <div> {/* История выполнения */}
                            <div>{/* кнопка истории повторений в других тренировках (открывается скрол панель со всеми повторениями с датой и количеством повторений и номером подхода) */}</div>
                            <div> {/* кол-во повторений */}</div>
                            <div> {/* кол-ва килограмм */}</div>
                        </div>
                    </div>
                    <div> {/* начать тренеровку / купить программу */}</div>
                </div>

                <div> {/* панель редактирования */}
                    
                </div>
            </div>
        )
    }
}

export default Exercise