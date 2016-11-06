package org.bh.tools.io.logging

import org.bh.tools.io.logging.LogLevel.*
import java.util.logging.Level
import java.util.logging.Level.*
import java.util.logging.Logger

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * Shorthand log functions!
 *
 * @author Kyli Rouge
 * @since 2016-11-04
 */
object Log {
    /**
     * The basis of [Log]
     *
     * @param logger  The logger which will do the grunt work of logging messages
     * @param level   The level by which to log the message
     * @param message The message to log. If `null`, `"null"` is logged.
     */
    fun log(message: Any?, logger: Logger = Logger.getGlobal(), level: LogLevel = debug)
            = logger.log(level.javaValue, message.toString())

    /**
     * Shorthand for [log] with [never] level. So... why would you call this, again?
     */
    fun x(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, never)

    /**
     * Shorthand for [log] with [finest] level
     */
    fun f3(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, finest)

    /**
     * Shorthand for [log] with [finer] level
     */
    fun f2(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, finer)

    /**
     * Shorthand for [log] with [fine] level
     */
    fun f1(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, fine)

    /**
     * Shorthand for [log] with [fine] level
     */
    fun f(message: Any?, logger: Logger = Logger.getGlobal()) = f1(message, logger)

    /**
     * Shorthand for [log] with [debug] level
     */
    fun d(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, debug)

    /**
     * Shorthand for [log] with [info] level
     */
    fun i(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, info)

    /**
     * Shorthand for [log] with [warning] level
     */
    fun w(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, warning)

    /**
     * Shorthand for [log] with [severe] level
     */
    fun s(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, severe)

    /**
     * Shorthand for [log] with [always] level
     */
    fun a(message: Any?, logger: Logger = Logger.getGlobal()) = log(message, logger, always)
}

enum class LogLevel(val javaValue: Level) {
    /**
     * When used in a single message, this means "don't log this message".
     * When used for filters, this means "log all messages".
     */
    never(ALL),

    /**
     * _Extremely_ unimportant messages. This should be used for needlessly-detailed logging.
     */
    finest(FINEST),

    /**
     * _Really_ unimportant message. This should be used for more detailed logging when needed.
     */
    finer(FINER),

    /**
     * Unimportant message. This should be used for the most general logging.
     */
    fine(FINE),

    /**
     * A message that's only useful when tracking bugs.
     */
    debug(CONFIG),

    /**
     * An informational message that the user should be able to understand. May or may not be presented to the user
     * through the UI.
     */
    info(INFO),

    /**
     * An important message that concerns the user. May be presented to the user through the UI.
     */
    warning(WARNING),

    /**
     * An urgent message about a problem that the user would want to see. Will most likely be presented to the user
     * through the UI.
     */
    severe(SEVERE),

    /**
     * When used in a message, this means "always log this message". The The user will almost definitely see this.
     * When used for filters, this means "don't log any messages".
     */
    always(OFF);

    companion object {
        /**
         * Gosh your log is huge! This is where all that needlessly-detailed info should go.
         */
        val verbose = finest
    }
}
