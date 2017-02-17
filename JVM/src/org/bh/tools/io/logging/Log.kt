@file:Suppress(
        "NOTHING_TO_INLINE", // These are inlined so the debugger properly reflects the method/class where logging took place
        "unused"
)

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
class Log(val defaultLogger: Logger) {
    /**
     * The basis of [Log]; logs the given message using the given logger at the given log level
     *
     * @param message The message to log. If `null`, `"null"` is logged
     * @param logger  _optional_ - The logger which will do the grunt work of logging messages; defaults to [defaultLogger]
     * @param level   _optional_ - The level by which to log the message; defaults to [debug][LogLevel.debug]
     */
    inline fun print(message: Any?, logger: Logger = defaultLogger, level: LogLevel = debug)
            = logger.log(level.javaValue, message.toString())


    /**
     * Shorthand for [print] with [never] level. So... why would you call this, again?
     */
    inline fun never(message: Any?, logger: Logger = defaultLogger) = print(message, logger, never)

    /**
     * Shorthand for [print] with [never] level. So... why would you call this, again?
     */
    inline fun x(message: Any?, logger: Logger = defaultLogger) = never(message, logger)


    /**
     * Shorthand for [print] with [verbose][LogLevel.verbose] level
     */
    inline fun verbose(message: Any?, logger: Logger = defaultLogger) = print(message, logger, LogLevel.verbose)

    /**
     * Shorthand for [print] with [verbose][LogLevel.verbose] level
     */
    inline fun v(message: Any?, logger: Logger = defaultLogger) = finest(message, logger)


    /**
     * Shorthand for [print] with [finest][LogLevel.finest] level
     */
    inline fun finest(message: Any?, logger: Logger = defaultLogger) = print(message, logger, finest)

    /**
     * Shorthand for [print] with [finest][LogLevel.finest] level
     */
    inline fun f3(message: Any?, logger: Logger = defaultLogger) = finest(message, logger)


    /**
     * Shorthand for [print] with [finer][LogLevel.finer] level
     */
    inline fun finer(message: Any?, logger: Logger = defaultLogger) = print(message, logger, finer)

    /**
     * Shorthand for [print] with [finer][LogLevel.finer] level
     */
    inline fun f2(message: Any?, logger: Logger = defaultLogger) = finer(message, logger)


    /**
     * Shorthand for [print] with [fine][LogLevel.fine] level
     */
    inline fun fine(message: Any?, logger: Logger = defaultLogger) = print(message, logger, fine)

    /**
     * Shorthand for [print] with [fine][LogLevel.fine] level
     */
    inline fun f1(message: Any?, logger: Logger = defaultLogger) = fine(message, logger)

    /**
     * Shorthand for [print] with [fine][LogLevel.fine] level
     */
    inline fun f(message: Any?, logger: Logger = defaultLogger) = fine(message, logger)


    /**
     * Shorthand for [print] with [debug][LogLevel.debug] level
     */
    inline fun debug(message: Any?, logger: Logger = defaultLogger) = print(message, logger, debug)

    /**
     * Shorthand for [print] with [debug][LogLevel.debug] level
     */
    inline fun d(message: Any?, logger: Logger = defaultLogger) = debug(message, logger)


    /**
     * Shorthand for [print] with [info][LogLevel.info] level
     */
    inline fun info(message: Any?, logger: Logger = defaultLogger) = print(message, logger, info)

    /**
     * Shorthand for [print] with [info][LogLevel.info] level
     */
    inline fun i(message: Any?, logger: Logger = defaultLogger) = info(message, logger)


    /**
     * Shorthand for [print] with [warning][LogLevel.warning] level
     */
    inline fun warning(message: Any?, logger: Logger = defaultLogger) = print(message, logger, warning)

    /**
     * Shorthand for [print] with [warning][LogLevel.warning] level
     */
    inline fun warn(message: Any?, logger: Logger = defaultLogger) = warning(message, logger)

    /**
     * Shorthand for [print] with [warning][LogLevel.warning] level
     */
    inline fun w(message: Any?, logger: Logger = defaultLogger) = warning(message, logger)


    /**
     * Shorthand for [print] with [severe][LogLevel.error] level
     */
    inline fun error(message: Any?, logger: Logger = defaultLogger) = print(message, logger, LogLevel.error)

    /**
     * Shorthand for [print] with [severe][LogLevel.error] level
     */
    inline fun e(message: Any?, logger: Logger = defaultLogger) = severe(message, logger)


    /**
     * Shorthand for [print] with [severe][LogLevel.severe] level
     */
    inline fun severe(message: Any?, logger: Logger = defaultLogger) = print(message, logger, severe)

    /**
     * Shorthand for [print] with [severe][LogLevel.severe] level
     */
    inline fun s(message: Any?, logger: Logger = defaultLogger) = severe(message, logger)


    /**
     * Shorthand for [print] with [always][LogLevel.always] level
     */
    inline fun always(message: Any?, logger: Logger = defaultLogger) = print(message, logger, always)

    /**
     * Shorthand for [print] with [always][LogLevel.always] level
     */
    inline fun a(message: Any?, logger: Logger = defaultLogger) = always(message, logger)
}

val log = Log(Logger.getGlobal())



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
        @JvmStatic
        inline val verbose get() = finest


        /**
         * An urgent message about a problem that the user would want to see. Will most likely be presented to the user
         * through the UI.
         */
        @JvmStatic
        inline val error get() = severe
    }
}
