import java.util.ArrayList

fun main() {
    var str: String
    val op: Array<Char> = arrayOf('+', '-', '*', '/')
    val arrstr: ArrayList<String> = arrayListOf<String>()
    var fl: Boolean = true;
    var result = false
    var i = 0
    println("Введите выражение в префиксной форме. Для выхода введите пустую строку.")
    while (!result) { //основной цикл
        arrstr.clear(); fl = true; i = 0;   //приведение контрольных данных к виду по умолчанию.
        result = true
        str = readLine().toString()
        if (str == "") {
            break
        }
        for (c in str) { //запись выражения в массив строк
            if (c in op) { // проверяем, является ли символ операцией
                fl = true
                arrstr.add(c.toString())//добавляем символ операции в массив
            } else if (c.toInt() in 48..57) {               // проверяем, присутствует ли десятичное число в итерации
                if (fl) {
                    arrstr.add(c.toString()); fl = false    // Добавляем новый элемент числа
                } else {
                    arrstr[arrstr.lastIndex] += c.toString()  // Либо обновляем элемент числа, если число многоразрядное
                }
            } else if (c == ' ') { // если встретился пробел, устанавливаем флаг нового числа
                fl = true
            } else { // обработка ввода недопустимых символов
                result = false
                break
            }
        }
        if (result and (arrstr.size>=3)) {//если нет недопустимых символов и размер массива больше 3, пробуем перевести в инфиксную форму
            var count = 0
            while (count < arrstr.size){//убираем лишние нули перед числом
                if (!(arrstr[count][0] in op)) {
                    arrstr[count] = stoi(arrstr[count]).toString()
                }
                count++
            }
            fl = true
            while (fl) {//проходим по массиву столько раз, пока не преобразуем все простые выражения
                       //простое выражение - операция и за ней два числа или простых выражения
                i = 0
                fl = false
                while (i < arrstr.size - 2) {//в данном цикле преобразуем все простые выражения
                    if (arrstr[i][0] in op) // если встретилась операция
                        if ((arrstr[i + 1][0] !in op) and (arrstr[i + 2][0] !in op)) {//а за ней два числа
                            fl = true
                            if ((arrstr[i][0] == '+')) {//если операция +, то убираем лишние скобки
                                if (arrstr[i + 1][0] == '(') {
                                    arrstr[i + 1] = arrstr[i + 1].drop(1)
                                    arrstr[i + 1] = arrstr[i + 1].dropLast(1)
                                }
                                if (arrstr[i + 2][0] == '(') {
                                    arrstr[i + 2].drop(1)
                                    arrstr[i + 2].dropLast(1)
                                }
                            }
                            if ((arrstr[i][0] == '-')) {//если операция -, то убираем лишние скобки
                                if (arrstr[i + 1][0] == '(') {
                                    arrstr[i + 1] = arrstr[i + 1].drop(1)
                                    arrstr[i + 1] = arrstr[i + 1].dropLast(1)
                                }
                            }
                            if ((arrstr[i][0] == '/')) {//если операция /, то добавляем недостающие скобки
                                if ((arrstr[i + 2][0] != '(') and ((arrstr[i + 2].contains('*')) or (arrstr[i + 2].contains('/')))) {
                                    arrstr[i + 2] = '(' + arrstr[i + 2] + ')'
                                }
                            }
                            arrstr[i + 1] += arrstr[i] + arrstr[i + 2]//записываем простое выражение в инфиксную форму
                            if (((arrstr[i][0] == '+') or (arrstr[i][0] == '-')) and (arrstr.size != 3)) {//если + или -
                                arrstr[i + 1] = '(' + arrstr[i + 1] + ')'//ставим скобки
                            }
                            arrstr[i] = arrstr[i + 1]//записываем выражение в i-ый элемент
                            arrstr.removeAt(i + 1)//удаляем лишние элементы массива
                            arrstr.removeAt(i + 1)
                        }
                    i++
                }
            }
            if (arrstr.size != 1) {//если после преобразования не получилась одна строка, то выражение было некорректно
                println("Некорректное выражение")
                println("Что-то пошло не так, попробуйте ещё раз")
                result = false
            }
            else {
                for (c in arrstr) println(c)//выводим результат
                println("Введите выражение в префиксной форме. Для выхода введите пустую строку.")
                result = false
            }
        }
        else { //если есть недопустимые символы или размер массива меньше 3, то начинаем всё сначала
            println("Некорректное выражение")
            println("Что-то пошло не так, попробуйте ещё раз")
            result = false
        }
    }
}

fun stoi(str: String): Int{ // функция поэлементно перебирает строку собирая из неё адекватное десятичное число
    var a: Int = 0
    for (c in str){
        a = (a * 10) + (c.toInt()-48)
    }
    return a
}