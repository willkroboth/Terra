package com.dfsek.terra

import java.io.File
import java.util.function.Predicate
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.extra
import kotlin.streams.asStream


/**
 * Configures a directory where addons will be put.
 */
fun Project.addonDir(dir: File, task: Task) {
    task.doFirst {
        dir.parentFile.mkdirs()
        matchingAddons(dir) {
            it.name.startsWith("Terra-") // Assume everything that starts with Terra- is a core addon.
        }.forEach {
            println("Deleting old addon: " + it.absolutePath)
            it.delete()
        }
        project(":common:addons").subprojects.forEach { addonProject ->
            val jar = (addonProject.tasks.named("jar").get() as Jar)
            
            val boot = if (addonProject.project.extra.has("bootstrap") && addonProject.project.extra.get("bootstrap") as Boolean) "bootstrap/" else ""
            val target = File(dir, boot + jar.archiveFileName.get())
            
            val base = "${jar.archiveBaseName.get()}-${project.version}"
            
            println("Copying addon ${jar.archiveFileName.get()} to ${target.absolutePath}. Base name: $base")
            
            jar.archiveFile.orNull?.asFile?.copyTo(target)
        }
    }
}

fun matchingAddons(dir: File, matcher: Predicate<File>): Set<File> {
    val matching = HashSet<File>()
    dir.walk().asStream().filter(matcher).forEach(matching::add)
    return matching
}
