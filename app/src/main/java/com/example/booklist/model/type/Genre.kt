package com.example.booklist.model.type

enum class Genre {
  COMIC {
    override fun toString(): String = "COMIC"
  },
  SCIENCE {
    override fun toString(): String = "SCIENCE"
  },
  HOBBY {
    override fun toString(): String = "HOBBY"
  },
  HISTORY {
    override fun toString(): String = "HISTORY"
  },
  ESSAY {
    override fun toString(): String = "ESSAY"
  },
  SELF_HELP {
    override fun toString(): String = "SELF-HELP"
  },
  NOVEL {
    override fun toString(): String = "NOVEL"
  },
  ETC {
    override fun toString(): String = "ETC"
  };

  companion object {
    fun fromString(string: String): Genre =
      when (string) {
        "COMIC" -> COMIC
        "SCIENCE" -> SCIENCE
        "HOBBY" -> HOBBY
        "HISTORY" -> HISTORY
        "SELF_HELP" -> SELF_HELP
        "ESSAY" -> ESSAY
        "NOVEL" -> NOVEL
        else -> ETC
      }
  }

  abstract override fun toString(): String
}