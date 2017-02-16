package org.bh.tools.io.setup

import org.bh.tools.base.collections.filterMap

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A complete command-line argument.
 *
 * Attempts to follow guidelines at http://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html
 *
 * @author Kyli Rouge
 * @since 2016-11-02
 */
data class CompleteCommandLineArg<out ActionOutput>(
        override val singleCharacterArgument: Char,
        override val fullTextArgument: String,
        override val description: String,
        override val action: (Array<String>) -> ActionOutput
) : SingleCharacterCommandLineArg<ActionOutput>, FullTextCommandLineArg<ActionOutput> {
    override val regex: Regex
        get() = Regex("(${super<FullTextCommandLineArg>.regex.pattern}" +
                "|${super<SingleCharacterCommandLineArg>.regex.pattern})")
}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A single-character command-line argument. This is meant to combine with other single-character arguments so that
 * `-a -b -c` is considered the same as `-abc`
 *
 * Attempts to follow guidelines at http://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html
 *
 * @author Kyli Rouge
 * @since 2016-11-02
 */
interface SingleCharacterCommandLineArg<out ActionOutput> : CommandLineArg<ActionOutput> {
    /**
     * The argument as a single character. This might be combined with others, so `-a -b -c` is the same as `-abc`.
     * Whitespace is not supported.
     */
    val singleCharacterArgument: Char

    /**
     * Matches a singular hyphen, zero or more non-whitespace characters, this command's character, and zero or more
     * non-whitespace characters
     */
    override val regex: Regex
        get() = Regex("^${prefix}[^-\\s]*?$singleCharacterArgument[^-\\s]*?\$")

    companion object {
        val prefix = "-"
    }
}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A full-text command-line argument. This is meant to stand alone so that `--argument` can only mean one thing
 *
 * Attempts to follow guidelines at http://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html
 *
 * @author Kyli Rouge
 * @since 2016-11-02
 */
interface FullTextCommandLineArg<out ActionOutput> : CommandLineArg<ActionOutput> {
    /**
     * The argument as a full-text word. Whitespace is not supported.
     */
    val fullTextArgument: String

    /**
     * Matches two hyphens and the full text argument
     */
    override val regex: Regex
        get() = Regex("^${prefix}$fullTextArgument\$")

    companion object {
        val prefix = SingleCharacterCommandLineArg.prefix + SingleCharacterCommandLineArg.prefix
    }
}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * Properties that all command-line arguments share
 *
 * Attempts to follow guidelines at http://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html
 *
 * @author Kyli Rouge
 * @since 2016-11-02
 */
interface CommandLineArg<out ActionOutput> {
    /**
     * A long, plain-text, human-readable description of what this argument does
     */
    val description: String

    /**
     * The action to be taken if this argument is passed.
     *
     * This also parses any parameters that come after the argument. For instance `-o /usr/foo.txt /var/bar.gz` would
     * pass an array like: `["/usr/foo.txt", "/var/bar.gz"]`
     *
     * @param parameters Any parameters that came after this argument
     */
    val action: (parameters: Array<String>) -> ActionOutput

    /**
     * A regular expression that will match this argument
     */
    val regex: Regex
}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A collection of command-line arguments, sorted by priority
 *
 * @author Kyli Rouge
 * @since 2016-11-02
 */
abstract class CommandlineArgCollection {
    abstract val args: Array<CommandLineArg<*>>

    private val _parser: CommandLineArgParser by lazy { CommandLineArgParser(this) }

    fun parse(args: Array<String>) = _parser.parseArgs(args)
}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * Uses a collection of valid arguments to parse a set of arguments the user has input.
 *
 * Attempts to follow guidelines at http://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html
 *
 * @author Kyli Rouge
 * @since 2016-11-04
 */
open class CommandLineArgParser(val expectedArgs: CommandlineArgCollection) {
    /**
     * Parses the given strings into arguments. Does not support argument parameters like `-o /path/to/file.txt`.
     *
     * TODO: Support argument parameters
     */
    fun parseArgs(userArgs: Array<String>): List<CommandLineArg<*>> {
        return userArgs.filterMap { arg ->
            val match = expectedArgs.args.firstOrNull { it.regex.matches(arg) }
            Pair(match != null, { match })
        }.requireNoNulls()
    }
}

/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * Uses a collection of valid arguments to process (parse and call actions on) a set of arguments the user has input.
 *
 * Attempts to follow guidelines at http://www.gnu.org/software/libc/manual/html_node/Argument-Syntax.html
 *
 * @author Kyli Rouge
 * @since 2016-11-04
 */
open class CommandLineArgProcessor(val expectedArgs: CommandlineArgCollection) {
    fun process(userArgs: Array<String>) {
        this.expectedArgs.parse(userArgs).forEach { it.action(emptyArray()) }
    }
}
