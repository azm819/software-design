package token

class TokenNumber(private val n: Int) : Token {
    override fun textRepresentation(): String {
        return n.toString()
    }

    fun getValue(): Int {
        return n
    }
}
