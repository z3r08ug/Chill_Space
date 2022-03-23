package com.z3r0_8ug.ui_common.framework.ui.navigation

import android.os.Bundle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.util.*

object NavArgumentType {
  @JvmField
  val Uuid: NavType<UUID?> = object : NavType<UUID?>(true) {
    override val name: String
      get() = "uuid"

    override fun put(bundle: Bundle, key: String, value: UUID?) {
      bundle.putSerializable(key, value)
    }

    override fun get(bundle: Bundle, key: String): UUID? {
      return bundle[key] as? UUID?
    }

    override fun parseValue(value: String): UUID {
      return UUID.fromString(value)
    }
  }

  val uuidList: NavType<List<UUID>> = object: NavType<List<UUID>>(false) {
    override val name: String
      get() = "uuidList"

    override fun put(bundle: Bundle, key: String, value: List<UUID>) {
      val array = value.map { it.toString() }.toTypedArray()
      bundle.putStringArray(key, array)
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(bundle: Bundle, key: String): List<UUID> {
      val array = bundle[key] as Array<String>
      return array.map { UUID.fromString(it) }
    }

    override fun parseValue(value: String): List<UUID> {
      if (value.isBlank()) {
        return emptyList()
      }

      if (!value.startsWith('[') || !value.endsWith(']')) {
        throw IllegalArgumentException("List values must be defined between [ and ]")
      }

      val trimmed = value.substring(1, value.length -1)
      if (trimmed.isBlank()) {
        return emptyList()
      }

      val stringIds = trimmed.split(",")
      return stringIds.map {
        UUID.fromString(it.trim())
      }
    }
  }

  class Enumeration<T : Enum<T>>(
    private val type: Class<T>
  ) : NavType<T?>(true) {

    override val name: String
      get() = type.name

    override fun put(bundle: Bundle, key: String, value: T?) {
      type.cast(value)
      bundle.putSerializable(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(bundle: Bundle, key: String): T? {
      return bundle[key] as? T?
    }

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun parseValue(value: String): T {
      return type.enumConstants.firstOrNull {
        it.name.equals(value, true)
      } ?: throw IllegalArgumentException("Enum value \"$value\" cannot be converted to type ${type.name}")
    }
  }
}

fun <T> namedNavArg(
  name: String,
  type: NavType<T>,
  nullable: Boolean = false
): NamedNavArgument {
  return navArgument(name) {
    this.type = type
    this.nullable = nullable

    if (nullable) {
      this.defaultValue = null
    }
  }
}

fun <T> namedNavArg(
  name: String,
  type: NavType<T>,
  defaultValue: T,
  nullable: Boolean = false
): NamedNavArgument {
  return navArgument(name) {
    this.type = type
    this.nullable = nullable
    this.defaultValue = defaultValue
  }
}
