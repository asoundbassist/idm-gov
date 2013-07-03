package launch;

public class Launcher {

    public static void main(String[] args) {
        JarClassLoader jcl = new JarClassLoader();
        try {
            jcl.invokeMain("ui.SearchWindow", args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    } // main()
    
} // class MyAppLauncher