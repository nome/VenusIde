package de.sciencecomputing.venuside

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFileSystemItem
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import de.sciencecomputing.venuside.psi.ContextAssignment
import de.sciencecomputing.venuside.psi.ContextFile
import java.util.*

/**
 * Utilities for dealing with an scVENUS style domain/... directory tree
 */

fun findContextVariable(project: Project, context: String, variable: String): List<ContextAssignment> {
    val result = ArrayList<ContextAssignment>()
    val files = FileTypeIndex.getFiles(ContextFileType, GlobalSearchScope.allScope(project))

    for (file in files) {
        if (file.name != context)
            continue
        val contextFile = PsiManager.getInstance(project)
                .findFile(file) as ContextFile? ?: continue
        val assignments = PsiTreeUtil.getChildrenOfType(contextFile, ContextAssignment::class.java) ?: continue
        for (assignment in assignments) {
            if (assignment.variable == variable)
                result.add(assignment)
        }
    }
    return result
}

fun depotPath(item: PsiFileSystemItem): String? {
    val path = ArrayList<String>()
    var i: PsiFileSystemItem? = item
    while (i != null) {
        path.add(i.name)
        if (i.name == "domain")
            break
        i = i.parent
    }
    if (path.size < 4 || path[path.size - 1] != "domain")
        return null
    val subPath = path.subList(2, path.size - 1)
    Collections.reverse(subPath)
    return subPath.joinToString("/")
}

/**
 * Lookup context variable in domain/$location/context/$context, its supergroups and
 * domain/common/context/$context.
 *
 * @param project
 *
 * @param location configuration depot path substring between domain/ and /context/$context
 *
 * @param context name of context
 *
 * @param variable name of context variable
 *
 * @return the variable assignment, or null if not found
 */
fun lookupContextVariable(project: Project, location: String, context: String, variable: String): ContextAssignment? {
    for (assignment in findContextVariable(project, context, variable)) {
        val subPathStr = depotPath(assignment.containingFile)
        if (location == subPathStr || location.startsWith(subPathStr + "/"))
            return assignment
    }
    if (location == "common")
        return null
    else
        return lookupContextVariable(project, "common", context, variable)
}