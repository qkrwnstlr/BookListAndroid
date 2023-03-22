package com.example.booklist.model.type

enum class Country {
  ALL {
    override fun toString(): String = "ALL"
  },
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
        "ALL" -> ALL
        "KOREA" -> KOREA
        "JAPAN" -> JAPAN
        else -> ETC
      }
    }
  }

  abstract override fun toString(): String
}