package com.gaoding.unittest;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

public class FormatCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        WriteCommandAction.runWriteCommandAction(event.getProject(), new Runnable() {
            @Override
            public void run() {
                PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
                Editor editor = DataKeys.EDITOR.getData(event.getDataContext());
                PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
                PsiClass psiClass = PsiTreeUtil.getParentOfType(element, PsiClass.class);
                PsiMethod[] methods = psiClass.getMethods();

                if(methods==null&&methods.length<=0){
                    return;
                }
                for (PsiMethod method : methods) {
                    String name = method.getName();
                    if(!name.startsWith("test")){
                        String firstCharter = Character.toString(name.charAt(0));
                        firstCharter = firstCharter.toUpperCase();
                        name = "test"+firstCharter+name.substring(1);
                    }
                    method.setName(name);
                }
            }
        });


    }


}
