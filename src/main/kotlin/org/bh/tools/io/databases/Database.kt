@file:Suppress("unused")

package org.bh.tools.io.databases

/**
 * @author Ben
 * @since 2018-07-22
 */
interface Database<Query, Contents, Self>
        where Self: Database<Query, Contents, Self> {
    fun retrieve(using: Query, result: (Contents) -> Unit)
}



interface MutableDatabase<Query, Contents, Self>: Database<Query, Contents, Self>
        where Self: MutableDatabase<Query, Contents, Self> {
    fun mutate(using: Query): Self
}
