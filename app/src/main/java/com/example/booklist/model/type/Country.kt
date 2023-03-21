package com.example.booklist.model.type

enum class Country {
  KOREA {
    override fun toString(): String = "KOREA"
  },
  JAPAN {
    override fun toString(): String = "JAPAN"
  },
  NONE {
    override fun toString(): String = ""
  },
  ETC {
    override fun toString(): String = "ETC"
  };

  companion object {
    fun fromString(string: String): Country {
      return when (string) {
        "KOREA" -> KOREA
        "JAPAN" -> JAPAN
        "" -> NONE
        else -> ETC
      }
    }
  }

  abstract override fun toString(): String
}