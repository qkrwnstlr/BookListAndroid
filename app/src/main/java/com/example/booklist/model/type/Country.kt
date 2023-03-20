package com.example.booklist.model.type

enum class Country {
  KOREA {
    override fun toString(): String = "KOREA"
  },
  JAPAN {
    override fun toString(): String = "JAPAN"
  },
  ETC {
    override fun toString(): String = "ETC"
  };

  companion object {
    fun fromString(string: String): Country {
      return when (string) {
        "KOREA" -> KOREA
        "JAPAN" -> JAPAN
        else -> ETC
      }
    }
  }

  abstract override fun toString(): String
}