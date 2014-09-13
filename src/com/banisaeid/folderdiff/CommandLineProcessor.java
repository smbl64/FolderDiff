package com.banisaeid.folderdiff;

public class CommandLineProcessor implements Logger{
    private Controller controller = new Controller(this);

    public void process(String[] args) {
        if (args.length == 0) {
            showHelp(true);
            return;
        }

        String command = args[0];

        if (command.equals("--take-snapshot") || command.equals("-ts")){
            if (args.length < 3){
                showHelp(false);
                return;
            }

            controller.takeSnapshot(args[1], args[2]);
            return;
        }

        if (command.equals("--generate-patch") || command.equals("-gp")){
            if (args.length < 4){
                showHelp(false);
                return;
            }

            controller.generatePatch(args[1], args[2], args[3]);
            return;
        }

        if (command.equals("--info")){
            if (args.length < 2){
                showHelp(false);
                return;
            }

            controller.showSnapshotInfo(args[1]);
            return;
        }

        showHelp(true);
    }


    private void showHelp(boolean showVersion){
        if (showVersion){
            log("Folder Diff v%s", CommonProps.VERSION);
        }

        log("FolderDiff <Command> <Param 1> <Param 2> ...");
        log("Command: --take-snapshot (-ts)");
        log(" Params:");
        log("    <Folder>        : The folder to take snapshot of");
        log("    <Snapshot File> : Where to save the snapshot");
        log(" Example: --take-snapshot c:\\my_folder\\ current.snapshot");
        log("");

        log("Command: --generate-patch (-gp)");
        log(" Params:");
        log("    <Snapshot File>: Snapshot for comparison");
        log("    <Source Folder>: Source folder for comparison");
        log("    <Patch Folder> : Where to generate the patch folder.");
        log(" Example: --generate-patch current.snapshot c:\\my_new_folder\\ c:\\patch_2014\\");
        log("");

        log("Command: --info");
        log(" Params:");
        log("    <Snapshot File> : Snapshot file");
        log(" Example: --info current.snapshot");
        log("");
    }

    @Override
    public void log(String format, Object... args) {
        String msg = String.format(format, args);
        System.out.println(msg);
    }
}
