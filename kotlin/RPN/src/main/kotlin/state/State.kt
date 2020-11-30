package state

import tokenizer.Tokenizer

interface State {
    fun start(tokenizer: Tokenizer, c: Char) {
        throw IllegalStateException("Not implemented")
    }

    fun finish(tokenizer: Tokenizer) {}
}
