package Nexzer;

import com.intellij.codeInsight.template.Expression;
import com.intellij.codeInsight.template.ExpressionContext;
import com.intellij.codeInsight.template.Result;
import com.intellij.codeInsight.template.TextResult;
import com.intellij.codeInsight.template.macro.MacroBase;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class PluginMacro extends MacroBase {
    public PluginMacro() {
        super("CakePHP3Plugin", "CakePHP3Plugin");
    }
    @Override
    protected Result calculateResult(@NotNull Expression[] params, ExpressionContext context, boolean quick) {
        //Find project path
        String baseDir = context.getProject().getBaseDir().getPath();
        String currentFilePath = FileDocumentManager.getInstance().getFile(context.getEditor().getDocument()).getPath();
        String projectPath = currentFilePath.replace(baseDir+"/","");

        //array of file structure
        String[] folders = projectPath.split("/");

        String[] search = {"src","plugins"};
        String plugin = "";

        //find the plugin
        for(int i=0;i<folders.length-1;i++) {
            String folder = folders[i];
            if(Arrays.asList(search).contains(folder)){
                if(folder.equals("src")){
                    plugin = "app";
                }else{
                    plugin = folders[i+1];
                }
                break;
            }
        }

        //convert plugin to lowercase underscore version
        plugin = plugin.replaceAll("(?<!^)(?=[A-Z])", "_").toLowerCase();

        return new TextResult(plugin);
    }
}
