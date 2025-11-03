package de.neusta.dddworkshop.common

sealed class ErzeugungsErgebnis<out T>

class Erzeugt<T>(val value: T) : ErzeugungsErgebnis<T>()

class UngueltigeArgumente(val fehler: List<String> = emptyList()) : ErzeugungsErgebnis<Nothing>()