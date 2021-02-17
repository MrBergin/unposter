package mr.bergin.unposter.model

inline fun <reified T> Iterable<*>.noneAre() = filterIsInstance<T>().none()