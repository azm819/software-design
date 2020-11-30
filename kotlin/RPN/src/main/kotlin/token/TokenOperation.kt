package token

import java.util.function.BiFunction

class TokenOperation(
    private val priority: Int,
    private val operation: BiFunction<Int, Int, Int>,
    private val textOperation: String
) : Token {

    override fun textRepresentation(): String {
        return textOperation
    }

    fun getPriority(): Int {
        return priority
    }

    fun getOperation(): BiFunction<Int, Int, Int> {
        return operation
    }

    companion object {
        fun plus(): TokenOperation {
            return TokenOperation(0, { x, y -> x + y }, "+")
        }

        fun minus(): TokenOperation {
            return TokenOperation(0, { x, y -> x - y }, "-")
        }

        fun mul(): TokenOperation {
            return TokenOperation(1, { x, y -> x * y }, "*")
        }

        fun div(): TokenOperation {
            return TokenOperation(1, { x, y -> x / y }, "/")
        }
    }
}
