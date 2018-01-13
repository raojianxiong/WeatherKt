package jianxiongrao.weatherkt.domain.commands

interface Command<out T> {
    fun execute(): T
}