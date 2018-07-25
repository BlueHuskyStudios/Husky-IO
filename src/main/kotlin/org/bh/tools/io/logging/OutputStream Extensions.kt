package org.bh.tools.io.logging

import java.io.OutputStream
import java.io.PrintStream

/*
 * Streams made easier
 *
 * @author Ben Leggiero
 * @since 2017-02-17
 */


/**
 * Writes the given charSequence to this output stream.
 *
 *  * If this is a [PrintStream], its [print][PrintStream.print] method is called
 *  * Else, a writer is created by [kotlin.io.OutputStream.writer()] and written by its [write()][java.io.OutputStreamWriter.write]
 */
fun OutputStream.write(charSequence: CharSequence) {
    when (this) {
        is PrintStream -> print(charSequence)
        else -> writer().write(charSequence.toString())
    }
}
