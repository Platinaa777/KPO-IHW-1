package main

import main.launch.configure
import main.launch.run

fun main() {
    // registry dependencies
    val app = configure()

    run(app)
}