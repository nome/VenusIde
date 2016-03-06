package de.sciencecomputing.venuside;

import de.sciencecomputing.venuside.psi.ContextFile;
import de.sciencecomputing.venuside.psi.ContextAssignment;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.PsiFileSystemItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utilities for dealing with an scVENUS style domain/... directory tree
 */
public class DomainTree {
    public static List<ContextAssignment> findContextVariable(@NotNull Project project, @NotNull String context, @NotNull String variable) {
        List<ContextAssignment> result = new ArrayList<ContextAssignment>();
        Collection<VirtualFile> files = FileTypeIndex.getFiles(ContextFileType.INSTANCE, GlobalSearchScope.allScope(project));

        for (VirtualFile file : files) {
            if (!file.getName().equals(context))
                continue;
            ContextFile contextFile = (ContextFile) PsiManager.getInstance(project).findFile(file);
            if (contextFile == null)
                continue;
            ContextAssignment[] assignments = PsiTreeUtil.getChildrenOfType(contextFile, ContextAssignment.class);
            if (assignments == null)
                continue;
            for (ContextAssignment assignment: assignments) {
                if (assignment.getVariable().equals(variable))
                    result.add(assignment);
            }
        }
        return result;
    }

    public static String depotPath(PsiFileSystemItem item) {
        List<String> path = new ArrayList<String>();
        for(PsiFileSystemItem i = item; i != null; i = i.getParent()) {
            path.add(i.getName());
            if (i.getName().equals("domain"))
                break;
        }
        if (path.size() < 4 || !path.get(path.size()-1).equals("domain"))
            return null;
        List<String> subPath = path.subList(2, path.size()-1);
        Collections.reverse(subPath);
        return String.join("/", subPath);
    }

    /**
     * Lookup context variable in domain/$location/context/$context, its supergroups and
     * domain/common/context/$context.
     *
     * @param project
     * @param location configuration depot path substring between domain/ and /context/$context
     * @param context name of context
     * @param variable name of context variable
     * @return the variable assignment, or null if not found
     */
    public static ContextAssignment lookupContextVariable(Project project, @NotNull String location, @NotNull String context, @NotNull String variable) {
        for (ContextAssignment assignment : findContextVariable(project, context, variable)) {
            String subPathStr = depotPath(assignment.getContainingFile());
            if (location.equals(subPathStr) || location.startsWith(subPathStr + "/"))
                return assignment;
        }
        if (location.equals("common"))
            return null;
        else
            return lookupContextVariable(project, "common", context, variable);
    }
}