@file:Suppress("unused")

package org.bh.tools.io.setup

import org.bh.tools.base.abstraction.Integer
import org.bh.tools.base.collections.extensions.*
import org.bh.tools.base.func.tuple
import org.bh.tools.base.math.max
import org.bh.tools.io.logging.log
import org.bh.tools.io.logging.write
import java.io.OutputStream
import java.util.regex.Pattern

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
open class CompleteCommandLineArg<out ActionOutput>(
        override val singleCharacterArgument: Char,
        override val fullTextArgument: String,
        override val description: String,
        override val action: (Array<String>) -> ActionOutput
) : SingleCharacterCommandLineArg<ActionOutput>,
        FullTextCommandLineArg<ActionOutput> {
    override val regex by lazy {
        Regex("(${super<FullTextCommandLineArg>.regex.pattern}" +
                "|${super<SingleCharacterCommandLineArg>.regex.pattern})")
    }

    override val allArgumentStrings by lazy {
        listOf(
                SingleCharacterCommandLineArg.prefix + singleCharacterArgument,
                FullTextCommandLineArg.prefix + fullTextArgument
        )
    }
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
        get() = Regex("^$prefix[^$prefix\\s]*?${Pattern.quote(singleCharacterArgument.toString())}[^$prefix\\s]*?\$")


    override val allArgumentStrings: Collection<String> get() = listOf("$prefix$singleCharacterArgument")

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
        get() = Regex("^$prefix$fullTextArgument\$")


    override val allArgumentStrings: Collection<String> get() = listOf("$prefix$fullTextArgument")


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


    @Suppress("KDocUnresolvedReference")
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


    /**
     * A collection of all arguments as strings, like `["-a <subarg>", "--argument <subarg>"]`
     */
    val allArgumentStrings: Collection<String>



    companion object Defaults {

        /**
         * A standard help argument (`-?` or `--help`).
         *
         * @param executableName The name of the executable which the user types in the command line
         * @param allArgs        The arguments that should show up in `help`
         * @param rightMargin    _optional_ = The character width of the console/terminal
         * @param stream         _optional_ = The stream to which the help text will be printed
         */
        class HelpArg(executableName: String,
                      allArgs: Collection<CommandLineArg<*>>,
                      rightMargin: Integer = 80,
                      stream: OutputStream = System.out
        ) : CompleteCommandLineArg<Unit>(
                singleCharacterArgument = '?',
                fullTextArgument = "help",
                description = "Display this message",
                action = {}
        ) {

            private val minimumMargin = rightMargin / 3

            override val action: (Array<String>) -> Unit = {
                val output = StringBuilder("usage: ").append(executableName).append(" ")
                val allArgsIncludingThisOne = allArgs.toList() + this

                val indent = output.length
                val argumentSummaryWidth = max(rightMargin - indent, minimumMargin)
                val argumentSummaryLeadingPadding = " ".repeat(indent)

                val argumentStrings = allArgsIncludingThisOne
                        .map { it.allArgumentStrings.toString(prefix = "[", glue = " | ", suffix = "]") }

                val argumentSummaryLines = MutableList(StringBuilder())

                argumentStrings.forEach { argumentString ->
                    val currentLine = argumentSummaryLines.last
                    if (currentLine.length + argumentString.length + 1 > argumentSummaryWidth) {
                        argumentSummaryLines.add(StringBuilder(argumentString))
                    } else {
                        currentLine.append(" ").append(argumentString)
                    }
                }

                argumentSummaryLines.forEach { line ->
                    output.append(line).append("\r\n").append(argumentSummaryLeadingPadding)
                }

                log.debug("TODO: More help")

                stream.write(output.append("\r\n\r\n").toString())
            }
        }
    }
}


/**
 * Copyright BHStudios ©2016 BH-1-PS. Made for Snek.
 *
 * A collection of command-line arguments, sorted by priority
 *
 * @author Kyli Rouge
 * @since 2016-11-02
 */
abstract class CommandlineArgCollection<out ActionOutput, Argument: CommandLineArg<ActionOutput>> {
    abstract val args: Array<Argument>

    private val _parser: CommandLineArgParser<ActionOutput, Argument> by lazy { CommandLineArgParser(this) }

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
open class CommandLineArgParser<out ActionOutput, Argument: CommandLineArg<ActionOutput>>(
        val expectedArgs: CommandlineArgCollection<ActionOutput, Argument>
) {
    /**
     * Parses the given strings into arguments. Does not support argument parameters like `-o /path/to/file.txt`.
     *
     * TODO: Support argument parameters
     */
    fun parseArgs(userArgs: Array<String>): List<Argument> {
        return userArgs.filterMap { arg ->
            val match = expectedArgs.args.firstOrNull { it.regex.matches(arg) }
            tuple(match != null, { match })
        }.filterNotNull()
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
open class CommandLineArgProcessor<out ActionOutput, Argument: CommandLineArg<ActionOutput>>(
        val expectedArgs: CommandlineArgCollection<ActionOutput, Argument>
) {
    fun process(userArgs: Array<String>) {
        this.expectedArgs.parse(userArgs).forEach { it.action(emptyArray()) }
    }
}
