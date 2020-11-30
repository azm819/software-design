package tokenizer

import state.State
import state.StateEof
import state.StateSymbol
import token.Token

class Tokenizer(
    private var state: State = StateSymbol(),
    private val tokens: MutableList<Token> = mutableListOf()
) {

    fun processCharacter(c: Char) {
        state.start(this, c)
    }

    fun processInput(input: String) {
        for (element in input) {
            processCharacter(element)
        }
        state.finish(this)
        state = StateEof()
    }

    fun setState(state: State) {
        this.state = state
    }

    fun getTokens(): MutableList<Token> {
        return tokens
    }
}
